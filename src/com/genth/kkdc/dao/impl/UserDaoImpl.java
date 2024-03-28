/**
 * 
 */
package com.genth.kkdc.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.genth.kkdc.dao.UserDao;
import com.genth.kkdc.domain.*;

/**
 * @author Thanompong.W
 *
 */
@Service("userDao")
@Transactional
public class UserDaoImpl implements UserDao {
	
	protected static Logger logger = Logger.getLogger("UserDao");
	
	@PersistenceContext(unitName="KK_UL_Callback")
	private EntityManager entityManager;

	public UserDaoImpl() {
	}

	/**
	 * @param entityManager the entityManager to set
	 */
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public User findByUserName(String userName) {
		try {
			StringBuilder sql = new StringBuilder();
			
			sql.append("SELECT u FROM User u ");
			sql.append("WHERE u.userName = :userName ");
			
			Query query = entityManager.createQuery(sql.toString());
			query.setParameter("userName", userName);
			
			User user = (User) query.getSingleResult();
			
			sql.setLength(0);
			sql.append("SELECT ur FROM UserRole ur ");
			sql.append("JOIN FETCH ur.role ");
			sql.append("WHERE ur.id.userId = :userId ");
			
			query = entityManager.createQuery(sql.toString());
			query.setParameter("userId", user.getId());
			
			@SuppressWarnings("unchecked")
			List<UserRole> userRoles = query.getResultList();
			
			for (UserRole userRole : userRoles) {
				Role role = entityManager.find(Role.class, userRole.getId().getRoleId());
				userRole.setRole(role);
			}
			
			user.setUserRoles(userRoles);
			
			return user;
		} catch(NoResultException e) {
	        return null;
	    }
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getAll() {
		
		List<User> users = entityManager.createQuery("SELECT u FROM User u").getResultList();
		
		for (User user : users) {
			Query query = entityManager.createQuery("SELECT ur FROM UserRole ur WHERE ur.id.userId = :userId");
			query.setParameter("userId", user.getId());
			
			List<UserRole> userRoles = query.getResultList();
			
			user.setUserRoles(userRoles);
		}
		
		return users;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Status> getUserStatusList() {
		return entityManager.createQuery("SELECT s FROM Status s").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Role> getRoleList() {
		return entityManager.createQuery("SELECT r FROM Role r").getResultList();
	}
	
	@Override
	public void add(User user) {
		// Persists to db
		entityManager.persist(user);
		entityManager.flush();
		
		String[] roleIds = user.getRole().split(",");
		
		User currentUser = this.findByUserName(user.getUserName());
		for (String roleId : roleIds) {
			UserRole userRole = new UserRole();
			
			userRole.setId(new UserRolePK(currentUser.getId(), Integer.parseInt(roleId)));
			userRole.setCreatedBy(user.getCreatedBy());
			userRole.setCreatedDate(user.getCreatedDate());
			
			entityManager.persist(userRole);
		}
	}

	@Override
	public void edit(User user) {

		User exsitingUser = this.findByUserName(user.getUserName());
		
		Query query = entityManager.createQuery("SELECT s FROM Status s WHERE s.id = :id");
		query.setParameter("id", user.getStatus().getId());
		Status status = (Status) query.getSingleResult();
		
		exsitingUser.setEmail(user.getEmail());
		exsitingUser.setFirstName(user.getFirstName());
		exsitingUser.setLastName(user.getLastName());
		exsitingUser.setStatus(status);
		exsitingUser.setUpdatedBy(user.getUpdatedBy());
		exsitingUser.setUpdatedDate(user.getUpdatedDate());
		
		if (status.getId() == 1) {
			exsitingUser.setInvalidLogin(0);
		}
		
		entityManager.merge(exsitingUser);
		
		List<Integer> roleIds = new ArrayList<Integer>();
		
		for (String roleId : user.getRole().split(",")) {
			roleIds.add(Integer.parseInt(roleId));
		}
		
		// Case : new user role
		for (Integer roleId : roleIds) {
			UserRolePK userRoleId = new UserRolePK(exsitingUser.getId(), roleId);
			UserRole userRole = entityManager.find(UserRole.class, userRoleId);
			
			if (userRole == null) {
				userRole = new UserRole();
				userRole.setId(userRoleId);
				userRole.setCreatedBy(user.getUpdatedBy());
				userRole.setCreatedDate(user.getUpdatedDate());
				entityManager.persist(userRole);
				entityManager.flush();
			}		
		}
		
		// Case : remove user role
		for (UserRole existRole : exsitingUser.getUserRoles()) {
			if (!roleIds.contains(existRole.getId().getRoleId())) {
				entityManager.remove(existRole);
				entityManager.flush();
			}
		}
	}

	@Override
	public void delete(Integer id) {
		// Retrieve existing agent card via id
		User user = entityManager.find(User.class, id);
		
		// Delete
		entityManager.remove(user);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserRole> getAuthorities(Integer id) {
		
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT u FROM UserRole u ");
		sql.append("WHERE u.id.userId = :userId ");
		
		Query query = entityManager.createQuery(sql.toString());
		query.setParameter("userId", id);
		
		return query.getResultList();
	}

	@Override
	public void update(User user) {
		entityManager.merge(user);		
	}

	@Override
	public void resetPassword(User user) {
		User exsitingUser = entityManager.find(User.class, user.getId());
		
		exsitingUser.setPassword(user.getPassword());
		exsitingUser.setUpdatedBy(user.getUpdatedBy());
		exsitingUser.setUpdatedDate(user.getUpdatedDate());
		
		entityManager.merge(exsitingUser);
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<User> getQaStaff() {
		
		try {

			StringBuilder sql = new StringBuilder();
			
			sql.append("SELECT u.* from TB_M_USER u , TB_M_ROLE r, TB_M_USER_ROLE ur ");
			sql.append(" where u.USER_ID = ur.USER_ID ");
			sql.append(" and ur.ROLE_ID = r.ROLE_ID and r.ROLE_ID = ? ");
			sql.append(" and NOT EXISTS ");
			sql.append("   (SELECT '1' FROM TB_M_USER_ROLE iur ");
			sql.append("    WHERE iur.ROLE_ID <> 2 ");
			sql.append("    AND   ur.USER_ID = iur.USER_ID ) ");
			
			Query query = entityManager.createNativeQuery(sql.toString(), User.class);

			int role_staff = 2;
			query.setParameter(1, role_staff);		
			
			List<User> user =query.getResultList();
			return user;
		} catch(NoResultException e) {
	        return null;
	    }
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<User> getSupervisorList() {
		
		try {

			StringBuilder sql = new StringBuilder();
			
			sql.append("SELECT u.* from TB_M_USER u , TB_M_ROLE r, TB_M_USER_ROLE ur ");
			sql.append(" where u.USER_ID = ur.USER_ID ");
			sql.append(" and ur.ROLE_ID = r.ROLE_ID and r.ROLE_ID = ? ");
			
			Query query = entityManager.createNativeQuery(sql.toString(), User.class);

			int role_supervisor = 3;
			query.setParameter(1, role_supervisor);		
			
			List<User> user =query.getResultList();
			return user;
		} catch(NoResultException e) {
	        return null;
	    }
	}
}
