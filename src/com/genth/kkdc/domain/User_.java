package com.genth.kkdc.domain;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2554-10-13T16:45:09.687+0700")
@StaticMetamodel(User.class)
public class User_ {
	public static volatile SingularAttribute<User, Integer> id;
	public static volatile SingularAttribute<User, String> createdBy;
	public static volatile SingularAttribute<User, Timestamp> createdDate;
	public static volatile SingularAttribute<User, String> email;
	public static volatile SingularAttribute<User, String> firstName;
	public static volatile SingularAttribute<User, Integer> invalidLogin;
	public static volatile SingularAttribute<User, Timestamp> lastLoginTime;
	public static volatile SingularAttribute<User, String> lastName;
	public static volatile SingularAttribute<User, String> password;
	public static volatile SingularAttribute<User, String> updatedBy;
	public static volatile SingularAttribute<User, Timestamp> updatedDate;
	public static volatile SingularAttribute<User, String> userName;
	public static volatile SingularAttribute<User, Status> status;
	public static volatile SingularAttribute<User, PasswordStatus> passwordStatus;
	public static volatile ListAttribute<User, UserRole> userRoles;
}
