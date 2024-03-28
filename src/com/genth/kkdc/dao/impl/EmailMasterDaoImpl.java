/**
 * 
 */
package com.genth.kkdc.dao.impl;

import java.util.List;

import javax.persistence.*;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.genth.kkdc.common.util.ErrorUtil;
import com.genth.kkdc.dao.EmailMasterDao;
import com.genth.kkdc.domain.EmailMaster;
import com.genth.kkdc.exception.CommonException;

/**
 * @author Thanompong.W
 *
 */
@Service("emailMasterDao")
@Transactional(rollbackFor = CommonException.class)
public class EmailMasterDaoImpl implements EmailMasterDao {
	
	private static Logger logger = Logger.getLogger(EmailMasterDaoImpl.class);
	
	@PersistenceContext(unitName="KK_UL_Callback")
	private EntityManager em;

	public EmailMasterDaoImpl() {
	}

	/**
	 * @param em the entityManager to set
	 */
	public void setEntityManager(EntityManager entityManager) {
		this.em = entityManager;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EmailMaster> getAll() throws CommonException {
		try	{
			return em.createQuery("SELECT a FROM EmailMaster a ORDER BY a.id").getResultList();
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw ErrorUtil.generateError("MSTD0006AERR", ex.getMessage());
		}
	}
	
	@Override
	public EmailMaster get(Integer id) {
		return (EmailMaster) em.find(EmailMaster.class, id);
	}
	
	@Override
	public void edit(EmailMaster mail) {
		
		EmailMaster exitingMail = this.get(mail.getId());

		exitingMail.setAgentNumber( mail.getAgentNumber());
		exitingMail.setReinsurerName( mail.getReinsurerName());
		exitingMail.setFrom(	  mail.getFrom());
		exitingMail.setTo(	  mail.getTo());
		exitingMail.setCc(	  mail.getCc());
		exitingMail.setBcc(		  mail.getBcc());
		exitingMail.setMessage(		  mail.getMessage());

		exitingMail.setUpdatedBy(      mail.getUpdatedBy());
		exitingMail.setUpdatedDate(	  mail.getUpdatedDate());
		// Save updates
		em.merge(exitingMail);
	}
	
	@Override
	public void delete(Integer id) {
		
		// Retrieve existing configvalue card via id
		EmailMaster email = this.get(id);
		
		// Delete
		em.remove(email);
	}

	@Override
	public void add(EmailMaster email) {
		// Persists to db
		em.persist(email);
	}

	@Override
	public EmailMaster getEmail(String agentNumber) throws CommonException {
		try	{
			List<EmailMaster> email = em.createQuery("SELECT a FROM EmailMaster a WHERE a.agentNumber = '"+agentNumber+"'").getResultList();
			return email.get(0);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw ErrorUtil.generateError("MSTD0006AERR", ex.getMessage());
		}
	}

	
	
}
