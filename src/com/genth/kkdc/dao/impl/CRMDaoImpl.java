/**
 * 
 */
package com.genth.kkdc.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import javax.persistence.*;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.genth.kkdc.dao.CRMDao;
import com.genth.kkdc.domain.CRMCleanup;
 
/**
 * @author Thanompong.W
 *
 */
@Service("crmDao")
@Transactional
public class CRMDaoImpl implements CRMDao {
	
	private static Logger logger = Logger.getLogger("dao");
	
	private JdbcTemplate cmsJdbcTemplate;
	
	private JdbcTemplate crmJdbcTemplate;
	
	private JdbcTemplate appJdbcTemplate;
	
	private JdbcTemplate dwJdbcTemplate;
	private JdbcTemplate kkgenbizJdbcTemplate;
	
	@PersistenceContext(unitName="KK_UL_Callback")
	private EntityManager entityManager;

	public CRMDaoImpl() {
	}

	/**
	 * @param cmsJdbcTemplate the cmsJdbcTemplate to set
	 */
	public void setCmsJdbcTemplate(JdbcTemplate cmsJdbcTemplate) {
		this.cmsJdbcTemplate = cmsJdbcTemplate;
	}

	public void setKkgenbizJdbcTemplate(JdbcTemplate kkgenbizJdbcTemplate) {
		this.kkgenbizJdbcTemplate = kkgenbizJdbcTemplate;
	}

	/**
	 * @param crmJdbcTemplate the crmJdbcTemplate to set
	 */
	public void setCrmJdbcTemplate(JdbcTemplate crmJdbcTemplate) {
		this.crmJdbcTemplate = crmJdbcTemplate;
	}

	/**
	 * @param appJdbcTemplate the appJdbcTemplate to set
	 */
	public void setAppJdbcTemplate(JdbcTemplate appJdbcTemplate) {
		this.appJdbcTemplate = appJdbcTemplate;
	}

	/**
	 * @param dwJdbcTemplate the dwJdbcTemplate to set
	 */
	public void setDwJdbcTemplate(JdbcTemplate dwJdbcTemplate) {
		this.dwJdbcTemplate = dwJdbcTemplate;
	}

	/**
	 * @param entityManager the entityManager to set
	 */
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
 
	@Override
	public void cleanup(String fileName) {
		// Prepare our SQL statement using Unnamed Parameters style
		StringBuilder sql = new StringBuilder();
		
		sql.append("UPDATE COMMUNICATION ");
		sql.append("SET COMM_STATUS = 'Cancelled' ");
		sql.append("WHERE COMM_DELETED IS NULL ");
		sql.append("AND   COMM_STATUS = 'Pending' ");
		sql.append("AND   LTRIM(RTRIM(COMM_CVSUBJECT)) = ? ");
	   
	    // Assign values to parameters
	    Object[] parameters = new Object[] { fileName };
	   
	    // Delete
	    this.crmJdbcTemplate.update(sql.toString(), parameters);
	}

	@Override
	public void cleanup(List<String> list) {
		
		for (String fileName : list) {
			this.cleanup(fileName);
		}
	}
   
	private void createExportTempData() {
		// Prepare our SQL statement
		StringBuilder sql = new StringBuilder();
		
		sql.append("INSERT INTO TB_T_EXPORT (CHDRNUM, TTMPRCNO, LIFCNUM, LINSNAME, "); 
		sql.append("						 BANKACCKEY, BANKACCDSC, CBILLAMT, EFFDATE02, "); 
		sql.append("						 RDOCNUM, PREMSUSP, PDDAY, PPDAY, CCDATE, PTDATE, "); 
		sql.append("						 BTDATE, ZBKTRANCDE, DESCR, PAYRNUM, PAYERNAME, "); 
		sql.append("						 ZHPHONE, ZOPHONE, ZMPHONE, STCAL, TOTALCASE, "); 
		sql.append("						 LAPSETYPE, TRAND, SA, MOP, OSMTHS, OSPRM, PLANNAME, "); 
		sql.append("						 SYSTEMDATE, CANCELTYPE, SENTDATE, RECEIVEDATE, "); 
		sql.append("						 REMARK, STATUS) ");
		sql.append("SELECT I.CHDRNUM, I.TTMPRCNO, I.LIFCNUM, I.LINSNAME, I.BANKACCKEY, I.BANKACCDSC, "); 
		sql.append("       G.CBILLAMT, I.EFFDATE02, I.RDOCNUM, I.PREMSUSP, I.PDDAY, I.PPDAY, I.CCDATE, "); 
		sql.append("       I.PTDATE, I.BTDATE, I.ZBKTRANCDE, I.DESCR, I.PAYRNUM, I.PAYERNAME, I.ZHPHONE, "); 
		sql.append("       I.ZOPHONE, I.ZMPHONE, I.STCAL, I.TOTALCASE, I.LAPSETYPE, I.TRAND, I.SA, I.MOP, "); 
		sql.append("       I.OSMTHS, I.OSPRM, I.PLANNAME, I.SYSTEMDATE, I.CANCELTYPE, I.SENTDATE, I.RECEIVEDATE, "); 
		sql.append("       '-', 1 ");
		sql.append("FROM TB_T_IMPORT I, TB_T_GROUP G "); 
		sql.append("WHERE I.CHDRNUM = G.CHDRNUM "); 
		sql.append("AND   I.RUNNINGID = G.RUNNINGID ");
		
		this.entityManager.createNativeQuery(sql.toString()).executeUpdate();
		
		this.entityManager.flush();
	}
	
	private Long deleteNotCall() {
		
		StringBuilder sql = new StringBuilder();
		
		sql.append("DELETE TB_T_EXPORT ");
		sql.append("FROM TB_T_EXPORT X, TB_M_NOTCALL C ");
		sql.append("WHERE X.CHDRNUM = C.CHDRNUM");
		
		Query query = this.entityManager.createNativeQuery(sql.toString());
		
		return new Long(query.executeUpdate());
	}
	
	private Integer countNotCall() {
		
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT COUNT(T.CHDRNUM) ");
		sql.append("FROM TB_T_IMPORT T, TB_M_NOTCALL C ");
		sql.append("WHERE T.CHDRNUM = C.CHDRNUM");
		
		Query query = this.entityManager.createNativeQuery(sql.toString());
		
		return (Integer) query.getSingleResult();
	}
	
	private Integer countNotFound() {
		
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT COUNT(A.STCAL) COUNT_NOT_FOUND ");
		sql.append("FROM TB_T_IMPORT A ");
		sql.append("WHERE NOT EXISTS (SELECT B.CHDRNUM ");
        sql.append("                  FROM TB_M_NOTCALL B ");
        sql.append("                  WHERE B.CHDRNUM = A.CHDRNUM) ");
		sql.append("AND  NOT EXISTS (SELECT DISTINCT C.TM_CHANNEL_CODE ");
        sql.append("                 FROM TB_M_TM_CHANNEL C, TB_M_ASSIGN_AGENT D ");
        sql.append("                 WHERE D.TM_CHANNEL_ID = C.ID ");
        sql.append("                 AND C.TM_CHANNEL_CODE = A.STCAL) ");
		
		Query query = this.entityManager.createNativeQuery(sql.toString());
		
		return (Integer) query.getSingleResult();
	}
	
	private void createChannelGroup() {
		StringBuilder sql = new StringBuilder();
		
		sql.append("INSERT INTO TB_T_GROUP ");
		sql.append("SELECT MIN(RUNNINGID) AS RUNNINGID, CHDRNUM, SUM(CBILLAMT) AS CBILLAMT ");
		sql.append("FROM TB_T_IMPORT I ");
		sql.append("WHERE NOT EXISTS(SELECT C.CHDRNUM ");
		sql.append("                 FROM TB_M_NOTCALL C "); 
		sql.append("                 WHERE I.CHDRNUM = C.CHDRNUM) ");
		sql.append("GROUP BY CHDRNUM, TTMPRCNO, BANKACCKEY ");
		
		this.entityManager.createNativeQuery(sql.toString()).executeUpdate();
	}
	
	private void updateUnsuccessExportData() {
		
		Query query = this.entityManager.createNativeQuery("SELECT CHDRNUM FROM TB_T_EXPORT ");
				
		@SuppressWarnings("unchecked")
		List<String> clientNoList = query.getResultList();
		
		this.entityManager.close();
		
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT CHDRNUM, ");
		sql.append("	   PBILLFREQ, ");
		sql.append("	   RTRIM(LTRIM(C_ISALUTL_DESC + ' ' + ILGIVNAME + ' ' + ILSURNAME)) AS LINSNAME ");
		sql.append("FROM LMCONT P ");
		sql.append("WHERE P.CHDRNUM = ? ");
		
		String updateSql = "UPDATE TB_T_EXPORT SET MODE = ?, LINSNAME = ? WHERE CHDRNUM = ? ";
		
		for (String cilentNo : clientNoList) {
			
			Object[] args = new Object[] { cilentNo };
			
			Map<String, Object> result = 
					this.dwJdbcTemplate.queryForMap(sql.toString(), args);
			
			
			if (result != null && !result.isEmpty()) {
				query = this.entityManager.createNativeQuery(updateSql);
				
				int parameterIndex = 1;
				
				query.setParameter(parameterIndex++, result.get("PBILLFREQ"));
				query.setParameter(parameterIndex++, result.get("LINSNAME"));
				query.setParameter(parameterIndex++, cilentNo);
				
				query.executeUpdate();
			}
		}
		
		this.entityManager.flush();
		
		/*String deleteSql = "DELETE TB_T_EXPORT WHERE STCAL = 'STD' OR STCAL = 'ST1'";
		
		this.entityManager.createNativeQuery(deleteSql).executeUpdate();
		
		this.entityManager.flush();*/
	}
	 
	private void mergePolicyByChannel() {
		StringBuilder sqlMergePolicy = new StringBuilder();
		
		sqlMergePolicy.append("SELECT A.LIFCNUM, MIN(A.CHDRNUM) CHDRNUM, A.CBILLAMT, STCAL ");
		sqlMergePolicy.append("FROM TB_T_EXPORT A ");
		sqlMergePolicy.append("WHERE EXISTS ( ");
		sqlMergePolicy.append("      SELECT 1 FROM ( ");
		sqlMergePolicy.append("            SELECT LIFCNUM, STCAL, MAX(CBILLAMT) MAX_PREMIUM, COUNT(1) N_POLICY ");
		sqlMergePolicy.append("            FROM TB_T_EXPORT ");
		sqlMergePolicy.append("            GROUP BY LIFCNUM, STCAL ");
		sqlMergePolicy.append("            HAVING COUNT(1) > 1 ");
		sqlMergePolicy.append("      ) B ");
		sqlMergePolicy.append("      WHERE A.LIFCNUM = B.LIFCNUM ");
		sqlMergePolicy.append("      AND A.CBILLAMT = B.MAX_PREMIUM ");
		sqlMergePolicy.append(") ");
		sqlMergePolicy.append("GROUP BY A.LIFCNUM, A.CBILLAMT, STCAL ");
		
		StringBuilder sqlGetPremium = new StringBuilder();
		sqlGetPremium.append("SELECT CHDRNUM, CBILLAMT, STCAL ");
		sqlGetPremium.append("FROM TB_T_EXPORT ");
		sqlGetPremium.append("WHERE LIFCNUM = ? ");
		sqlGetPremium.append("AND STCAL = ? ");
		sqlGetPremium.append("ORDER BY CBILLAMT DESC ");
		
		RowMapper<Map<String, Object>> mapper = new RowMapper<Map<String, Object>>() {
			
			@Override
			public Map<String, Object> mapRow(ResultSet rs, int i) throws SQLException {
				Map<String, Object> resultMap = new HashMap<String, Object>();
				
				resultMap.put("CHDRNUM", rs.getString("CHDRNUM"));
				resultMap.put("LIFCNUM", rs.getString("LIFCNUM"));
				resultMap.put("STCAL", rs.getString("STCAL"));
				
				return resultMap;
			}
		};
		
		List<Map<String, Object>> mergeList = 
				this.cmsJdbcTemplate.query(sqlMergePolicy.toString(), mapper);
		
		Object[] args = null;
		List<Map<String, Object>> remarkList = null;
		
		for (Map<String, Object> mergeInfo : mergeList) {
			
			mapper = new RowMapper<Map<String, Object>>() {
				
				@Override
				public Map<String, Object> mapRow(ResultSet rs, int i) throws SQLException {
					Map<String, Object> resultMap = new HashMap<String, Object>();
					
					resultMap.put("CHDRNUM", rs.getString("CHDRNUM"));
					resultMap.put("CBILLAMT", new Float(rs.getFloat("CBILLAMT")));
					resultMap.put("STCAL", rs.getString("STCAL"));
					
					return resultMap;
				}
			};

			args = new Object[] { (String) mergeInfo.get("LIFCNUM"), 
			                      (String) mergeInfo.get("STCAL") };
			
			remarkList = this.cmsJdbcTemplate.query(sqlGetPremium.toString(), args, mapper);
			
			StringBuilder sqlUpdateRemark = new StringBuilder();

			sqlUpdateRemark.append("UPDATE TB_T_EXPORT SET REMARK = ");
			for (int i = 0; i < remarkList.size(); i++) {
				Map<String, Object> remarkMap = remarkList.get(i);
				
				if (i == 0) {
					sqlUpdateRemark.append("'" + (String) remarkMap.get("CHDRNUM") + "' ");
					sqlUpdateRemark.append(" + '(' ");
					sqlUpdateRemark.append(" + '" + (Float) remarkMap.get("CBILLAMT") + "' ");
					sqlUpdateRemark.append(" + ')' ");
				} else {
					sqlUpdateRemark.append(" + ':' + '" + (String) remarkMap.get("CHDRNUM") + "' ");
					sqlUpdateRemark.append(" + '(' ");
					sqlUpdateRemark.append(" + '" + (Float) remarkMap.get("CBILLAMT") + "' ");
					sqlUpdateRemark.append(" + ')' ");
				}
			}
			sqlUpdateRemark.append("WHERE LIFCNUM =  ");
			sqlUpdateRemark.append("'" + (String) mergeInfo.get("LIFCNUM") + "' ");
			sqlUpdateRemark.append("AND CHDRNUM = ");
			sqlUpdateRemark.append((String) mergeInfo.get("CHDRNUM"));

			Query query = this.entityManager.createNativeQuery(sqlUpdateRemark.toString());
			query.executeUpdate();
		}
		
		StringBuilder sqlDeleteDuplicate = new StringBuilder();
		sqlDeleteDuplicate.append("DELETE TB_T_EXPORT ");
		sqlDeleteDuplicate.append("FROM TB_T_EXPORT ");
		sqlDeleteDuplicate.append("WHERE LIFCNUM = ? ");
		sqlDeleteDuplicate.append("AND STCAL = ? ");
		sqlDeleteDuplicate.append("AND CHDRNUM <> ? ");
		
		for (Map<String, Object> mergeInfo : mergeList) {
			
			int parameterIndex = 1;
			
			Query query = this.entityManager.createNativeQuery(sqlDeleteDuplicate.toString());
			
			query.setParameter(parameterIndex++, (String) mergeInfo.get("LIFCNUM"));
			query.setParameter(parameterIndex++, (String) mergeInfo.get("STCAL"));
			query.setParameter(parameterIndex++, (String) mergeInfo.get("CHDRNUM"));
			
			query.executeUpdate();
		}
	}
 
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getAgenCode() {
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT(GTAGNTNAME) AS GTAGNTNAME ");
		sql.append("FROM TB_T_EXPORT ");
		sql.append("ORDER BY GTAGNTNAME ");
		Query query = this.entityManager.createNativeQuery(sql.toString());

		return query.getResultList();
	}
	
	private void truncateTempTable() {
		
		// CLEAR TEMP TABLE (TB_T_IMPORT)
		this.entityManager.createQuery("DELETE FROM Import").executeUpdate();
		
		this.entityManager.flush();

		// CLEAR TEMP TABLE (TB_T_EXPORT)
		this.entityManager.createQuery("DELETE FROM Export").executeUpdate();
		
		this.entityManager.flush();
		
		// CLEAR TEMP TABLE (TB_T_GROUP)
		this.entityManager.createQuery("DELETE FROM Group").executeUpdate();
		
		this.entityManager.flush();
		
	}
 
	@Override
	public Map<String, String> getPolicyInfo(String policyNo) {
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT ");
		sql.append("    TTMPRCNO, ");
		sql.append("    LIFCNUM, ");
		sql.append("    LINSNAME, ");
		sql.append("    STCAL ");
		sql.append("FROM DD_REPORT ");
		sql.append("WHERE CHDRNUM = ? ");
		sql.append("AND TRANSACTION_TIME = (SELECT MAX(TRANSACTION_TIME) ");
		sql.append("                        FROM DD_REPORT ");
		sql.append("                        WHERE CHDRNUM = ?) ");
		
		Object[] args = new Object[] {
				policyNo, policyNo,
		};
		
		ResultSetExtractor<Map<String, String>> rse = new ResultSetExtractor<Map<String,String>>() {
			
			@Override
			public Map<String, String> extractData(ResultSet rs) throws SQLException,
					DataAccessException {
				
				Map<String, String> resultMap = null;
				if (rs.next()) {
					resultMap = new HashMap<String, String>();
					
					resultMap.put("applicationNo", StringUtils.trimToEmpty(rs.getString("TTMPRCNO")));
					resultMap.put("clientNo", StringUtils.trimToEmpty(rs.getString("LIFCNUM")));
					resultMap.put("insured", StringUtils.trimToEmpty(rs.getString("LINSNAME")));
					resultMap.put("tmChannel", StringUtils.trimToEmpty(rs.getString("STCAL")));
				}
				
				return resultMap;
			}
		};
		
		Map<String, String> resultMap = appJdbcTemplate.query(sql.toString(), args, rse);
		
		return resultMap;
	}

	@Override
	public List<CRMCleanup> getCleanupList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void populateUnsuccessData() {
		// TODO Auto-generated method stub
		
	}
 

}
