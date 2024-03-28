package com.genth.kkdc.domain;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2554-10-11T09:34:39.000+0700")
@StaticMetamodel(UserRole.class)
public class UserRole_ {
	public static volatile SingularAttribute<UserRole, UserRolePK> id;
	public static volatile SingularAttribute<UserRole, String> createdBy;
	public static volatile SingularAttribute<UserRole, Timestamp> createdDate;
	public static volatile SingularAttribute<UserRole, String> updatedBy;
	public static volatile SingularAttribute<UserRole, Timestamp> updatedDate;
	public static volatile SingularAttribute<UserRole, Role> role;
}
