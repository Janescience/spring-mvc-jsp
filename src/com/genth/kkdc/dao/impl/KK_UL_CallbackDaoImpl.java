/**
 * 
 */
package com.genth.kkdc.dao.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.genth.kkdc.common.util.StringUtil;
import com.genth.kkdc.dao.KK_UL_CallbackDao;
import com.genth.kkdc.domain.KK_UL_Callback;
import com.genth.kkdc.exception.CommonException;
import com.genth.kkdc.service.CalculateCOI;

/**
 * @author Thanompong.W
 *
 */
@Service("KK_UL_CallbackDao")
@Transactional(rollbackFor = Exception.class)
public class KK_UL_CallbackDaoImpl implements KK_UL_CallbackDao {
	
	private static Logger logger = Logger.getLogger(KK_UL_CallbackDaoImpl.class);

	private static final String COMPLETED = "Completed";
	
	@PersistenceContext(unitName="KK_UL_Callback")
	private EntityManager em;
	
	@PersistenceContext(unitName="KKGENBIZ")
	private EntityManager emKKGENBIZ;

	public KK_UL_CallbackDaoImpl() {
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
	public List<KK_UL_Callback> getAll(String customerName,String searchStatus)
			throws CommonException {
		return getAll(customerName, searchStatus, null, null);
	}
	
	@Override
	public List<KK_UL_Callback> getAll(String customerName,String searchStatus, Date start, Date end)
			throws CommonException {
		// TODO Auto-generated method stub
		try {
			
			
			String whereStr = "";

			List<KK_UL_Callback> kkulCallbackList = new ArrayList<KK_UL_Callback>();
			
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
				sql.append(" CONVERT(varchar, CAST(sp.SumInsured AS money), 1) as SumInsured, ");
				sql.append(" sc.gender, "); 
				//sql.append(" sc.age, "); 
				sql.append(" isnull(insp.Insured_Age, sc.age) as age, "); 
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
				sql.append(" isnull(STUFF( ( SELECT ','+fundName  "); 
							sql.append(" FROM KK_UL_Funds_Selection y "); 
							sql.append(" WHERE y.sisno = cl.sisno "); 
							sql.append(" AND y.validFlag = 1 "); 
							sql.append(" ORDER BY sisno FOR XML PATH('') "); 
							sql.append(" ), 1, 1, ''),'') as fundSelectList, "); 
				sql.append(" isnull( STUFF( ( SELECT ','+CONVERT(NVARCHAR(6), percentSelect)+'%' "); 
							sql.append(" FROM KK_UL_Funds_Selection y "); 
							sql.append(" WHERE y.sisno = cl.sisno "); 
							sql.append(" AND y.validFlag = 1 "); 
							sql.append(" ORDER BY sisno FOR XML PATH('') "); 
							sql.append(" ), 1, 1, ''),'') as percentSelectList, "); 
				sql.append(" isnull( STUFF( ( SELECT ','+z.fundsLevel "); 
							sql.append(" FROM KK_UL_Funds_Selection y, KK_UL_Funds z "); 
							sql.append(" WHERE y.sisno = cl.sisno "); 
							sql.append(" AND y.fundName = z.fundsName "); 
							sql.append(" AND y.validFlag = 1 "); 
							sql.append(" ORDER BY sisno FOR XML PATH('') ");  
							sql.append("  ), 1, 1, ''),'') as riskSelectList, "); 
				sql.append(" isnull( CONVERT(CHAR(23),cl.kk_timestamp, 103),'') callDate , isnull( CONVERT(CHAR(23),cl.kk_timestamp, 108),'') callTime, ");
				sql.append(" isnull(cl.callBy,'') callBy, ");
				//sql.append(" case when q1 = q2 and q2=q3 and q3=q4 and q4=q5 and q5=q6 and q6=q7 and q7 = 1 then 'Yes' else 'No' end as isConfirm ");
				sql.append(" case when cl.[kk_flag]  = 'Y' then 'Yes' else 'No' end as isConfirm , ");
				sql.append(" isnull(ks.sub_standard,1) subStandard , ");
				sql.append(" case when cl.first_review_timestamp is null then '-' else CONVERT(varCHAR(23), cl.first_review_timestamp,103) end as first_review_timestamp ");
				
			sql.append(" FROM kk_ul_callback cl ");
			sql.append(" inner join sisclient sc  on cl.sisno = sc.sisno ");
			sql.append(" inner join sis s         on cl.sisno = s.sisno ");
			sql.append(" inner join sisplan sp    on cl.sisno = sp.sisno ");
			sql.append(" inner join kk_staff sf   on s.AgentCode = 'KK'+sf.userid ");
			sql.append(" inner join SISPlanSetup plan_setup on plan_setup.planCode = sp.planCode ");
			sql.append(" left join KK_SubStandard_Log ks on s.appno = ks.appno and ks.status = 'Active' ");
			sql.append(" LEFT JOIN KK_Create_KKINSP2_Data insp on insp.sisno = s.sisno ");
			sql.append(" WHERE s.KKRefNo is not null and s.AppCuStatus<>'RM' and insp.WSStatus<>'RM' ");
			sql.append( whereStr );
			sql.append(" order by cl.[kk_flag], cl.createdate desc, cl.id ");
			
			System.out.println("*******************************************************************");
			System.out.println("SQL KK_UL_Callback:\n"+sql.toString());
			System.out.println("*******************************************************************");
			
			Query query = em.createNativeQuery(sql.toString());
			List<Object[]> objs = query.getResultList();  
			
			for (Object[] obj : objs) {
				
				KK_UL_Callback doc = new KK_UL_Callback(obj,0);				
				//System.out.println(doc.getAppNo()+":"+doc.getCreatedDate());
				//if(doc.getAppNo().equals("8899999921"))
				//{
				//	System.out.println("16/08/2019");
				//}
				doc.setFrontEndFee( CalculateCOI.getFrontEndFee(doc.getPlanCode(), doc.getPremium().replaceAll(",", "") ) );
				doc.setCoiFee( CalculateCOI.sendGet(doc.getPlanCode(), doc.getAge(), doc.getGender(), doc.getPremium().replaceAll(",", ""), doc.getSubStandardValue(),doc.getSumInsured().replaceAll(",", "")  ) );
				doc.setMultipleSA(CalculateCOI.getMultipleSA(doc.getSumInsured(), doc.getPremium()));
						
				kkulCallbackList.add(doc);
			}
			
			return kkulCallbackList;
			
		} catch(Exception e) {
			System.out.println("*******************************************************************");
			System.out.println("     ERROR : Check Node Server JS ");
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
			sql.append(" from KK_UL_Callback cb, ");
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
	public void editStatus(KK_UL_Callback cb, String status) throws CommonException {
		// TODO Auto-generated method stub
		String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
		KK_UL_Callback existingCb = this.get(cb.getId());
		 
		existingCb.setKkFlag(status);
		existingCb.setKkNote(cb.getKkNote());

		existingCb.setQ1(cb.getQ1());
		existingCb.setQ2(cb.getQ2());
		existingCb.setQ3(cb.getQ3());
		existingCb.setQ4(cb.getQ4());
		existingCb.setQ5(cb.getQ5());
		existingCb.setQ6(cb.getQ6());
		existingCb.setQ7(cb.getQ7());
		existingCb.setQ8(cb.getQ8());
		existingCb.setQ9(cb.getQ9());
		existingCb.setQ10(cb.getQ10());

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
	public KK_UL_Callback get(Integer id) {
		return (KK_UL_Callback) em.find(KK_UL_Callback.class, id);
	}
	 	
	
		
}
