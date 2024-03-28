/**
 * 
 */
package com.genth.kkdc.dao.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

import com.genth.kkdc.common.util.ConfigurationResolver;
import com.genth.kkdc.common.util.MessageResolver;
import com.genth.kkdc.common.util.StringUtil;
import com.genth.kkdc.dao.KK_DocumentDao;
import com.genth.kkdc.dao.KK_OL_CallbackDao;
import com.genth.kkdc.domain.BillpaymentNotice;
import com.genth.kkdc.domain.KK_Document;
import com.genth.kkdc.domain.KK_Document_ForKKGENBIZ;
import com.genth.kkdc.domain.KK_Document_Log;
import com.genth.kkdc.domain.KK_Document_Log_ForKKGENBIZ;
import com.genth.kkdc.domain.KK_OL_Callback;
import com.genth.kkdc.domain.KK_UL_Fund_Selection;
import com.genth.kkdc.domain.RejectByAppno;
import com.genth.kkdc.domain.User;
import com.genth.kkdc.domain.UserRole;
import com.genth.kkdc.exception.CommonException;
import com.genth.kkdc.service.CalculateCOI;
import com.genth.kkdc.service.ResourceConfig;

/**
 * @author Thanompong.W
 *
 */
@Service("KK_OL_CallbackDao")
@Transactional(rollbackFor = Exception.class)
public class KK_OL_CallbackDaoImpl implements KK_OL_CallbackDao {

	
	private static Logger logger = Logger.getLogger(KK_OL_CallbackDaoImpl.class);

	private static final String COMPLETED = "Completed";
	
	// อันนี้ต้องเป็น KK_UL_Callback เพราะว่าเป็นชื่อโปรเจ็ค
	@PersistenceContext(unitName="KK_UL_Callback")
	private EntityManager em;
	
	@PersistenceContext(unitName="KKGENBIZ")
	private EntityManager emKKGENBIZ;

	public KK_OL_CallbackDaoImpl() {
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
	public List<KK_OL_Callback> getAll(String customerName,String searchStatus)
			throws CommonException {
		return getAll(customerName, searchStatus, null, null);
	}
	
	@Override
	public List<KK_OL_Callback> getAll(String customerName,String searchStatus, Date start, Date end)
			throws CommonException {
		// TODO Auto-generated method stub
		try {
			
			
			String whereStr = "";

			List<KK_OL_Callback> kkulCallbackList = new ArrayList<KK_OL_Callback>();
			
//			if( "undefined".equals(customerName) && "undefined".equals(searchStatus) ){
//				return kkulCallbackList;
//			}else{
				if( !"undefined".equals(customerName) && !"".equals(customerName) ){
					whereStr = whereStr + " AND (sc.Title+sc.FirstName+' '+sc.Surname) like N'%"+customerName+"%' ";
				}
				if( !"undefined".equals(searchStatus) && !"".equals(searchStatus) ){
					whereStr = whereStr + " AND cl.[kk_flag] = '"+searchStatus+"' ";
				}
				
				SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
				if( start != null ){
					String startStr = fmt.format(start);
					whereStr = whereStr + " AND cl.[createdate] >= '"+startStr+"' ";
				}
				if( end != null ){
					Calendar cal = Calendar.getInstance();
					cal.setTimeInMillis(end.getTime());
					cal.add(Calendar.DAY_OF_MONTH, 1);
					String endStr = fmt.format(cal.getTime());
					//String endStr = fmt.format(end);
					whereStr = whereStr + " AND cl.[createdate] < '"+endStr+"' ";
				}
//			}
			

			StringBuilder sql = new StringBuilder();
			
			sql.append(" SELECT cl.id, ");
				sql.append(" CONVERT(CHAR(23), cl.createdate, 103) as createdate, ");
				sql.append(" case when sc.Title = N'นส' then N'น.ส.' else sc.Title end + sc.FirstName+' '+sc.Surname as clientName, ");
				sql.append(" sc.MobileNo, ");
				sql.append(" case when cl.[kk_flag] = 'Y' then N'ยืนยันข้อมูล' ");
				sql.append("   when cl.[kk_flag] = 'R' then N'ลูกค้ายกเลิก' ");
				sql.append("   when cl.[kk_flag] = 'W' then N'รอตัดสินใจ' ");
				sql.append("   else 'New' end as status, ");
				sql.append(" s.AgentCode, ");
				sql.append(" sf.Titlename+sf.Userthaifirstname+' '+sf.Userthailastname as staffName, ");
				sql.append(" agentLicenseCode = isnull((SELECT slc.license_No FROM KK_Staff_License as slc WHERE sf.userid = slc.userid ");
				sql.append(" AND slc.license_type IN ('LC003', 'LIFE') ");
				sql.append(" ),'-') , "); 
				
				// get top most consult code expire date
				sql.append(" agentConsultantCode = isnull( "); 
				sql.append(" (SELECT TOP(1) slc.license_No FROM KK_Staff_License as slc WHERE sf.userid = slc.userid "); 
				sql.append(" AND slc.license_type IN ('LC021', 'LC035', 'ICComplex1', 'LC024', 'LC036', 'ICComplex2', 'LC019', 'LC026', 'LC037', 'ICComplex3', 'LC039', 'Single') "); 
				sql.append(" AND slc.license_status = 'A' ORDER BY license_expiry_dt DESC "); 
				sql.append(" ),'-') , "); 
				
				sql.append(" sf.Branchthai, ");
				sql.append(" sp.PlanCode, ");
				sql.append(" plan_setup.PlanName, ");
				sql.append(" CONVERT(varchar, CAST(sp.Premium AS money), 1) as premium, ");
				sql.append(" isnull(CONVERT(varchar, CAST((select sum(sp1.Premium )from sisplan sp1 where sp1.IsBasic = '0' and sp1.SISNo = s.sisno) as money),1),'0.00' ) as premiumRider, ");
//				sql.append("convert(varchar, cast(  CASE"); 
//				sql.append("   when s.PaymentMode = 'S' then ");
//				sql.append("   ((sp.Premium )+ isnull((select sum(sp1.Premium )from sisplan sp1 where sp1.IsBasic = '2' and sp1.SISNo = s.sisno),0))*2");
//				sql.append("   when s.PaymentMode = 'Q' then ");
//				sql.append("   ((sp.Premium )+ isnull((select sum(sp1.Premium )from sisplan sp1 where sp1.IsBasic = '2' and sp1.SISNo =s.sisno),0))*4");
//				sql.append("   when s.PaymentMode = 'M' then ");
//				sql.append("  ((sp.Premium )+ isnull((select sum(sp1.Premium )from sisplan sp1 where sp1.IsBasic = '2' and sp1.SISNo =s.sisno),0))*12 ");
//				sql.append("   else ");
//				sql.append("   ((sp.Premium )+ isnull((select sum(sp1.Premium )from sisplan sp1 where sp1.IsBasic = '2' and sp1.SISNo =s.sisno),0))*1 ");
//				sql.append("END as money),1) as totalPremium,");
				//sql.append(" isnull(CONVERT (varchar,(CAST(sp.Premium AS money) + CAST((select sum(sp1.Premium )from sisplan sp1 where sp1.IsBasic = '2' and sp1.SISNo = s.sisno) as money)),1), CONVERT(varchar, CAST(sp.Premium AS money), 1) ) as totalPremium, ");
				sql.append(" isnull(CONVERT(varchar, CAST((select sum(sp1.Premium )from sisplan sp1 where sp1.SISNo = s.sisno)as money),1),'0.00') as totalPremium,   ");
				sql.append(" CONVERT(varchar, CAST(sp.SumInsured AS money), 1) as SumInsured, ");
				sql.append(" sc.gender, "); 
				sql.append(" sc.age, "); 
				sql.append(" s.sisno, "); 
				sql.append(" s.AppNo, "); 
				//sql.append(" isnull( CONVERT(CHAR(23),s.CreateDate, 103),'') sisDate,");
				sql.append(" isnull( CONVERT(CHAR(23),insp.CreateDateTime, 103),'') sisDate,");
				sql.append(" isnull(s.KKRefNo,'') KKRefNo , "); 
				sql.append(" isnull(cl.kk_note,'') kk_note , "); 
				sql.append(" isnull(cl.note_from_branch,'') note_from_branch , "); 
				//sql.append(" case when cl.note_from_branch is null then '' else cl.note_from_branch end as note_from_branch , "); 
				sql.append(" isnull(q1,'') q1, isnull(q2,'') q2, isnull(q3,'') q3, isnull(q4,'') q4, isnull(q5,'') q5 , ");  
				sql.append(" isnull(q6,'') q6, isnull(q7,'') q7, isnull(q8,'') q8, isnull(q9,'') q9, isnull(q10,'') q10 , ");
				
				sql.append(" isnull( CONVERT(CHAR(23),cl.kk_timestamp, 103),'') callDate , isnull( CONVERT(CHAR(23),cl.kk_timestamp, 108),'') callTime, ");
				sql.append(" isnull(cl.callBy,'') callBy, ");
				//sql.append(" case when q1 = q2 and q2=q3 and q3=q4 and q4=q5 and q5=q6 and q6=q7 and q7 = 1 then 'Yes' else 'No' end as isConfirm ");
				sql.append(" case when cl.[kk_flag]  = 'Y' then 'Yes' else 'No' end as isConfirm , ");
				sql.append(" case when cl.first_review_timestamp is null then '-' else CONVERT(varCHAR(23), cl.first_review_timestamp,103) end as first_review_timestamp, ");
				sql.append(" isnull(pf.planCode,''), isnull(pf.planName,''), isnull(pf.coverageterm,''), isnull(pf.premiumterm,''), isnull(pf.refundtext,'') " );
				//sql.append(" ,lo.name as paymentMode, ");
				sql.append(",isnull((select name from LookupOptions where groupid='PAYMENT_PERIOD' and id=s.PaymentMode ), '') as paymentMode,");
				sql.append(" isnull(CONVERT(varchar, CAST((select sp1.Premium from sisplan sp1 where sp1.IsBasic = '0' and sp1.PlanCode='9943' and sp1.SISNo = s.sisno)as money),1),'0.00') as premiumHB,   ");
				sql.append(" isnull(CONVERT(varchar, CAST((select sp1.Premium from sisplan sp1 where sp1.IsBasic = '0' and sp1.PlanCode='9416' and sp1.SISNo = s.sisno)as money),1),'0.00') as premiumCI,   ");
				sql.append(" isnull(CONVERT(varchar, CAST((select sp1.Premium from sisplan sp1 where sp1.IsBasic = '0' and sp1.PlanCode='9940' and sp1.SISNo = s.sisno)as money),1),'0.00') as premiumWP,   ");
				//sql.append(" isnull(CONVERT(varchar, CAST((select sp1.Premium from sisplan sp1 where sp1.IsBasic ='0' and sp1.PlanCode='991A' and sp1.SISNo = s.sisno)as money),1),'N') as premiumADD,   ");
				sql.append(" isnull(CONVERT(varchar, CAST((select sp1.Premium from sisplan sp1 where sp1.IsBasic = '0' and sp1.PlanCode='991B' and sp1.SISNo = s.sisno)as money),1),'0.00') as premiumADB,   ");
				sql.append(" isnull(CONVERT(varchar, CAST((select sp1.SumInsured from sisplan sp1 where sp1.IsBasic = '0' and sp1.PlanCode='9943' and sp1.SISNo = s.sisno) as money),1),'0.00') as saHB,   ");
				sql.append(" isnull(CONVERT(varchar, CAST((select sp1.SumInsured from sisplan sp1 where sp1.IsBasic = '0' and sp1.PlanCode='9416' and sp1.SISNo = s.sisno)as money),1),'0.00') as saCI,   ");
				sql.append(" isnull(CONVERT(varchar, CAST((select sp1.SumInsured from sisplan sp1 where sp1.IsBasic = '0' and sp1.PlanCode='9940' and sp1.SISNo = s.sisno)as money),1),'0.00') as saWP,   ");
				//sql.append(" isnull(CONVERT(varchar, CAST((select sp1.SumInsured from sisplan sp1 where sp1.IsBasic = '0' and sp1.PlanCode='991A' and sp1.SISNo = s.sisno)as money),1),'N') as saADD,   ");
				sql.append(" isnull(CONVERT(varchar, CAST((select sp1.SumInsured from sisplan sp1 where sp1.IsBasic = '0' and sp1.PlanCode='991B' and sp1.SISNo = s.sisno)as money),1),'0.00') as saADB,   ");
				sql.append(" (select count(*) from dbo.SISPlan where (SISNo=s.sisno) and (ISBASIC='0')) as checkRider ");
				sql.append(" FROM KK_OL_Callback cl ");
			sql.append(" inner join sisclient sc  on cl.sisno = sc.sisno ");
			sql.append(" inner join sis s         on cl.sisno = s.sisno "); 
//			sql.append(" inner join [kk].[dbo].[sis] s         on cl.sisno = s.sisno "); // pui change for test
			sql.append(" inner join sisplan sp    on cl.sisno = sp.sisno and sp.IsBasic = 1 ");
			sql.append(" inner join kk_staff sf   on s.AgentCode = 'KK'+sf.userid ");
			//sql.append(" inner join kk_staff sf   on s.AgentCode COLLATE THai_CI_AS = 'KK' + sf.userid COLLATE THai_CI_AS "); // pui change for test
			sql.append(" inner join SISPlanSetup plan_setup on plan_setup.planCode = sp.planCode ");
			sql.append(" LEFT JOIN KK_Product_feature pf on plan_setup.PlanCode = pf.planCode ");
			sql.append(" LEFT JOIN KK_Create_KKINSP2_Data insp on insp.sisno = s.sisno ");
			//sql.append("   left join LookupOptions lo on s.PaymentMode = lo.id "); //pui test
			sql.append(" WHERE 1=1 and s.KKRefNo is not null and s.AppNo is not null and s.AppCuStatus<>'RM' and insp.WSStatus<>'RM' ");
		//	sql.append(" and lo.groupid = 'PAYMENT_PERIOD' "); //pui test 
			sql.append( whereStr );
			sql.append(" order by cl.[kk_flag], cl.createdate desc, cl.id ");
			
			System.out.println("*******************************************************************");
			System.out.println("SQL KK_OL_Callback:\n"+sql.toString());
			System.out.println("*******************************************************************");
			
			Query query = em.createNativeQuery(sql.toString());
			List<Object[]> objs = query.getResultList();  
						
			
			for (Object[] obj : objs) {
				
				KK_OL_Callback doc = new KK_OL_Callback(obj,0);
				
//				doc.setFrontEndFee( CalculateCOI.getFrontEndFee(doc.getPlanCode(), doc.getPremium()) );
//				doc.setCoiFee( CalculateCOI.sendGet(doc.getPlanCode(), doc.getAge(), doc.getGender(), doc.getPremium(), doc.getSubStandardValue()  ) );
			
				kkulCallbackList.add(doc);
			}
			
			return kkulCallbackList;
			
		} catch(Exception e) {
			System.out.println("*******************************************************************");
			System.out.println("     ERROR : Check Node Server JS ");
			System.out.println(e.getMessage());
			System.out.println("*******************************************************************");
	        e.printStackTrace();
			return null;
	    }
		
	}
	
	@Override
	public String getUlFunSelection(String sisno) throws CommonException {
		// TODO Auto-generated method stub
		 
		String retString = "";
          
		try { 
			
			StringBuilder sql = new StringBuilder();
						
			sql.append(" select cb.sisno, fs.fundName, fs.percentSelect , f.fundsLevel ");
			sql.append(" from KK_OL_Callback cb, ");
			sql.append("      KK_UL_Funds_Selection fs, ");
			sql.append("      KK_UL_Funds f ");
			sql.append(" where cb.sisno = fs.SISNo ");
			sql.append("   and fs.fundName = f.fundsName ");
			sql.append("   and fs.validFlag = 1 ");
			sql.append("   and f.fundsLevel <> 99 ");
			sql.append("   and cb.sisno = " + sisno);

			Query query = em.createNativeQuery(sql.toString());
			List<Object[]> objs = query.getResultList();
			
			for (Object[] obj : objs) {
				
				retString = retString + "<tr><td>" + obj[1] + "</td><td>" + obj[2] + "</td><td>" + obj[3] + "</td></tr>";
				
			} 
			
			return retString;
		} catch(NoResultException e) {
	        return null;
	    }
	}

	@Override
	public void editStatus(KK_OL_Callback cb, String status) throws CommonException {
		// TODO Auto-generated method stub
		String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
		
		KK_OL_Callback existingCb = this.get(cb.getId());
		 
		existingCb.setKkFlag(status);
		existingCb.setKkNote(cb.getKkNote());

		existingCb.setQ1(cb.getQ1());
		existingCb.setQ2(cb.getQ2());
		existingCb.setQ3(cb.getQ3());
		existingCb.setQ4(cb.getQ4());
		existingCb.setQ5(cb.getQ5());
		existingCb.setQ6(cb.getQ6());
//		existingCb.setQ7(cb.getQ7());
//		existingCb.setQ8(cb.getQ8());
//		existingCb.setQ9(cb.getQ9());
//		existingCb.setQ10(cb.getQ10());

		existingCb.setNoteFromBranch(cb.getNoteFromBranch());
		existingCb.setKkTimestamp(new Timestamp(System.currentTimeMillis())); 
		existingCb.setCallBy(cb.getCallBy()); 
		if(existingCb.getFirstReviewTimestamp() == null || "".equals(existingCb.getFirstReviewTimestamp()) ){
			existingCb.setFirstReviewTimestamp(StringUtil.getTimestampStr("yyyy-MM-dd"));
		}
		
		em.merge(existingCb); 
//		em.flush();
	}
	
	@Override
	public KK_OL_Callback get(Integer id) {
		return (KK_OL_Callback) em.find(KK_OL_Callback.class, id);
	}
	 	
	
		
}
