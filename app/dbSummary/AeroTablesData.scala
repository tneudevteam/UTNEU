package dbSummary

import com.google.inject.{ImplementedBy, Singleton}

/**
  * Created by azula on 12.02.17.
  */

@ImplementedBy(classOf[AeroTablesDataImpl])
trait AeroTablesData {

  val ADMINS_NAMESPACE: String
  val ADMINS_SET: String
  val ADMIN_LOGIN_B: String
  val ADMIN_EMAIL_B: String
  val ADMIN_PASSWORD_B: String
  val ADMIN_INDEX_B: String

  val ROLES_NAMESPACE: String
  val ROLES_SET: String
  val ROLES_USER_ID_B: String
  val ROLES_B: String

}

@Singleton
class AeroTablesDataImpl extends AeroTablesData {

  private[this] val USERS_N: String = "ETUsers"
  private[this] val ID: String = "id"

  val ADMINS_NAMESPACE: String = USERS_N
  val ADMINS_SET: String = "admins"
  val ADMIN_LOGIN_B: String = "login"
  val ADMIN_EMAIL_B: String = "email"
  val ADMIN_PASSWORD_B: String = "password"
  val ADMIN_INDEX_B = ADMINS_SET

  val ROLES_NAMESPACE: String = USERS_N
  val ROLES_SET: String = "roles"
  val ROLES_USER_ID_B = ID
  val ROLES_B = "userRoles"

}
