/**
 * 
 */
package com.genth.kkdc.dao.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.*;
import javax.persistence.metamodel.StaticMetamodel;

import org.apache.log4j.Logger;
import org.omg.CORBA.COMM_FAILURE;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.genth.kkdc.common.util.MessageResolver;
import com.genth.kkdc.common.util.StringUtil;
import com.genth.kkdc.dao.KK_DocumentDao;
import com.genth.kkdc.domain.BillpaymentNotice;
import com.genth.kkdc.domain.KK_Document;
import com.genth.kkdc.domain.KK_Document_ForKKGENBIZ;
import com.genth.kkdc.domain.KK_Document_Log;
import com.genth.kkdc.domain.KK_Document_Log_ForKKGENBIZ;
import com.genth.kkdc.domain.RejectByAppno;
import com.genth.kkdc.domain.User;
import com.genth.kkdc.domain.UserRole;
import com.genth.kkdc.exception.CommonException;
import com.genth.kkdc.service.ResourceConfig;

/**
 * @author Thanompong.W
 *
 */
@Service("KK_DocumentDao")
@Transactional(rollbackFor = Exception.class)
public class KK_DocumentDaoImpl implements KK_DocumentDao {
	
	private static Logger logger = Logger.getLogger(KK_DocumentDaoImpl.class);

	private static final String COMPLETED = "Completed";
	
	@PersistenceContext(unitName="KK_UL_Callback")
	private EntityManager em;
	
	@PersistenceContext(unitName="KKGENBIZ")
	private EntityManager emKKGENBIZ;

	public KK_DocumentDaoImpl() {
	}

	/**
	 * @param em the entityManager to set
	 */
	public void setEntityManager(EntityManager entityManager) {
		this.em = entityManager;
	}

	public void setEmKKGENBIZ(EntityManager emKKGENBIZ) {
		this.emKKGENBIZ = emKKGENBIZ;
	}

	
	@Override
	public List<KK_Document> getAll()
			throws CommonException {
		// TODO Auto-generated method stub
		return getByKKDocStatus("");
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<KK_Document> getByKKDocStatus(String status) throws CommonException {
		 
		try {
			String fileServer = ResourceConfig.getCommonProperty("FILE_SERVER");
			
			StringBuilder sqlDoc = new StringBuilder();
			
			sqlDoc.append(" SELECT ");
			if("".equals(status) && status.length() < 1){
				// For Documents History Menu order by docId and select top 1000
				sqlDoc.append(" TOP 1000 ");
			}
			sqlDoc.append(" DOCID,PRODUCT_TYPE,SUBMIT_DATA,BRANCH,FIRSTNAME,LASTNAME,IDCARDNO,SUMASSURE, ");
			sqlDoc.append("             PREMIUM,PACKAGE,AGENTCODE,ISNULL(T6.USERTHAIFIRSTNAME + ' '  + T6.USERTHAILASTNAME,' ' )  AGENTNAME, ");
			sqlDoc.append("             APPNO,LOCATION,CREATEBY,CASE WHEN STATUS like 'Resolved%' THEN 'Resolved' ELSE STATUS END ,SISNO,DOCTYPE,ISSENDMAIL, ");
			sqlDoc.append(" 	'"+fileServer+"' AS fileServer, ");
			sqlDoc.append("     ISNULL(T6.email,'BLANK_EMAIL') AS staffEmail, ");
			sqlDoc.append(" (SELECT COUNT(1) FROM KK_DOCUMENT XX WHERE XX.APPNO = A1.APPNO ) AS CNTPDF,");
			sqlDoc.append(" ISNULL(KKREFNO,' ') AS KKREFNO ");
			sqlDoc.append(" FROM ( ");
			sqlDoc.append(" 	SELECT T2.ID DOCID,   	 ");
			sqlDoc.append(" 	T1.CHANNEL PRODUCT_TYPE,  	 ");
			sqlDoc.append(" 	CONVERT( NVARCHAR(10), T2.CREATEDATE, 103) + ' ' + LEFT(CONVERT( NVARCHAR(10), T2.CREATEDATE, 114),5) SUBMIT_DATA, ");
			sqlDoc.append(" 	T3.BRANCH_NAME BRANCH,  	 ");
			sqlDoc.append(" 	T4.FIRSTNAME FIRSTNAME,  	 ");
			sqlDoc.append(" 	T4.SURNAME LASTNAME,   ");
			sqlDoc.append(" 	T4.IDCARDNO IDCARDNO,  	 ");
			sqlDoc.append(" 	T5.SUMINSURED SUMASSURE,  	 ");
			sqlDoc.append(" 	T5.PREMIUM PREMIUM,  	 ");
			sqlDoc.append(" 	T7.PLANNAME_THAI PACKAGE,  	 ");
			sqlDoc.append(" 	T1.AGENTCODE AGENTCODE, ");
			sqlDoc.append(" 	T2.APPNO APPNO,  	 ");
			sqlDoc.append(" 	T2.LOCATION LOCATION,  	 ");
			sqlDoc.append(" 	T2.CREATEBY CREATEBY,  	 ");
			sqlDoc.append(" 	T2.STATUS STATUS,  	 ");
			sqlDoc.append(" 	T1.SISNO SISNO,  	 ");
			sqlDoc.append(" 	T7.ISDOCTYPE DOCTYPE,   ");
			sqlDoc.append(" 	ISNULL(T7.ISCOMPLETESENDMAIL,'') AS ISSENDMAIL,   ");
			sqlDoc.append(" 	T1.KKREFNO KKREFNO   ");
			sqlDoc.append(" 	FROM SIS T1,  	 ");
			sqlDoc.append(" 	(select status,appno,Max(id) as id,max(CreateDate) as CreateDate, max(Location) as Location, max(CreateBy) as CREATEBY from KK_DOCUMENT  group by status,appno   ) T2,  	 ");
			sqlDoc.append(" 	KK_BRANCH_MASTER T3 ,  	 ");
			sqlDoc.append(" 	SISCLIENT T4,  	 ");
			sqlDoc.append(" 	SISPLAN T5,    	 ");
			sqlDoc.append(" 	SISPLANSETUP T7  ");
			sqlDoc.append(" 	WHERE T1.APPNO = T2.APPNO  	 ");
			if(!"".equals(status) && status.length() > 0){
				sqlDoc.append(" 	AND T2.STATUS IN ( "+ status + ") ");
			}
			sqlDoc.append(" 	AND T1.BRANCHCODE = T3.BRANCH_KEY   	 ");
			sqlDoc.append(" 	AND T1.SISNO = T4.SISNO  	 ");
			sqlDoc.append(" 	AND T1.SISNO = T5.SISNO  	 ");
			sqlDoc.append(" 	AND T5.PLANCODE = T7.PLANCODE  	 ");
			sqlDoc.append(" 	AND T7.ISBASICPLAN = 1   ");
			sqlDoc.append(" 	AND T1.APPCUSTATUS NOT IN ('RM')   ");
			sqlDoc.append(" 	AND T2.STATUS NOT IN ('Cancelled')   ");
			sqlDoc.append(" ) A1 LEFT JOIN KK_STAFF T6 ");
			sqlDoc.append(" ON A1.AGENTCODE = 'KK' + T6.USERID ");
			
			if("".equals(status) && status.length() < 1){
				// For Documents History Menu order by docId and select top 1000
				sqlDoc.append(" ORDER BY A1.DOCID DESC");
			}else{
				sqlDoc.append(" ORDER BY A1.STATUS DESC, A1.SUBMIT_DATA ");
			}
			
//			System.out.println("SQL="+sqlDoc.toString());
			
			Query query = em.createNativeQuery(sqlDoc.toString());
			List<Object[]> objs = query.getResultList();  
			
			List<KK_Document> kkDocList = new ArrayList<KK_Document>();
			
			for (Object[] obj : objs) {
				
				KK_Document doc = new KK_Document(obj,0);
				
				kkDocList.add(doc);
				
			}
			
			
			return kkDocList;
			
		} catch(NoResultException e) {
	        return null;
	    } 
	}
	
	@Override
	public List<String[]> getAllDocument(String appNo)
			throws CommonException {
		String allDocList = "";
		try {
			String getAllPdf = " select Location FROM KK_DOCUMENT A2 where A2.APPNO = '"+appNo+"' ORDER BY A2.ID DESC ";
			Query queryAllPdf = em.createNativeQuery(getAllPdf);
			List<String[]> objs = queryAllPdf.getResultList();
			
			return objs;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<BillpaymentNotice> getBillpaymentNotice(String appNo)
			throws CommonException {
		String allDocList = "";
		try {
			String whereStr = "";

			List<BillpaymentNotice> kkDocList = new ArrayList<BillpaymentNotice>();
			
			if( !"undefined".equals(appNo) ){
				whereStr = " AND A2.APPNO like '%"+appNo+"%' ";
			}else{
				return kkDocList;
			}
			
			// This connect to db (172.16.3.13 / 192.168.0.193)
			String getAllPdf = " select * FROM [dbo].[vBillpaymentNotice] A2 where 1=1 "+whereStr+" ORDER BY A2.KKDOCID DESC ";
			Query queryAllPdf = emKKGENBIZ.createNativeQuery(getAllPdf);
			List<String[]> objs = queryAllPdf.getResultList();
			
			
			for (Object[] obj : objs) {
				
				BillpaymentNotice doc = new BillpaymentNotice(obj,0);
				
				kkDocList.add(doc);
				
			}
			
			return kkDocList;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public List<RejectByAppno> getRejectByAppno(String appNo,String searchStatus)
			throws CommonException {
		String allDocList = "";
		try {
			String whereStr = "";

			List<RejectByAppno> kkDocList = new ArrayList<RejectByAppno>();
			
			if( "undefined".equals(appNo) && "undefined".equals(searchStatus) ){
				return kkDocList;
			}else{
				if( !"undefined".equals(appNo) ){
					whereStr = whereStr + " AND A2.APPNO like '%"+appNo+"%' ";
				}
				if( !"undefined".equals(searchStatus) && !"".equals(searchStatus) ){
					whereStr = whereStr + " AND A2.[Status] = '"+searchStatus+"' ";
				}
			}
			
//			if( !"undefined".equals(appNo) ){
//				whereStr = " AND A2.APPNO like '%"+appNo+"%' ";
//			}else{
//				return kkDocList;
//			}
			
			// This connect to db (172.16.3.13 / 192.168.0.193)
			String getAllPdf = " select * FROM [dbo].[vRejectByAppno] A2 where 1=1 "+whereStr+" ORDER BY A2.STATUS DESC,A2.KKDOCID DESC ";
			Query queryAllPdf = emKKGENBIZ.createNativeQuery(getAllPdf);
			List<String[]> objs = queryAllPdf.getResultList();
			
			
			for (Object[] obj : objs) {
				
				RejectByAppno doc = new RejectByAppno(obj,0);
				
				kkDocList.add(doc);
				
			}
			
			return kkDocList;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public int updatePolicyNumber(String appNo,String policyNo)
			throws Exception { 
		int returnRec = 0;
		try {
//			StringBuilder sql = new StringBuilder();
//			
//			sql.append(" UPDATE [KKGen].[dbo].[KK_DOCUMENT]  "); 
//			sql.append(" SET policyno =  :policyno " ); 
//			sql.append(" WHERE APPNO =  :appno "); 
//			
//			Query q =  emKKGENBIZ.createQuery(sql.toString());
//			q.setParameter(1,policyNo);
//			q.setParameter(1,appNo);
//			returnRec = emKKGENBIZ.createQuery(sql.toString()).executeUpdate();
			
			//emKKGENBIZ.flush();
			
			/*
			String getUpdateDoc = " select id FROM [KKGen].[dbo].[KK_Document] A2 where appNo = '"+appNo+"' ";
			Query queryList = emKKGENBIZ.createNativeQuery(getUpdateDoc);
			List<Long[]> objs = queryList.getResultList();
			for (Object obj : objs) {
				
				System.out.println(obj);
				KK_Document_ForKKGENBIZ k = (KK_Document_ForKKGENBIZ) emKKGENBIZ.find(KK_Document_ForKKGENBIZ.class, Integer.valueOf(obj.toString()));
				k.setPolicyNo(policyNo);

				emKKGENBIZ.getTransaction().begin();
				emKKGENBIZ.merge(k);
				

				emKKGENBIZ.getTransaction().commit();
			}

			
			
			System.out.println("kkkk");
			*/
			
			Query q = emKKGENBIZ.createNativeQuery("{call sp_UpdatePolicyNumberAndSendMail(?,?)}")
					.setParameter(1, appNo)
					.setParameter(2, policyNo);	
			
			String result = q.getSingleResult().toString();
			
//			System.out.println("==>UpdatePolicyNumberAndSendMail = "+result+ " row(s)." );
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			returnRec=-99;
			throw e;
		}
		return returnRec;
	}
	
	@Override
	public KK_Document get(Integer id) {
		return (KK_Document) em.find(KK_Document.class, id);
	}
	 	
	@Override
	public void edit(KK_Document doc, String mode) {
		
		KK_Document existingDoc = this.get(doc.getId());
		
		if( "verifyDocument".equals(mode) || 
		    "cancelVerify".equals(mode)   ||
		    "updateKKDocStatus".equals(mode) 
	    ){
			
			existingDoc.setStatus(doc.getStatus());
			
		}else{
			existingDoc.setAppNo(doc.getAppNo());
			existingDoc.setLocation(doc.getLocation());
			existingDoc.setStatus(doc.getStatus());
		}
		
		existingDoc.setUpdatedBy(doc.getUpdatedBy());
		existingDoc.setUpdatedDate(new Timestamp(new java.util.Date().getTime()));
		
		em.merge(existingDoc); 			// Save as history record
//		em.flush();
	}
	
	@Override
	public void delete(Integer id) {
		String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
		
		KK_Document existingDoc = this.get(id);
		
//		exitingBooking.setValidFlag("2");		// Mark record history
//		exitingBooking.setUpdatedBy(	currentUser);
//		exitingBooking.setUpdatedDate( new Timestamp(new java.util.Date().getTime()));
		em.merge(existingDoc); 			// Save as history record
		
		// Retrieve existing configvalue card via id
//		KK_Document delDoc = new KK_Document();	
//		
//		delBooking.setBookValue(	exitingBooking.getBookValue());
//		delBooking.setMarketerId(	exitingBooking.getMarketerId());
//		delBooking.setRegionCd(		exitingBooking.getRegionCd());
//		delBooking.setPlanCd(		exitingBooking.getPlanCd());
//		delBooking.setSubRegionCd(	exitingBooking.getSubRegionCd());
//		delBooking.setBranchCd(		exitingBooking.getBranchCd());
//		
//		delBooking.setHistoryKey(exitingBooking.getHistoryKey());
//		delBooking.setActionType( "Delete" );
//		
//		delBooking.setValidFlag("2");		// Mark record history
//		delBooking.setCreatedBy(	currentUser);
//		delBooking.setCreatedDate(new Timestamp(new java.util.Date().getTime()));
//		em.persist(delBooking); 			// Save as history record
	}

	@Override
	public void add(KK_Document doc) {
		// Persists to db
//		doc.setValidFlag("1");
		em.persist(doc);
//		em.flush();
//		if(booking.getHistoryKey() == null){
//			booking.setHistoryKey(booking.getId());
//		}
//		em.merge(booking);
		
	}

	
	@Override
	public KK_Document_ForKKGENBIZ getKKGenBiz(Integer id) {
		// TODO Auto-generated method stub
		return (KK_Document_ForKKGENBIZ) emKKGENBIZ.find(KK_Document_ForKKGENBIZ.class, id);
	}


	@Override
	public void editKKGenBiz(KK_Document doc, String mode)
			throws CommonException {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("KKGENBIZ");
		EntityManager customEm = emf.createEntityManager();
		
		KK_Document_ForKKGENBIZ existingDoc = this.getKKGenBiz(doc.getId());
		
		customEm.getTransaction().begin();
		if( "verifyDocument".equals(mode) || 
		    "cancelVerify".equals(mode)   ||
		    "updateKKDocStatus".equals(mode) 
	    ){
			
			existingDoc.setStatus(doc.getStatus());
			
		}else{
			existingDoc.setAppNo(doc.getAppNo());
			existingDoc.setLocation(doc.getLocation());
			existingDoc.setStatus(doc.getStatus());
		}
		
		existingDoc.setUpdatedBy(doc.getUpdatedBy());
		existingDoc.setUpdatedDate(new Timestamp(new java.util.Date().getTime()));
		
//		emKKGENBIZ.merge(existingDoc);
		customEm.merge(existingDoc); 			// Save as history record
		customEm.getTransaction().commit();
		customEm.close();
		emf.close();
	}

	
	@Override
	public List<KK_Document> getByAppNo(String appNo) throws CommonException {
		try {

			StringBuilder sql = new StringBuilder();
			
			sql.append("SELECT M.* FROM KK_Document M WHERE M.AppNo = '"+appNo+"' ");
			
			Query query = em.createNativeQuery(sql.toString(), KK_Document.class);
			 
			List<KK_Document> kkDocList =query.getResultList();
			return kkDocList;
			
		} catch(NoResultException e) {
	        return null;
	    }
	}

	@Override
	public void completeDocument(KK_Document doc) throws CommonException {
		// TODO Auto-generated method stub
		
		List<KK_Document> docList = getByAppNo(doc.getAppNo());
		for(KK_Document upd : docList){
			
			KK_Document completeDoc = get(upd.getId());
			completeDoc.setStatus(COMPLETED);
			
			em.merge(completeDoc);
		}
		
	}
	
	
	@Override
	public List<KK_Document> getKKGenBizDocByAppNo(String appNo)
			throws CommonException {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("KKGENBIZ");
		EntityManager customEm = emf.createEntityManager();
		try {

			StringBuilder sql = new StringBuilder();
			
			sql.append("SELECT M.* FROM KK_Document M WHERE M.AppNo = '"+appNo+"' ");
			
			Query query = customEm.createNativeQuery(sql.toString(), KK_Document.class);
			 
			List<KK_Document> kkDocList =query.getResultList();
			
			customEm.close();
			emf.close();
			
			return kkDocList;
			
		} catch(NoResultException e) {
	        return null;
	    }
	}

	@Override
	public void completeKKGENBIZDocument(KK_Document doc) throws CommonException {
		// TODO Auto-generated method stub
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("KKGENBIZ");
		EntityManager customEm = emf.createEntityManager();
		
		customEm.getTransaction().begin();
		
		List<KK_Document> docList = getByAppNo(doc.getAppNo());
		for(KK_Document upd : docList){
			
			KK_Document_ForKKGENBIZ completeDoc = getKKGenBiz(upd.getId());
			completeDoc.setStatus(COMPLETED);
			
			customEm.merge(completeDoc);
		}
		
		
		customEm.getTransaction().commit();
		customEm.close();
		emf.close();
		
	}

	@Override
	public void popupEditWebServiceData(String appno) throws CommonException {
		// TODO Auto-generated method stub
		Query q = em.createNativeQuery("{call sp_PopulateEditWebserviceData(?)}")
				.setParameter(1, appno);	
		
		String result = q.getSingleResult().toString();
		
//		System.out.println("==>Populate Edit Webservice data = "+result+ " row(s)." );
		
	}
	 
		
}
