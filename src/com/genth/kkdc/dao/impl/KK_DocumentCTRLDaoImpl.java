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
import com.genth.kkdc.dao.KK_DocumentDao;
import com.genth.kkdc.dao.KK_Document_CTRLDao;
import com.genth.kkdc.domain.KK_Document;
import com.genth.kkdc.domain.KK_Document_CTRL;
import com.genth.kkdc.domain.User;
import com.genth.kkdc.domain.UserRole;
import com.genth.kkdc.exception.CommonException;

/**
 * @author Thanompong.W
 *
 */
@Service("KK_Document_CTRLDao")
@Transactional(rollbackFor = CommonException.class)
public class KK_DocumentCTRLDaoImpl implements KK_Document_CTRLDao {
	
	private static Logger logger = Logger.getLogger(KK_DocumentCTRLDaoImpl.class);
	
	@PersistenceContext(unitName="KK_UL_Callback")
	private EntityManager em;

	public KK_DocumentCTRLDaoImpl() {
	}

	/**
	 * @param em the entityManager to set
	 */
	public void setEntityManager(EntityManager entityManager) {
		this.em = entityManager;
	}

	@Override
	public List<KK_Document_CTRL> getAll() throws CommonException {
		
		try {

			StringBuilder sql = new StringBuilder();
			
			sql.append("SELECT M.* FROM KK_Document_CTRL M ");
			
			Query query = em.createNativeQuery(sql.toString(), KK_Document_CTRL.class);
						
			List<KK_Document_CTRL> kkDocList =query.getResultList();
			return kkDocList;
			
		} catch(NoResultException e) {
	        return null;
	    }
		
	}
	
	
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<KK_Document_CTRL> getAll() throws CommonException {
//		
//		try {
//			
//			StringBuilder sqlDoc = new StringBuilder();
//			
//			sqlDoc.append(" SELECT channel,item,Item_description  ");
//			sqlDoc.append(" FROM KK_Document_CTRL ");
//			sqlDoc.append(" ORDER BY channel,item ");
//
//			
//			Query query = em.createNativeQuery(sqlDoc.toString(), KK_Document_CTRL.class);
//			List<KK_Document_CTRL> kkDocList = query.getResultList();  
//			 
//			
//			
//			return kkDocList;
//			
//		} catch(NoResultException e) {
//	        return null;
//	    } 
//	}

	@Override
	public KK_Document_CTRL get(Integer id) {
		// TODO Auto-generated method stub
		return (KK_Document_CTRL) em.find(KK_Document_CTRL.class, id);
	}
	 
		
}
