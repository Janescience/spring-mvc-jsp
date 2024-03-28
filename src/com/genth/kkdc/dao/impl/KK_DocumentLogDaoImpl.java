/**
 * 
 */
package com.genth.kkdc.dao.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.*;

import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.genth.kkdc.common.util.StringUtil;
import com.genth.kkdc.dao.KK_Document_LogDao;
import com.genth.kkdc.domain.KK_Document;
import com.genth.kkdc.domain.KK_Document_Log;
import com.genth.kkdc.domain.KK_Document_Log_ForKKGENBIZ;
import com.genth.kkdc.domain.User;
import com.genth.kkdc.domain.UserRole;
import com.genth.kkdc.exception.CommonException;

/**
 * @author Thanompong.W
 *
 */
@Service("KK_Document_LogDao")
@Transactional(rollbackFor = CommonException.class)
public class KK_DocumentLogDaoImpl implements KK_Document_LogDao {
	
	private static Logger logger = Logger.getLogger(KK_DocumentLogDaoImpl.class);
	private static final String COMPLETED = "Completed";
	
	@PersistenceContext(unitName="KK_UL_Callback")
	private EntityManager em;

	@PersistenceContext(unitName="KKGENBIZ")
	private EntityManager emKKGENBIZ;
	
	public KK_DocumentLogDaoImpl() {
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
	public List<KK_Document_Log> getAll() throws CommonException {
		
		try {

			StringBuilder sql = new StringBuilder();
			
			sql.append("SELECT M.* FROM KK_Document_Log M where M.status <> 'Cancelled' ");
			
			Query query = em.createNativeQuery(sql.toString(), KK_Document_Log.class);
			 
			List<KK_Document_Log> kkLogList =query.getResultList();
			return kkLogList;
			
		} catch(NoResultException e) {
	        return null;
	    }
		
	} 
	
	@Override
	public List<KK_Document_Log> getByAppNo(String appNo)
			throws CommonException {
		try {

			StringBuilder sql = new StringBuilder();
			
			sql.append("SELECT M.* FROM KK_Document_Log M WHERE M.AppNo = '"+appNo+"' and M.status = 'Not Received' ORDER BY M.id");
			
			Query query = em.createNativeQuery(sql.toString(), KK_Document_Log.class);
			 
			List<KK_Document_Log> kkLogList =query.getResultList();
			return kkLogList;
			
		} catch(NoResultException e) {
	        return null;
	    }
	}
	@Override
	public List<KK_Document_Log> getByDocId(String docId)
			throws CommonException {
		try {

			StringBuilder sql = new StringBuilder();
			
			sql.append("SELECT M.* FROM KK_Document_Log M WHERE M.doc_id = '"+docId+"' and M.status <> 'Cancelled' ORDER BY M.id");
			
			Query query = em.createNativeQuery(sql.toString(), KK_Document_Log.class);
			 
			List<KK_Document_Log> kkLogList =query.getResultList();
			return kkLogList;
			
		} catch(NoResultException e) {
	        return null;
	    }
	}

	@Override
	public KK_Document_Log get(Integer id) {
		// TODO Auto-generated method stub
		return (KK_Document_Log) em.find(KK_Document_Log.class, id);
	}

	@Override
	public void edit(KK_Document_Log doc, String mode) throws CommonException {
		// TODO Auto-generated method stub
		String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
		KK_Document_Log existingDoc = this.get(doc.getId());
		
		if( "updateKKDocStatus".equals(mode) ){
			existingDoc.setStatus(doc.getStatus());
			existingDoc.setRemark(doc.getRemark());
			existingDoc.setReviewBy(currentUser);
			existingDoc.setReviewDate(new Timestamp(System.currentTimeMillis()));
			if(COMPLETED.equals(doc.getStatus())){

				existingDoc.setCompleteBy(currentUser);
				existingDoc.setCompleteDate(new Timestamp(System.currentTimeMillis()));
			}
		}else{
			existingDoc.setStatus(doc.getStatus());
			existingDoc.setReviewBy(currentUser);
			existingDoc.setReviewDate(new Timestamp(System.currentTimeMillis()));
			existingDoc.setRemark(doc.getRemark());
			existingDoc.setFollowupDate(new Timestamp(System.currentTimeMillis()));
			existingDoc.setCompleteBy(currentUser);
			existingDoc.setCompleteDate(new Timestamp(System.currentTimeMillis()));
			
		}
		
		
		existingDoc.setUpdatedBy(currentUser);
		existingDoc.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
		
		em.merge(existingDoc); 
//		em.flush();
	}

	@Override
	public void add(KK_Document_Log doc) throws CommonException {
		// TODO Auto-generated method stub
		em.persist(doc);
	}

	@Override
	public void delete(Integer id) throws CommonException {
		// TODO Auto-generated method stub
				
		KK_Document_Log existingDoc = this.get(id);
		
		em.merge(existingDoc); 
	}

	
	@Override
	public KK_Document_Log_ForKKGENBIZ getKKGenBiz(Integer id) {
		// TODO Auto-generated method stub
		return (KK_Document_Log_ForKKGENBIZ) emKKGENBIZ.find(KK_Document_Log_ForKKGENBIZ.class, id);
	}

	@Override
	public void editKKGenBiz(KK_Document_Log doc, String mode)
			throws CommonException {
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("KKGENBIZ");
		EntityManager customEm = emf.createEntityManager();
		
		customEm.getTransaction().begin();
		
		String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
		KK_Document_Log_ForKKGENBIZ existingDoc = this.getKKGenBiz(doc.getId());
		
		if(existingDoc == null){
			customEm.getTransaction().commit();
			customEm.close();
			emf.close();
			return;
		}
		
		
		if( "updateKKDocStatus".equals(mode) ){
			existingDoc.setStatus(doc.getStatus());
			existingDoc.setRemark(doc.getRemark());
			existingDoc.setReviewBy(currentUser);
			existingDoc.setReviewDate(new Timestamp(System.currentTimeMillis()));
			if(COMPLETED.equals(doc.getStatus())){

				existingDoc.setCompleteBy(currentUser);
				existingDoc.setCompleteDate(new Timestamp(System.currentTimeMillis()));
			}
		}else{
			existingDoc.setStatus(doc.getStatus());
			existingDoc.setReviewBy(currentUser);
			existingDoc.setReviewDate(new Timestamp(System.currentTimeMillis()));
			existingDoc.setRemark(doc.getRemark());
			existingDoc.setFollowupDate(new Timestamp(System.currentTimeMillis()));
			existingDoc.setCompleteBy(currentUser);
			existingDoc.setCompleteDate(new Timestamp(System.currentTimeMillis()));
			
		}
		
		
		existingDoc.setUpdatedBy(currentUser);
		existingDoc.setUpdatedDate(new Timestamp(System.currentTimeMillis()));

//		emKKGENBIZ.merge(existingDoc);
		customEm.merge(existingDoc); 			// Save as history record
		customEm.getTransaction().commit();
		customEm.close();
		emf.close();
		
	}
	 
		
	@Override
	public int insertKKDocumentLog(Integer id,String appNo)
			throws Exception { 
		int returnRec = 0;
		try {
			// UAT-BI DB
			Query q = em.createNativeQuery("{call sp_insertKKDocumentLogForRejectSAPrem(?,?)}")
					.setParameter(1, id)
					.setParameter(2, appNo);	
			
			String result = q.getSingleResult().toString();
			
//			System.out.println("==>sp_insertKKDocumentLogForRejectSAPrem = "+result+ " row(s)." );
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			returnRec=-99;
			throw e;
		}
		return returnRec;
	}
}
