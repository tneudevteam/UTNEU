package dbSummary

import com.google.inject.{ImplementedBy, Singleton}

/**
  * Created by azula on 12.02.17.
  */

@ImplementedBy(classOf[AeroTablesDataImpl])
trait AeroTablesData {

   val ADMINS_N: String
   val ADMINS_SET: String
   val ADMIN_LOGIN_B: String
   val ADMIN_PASSWORD_B: String

}

@Singleton
class AeroTablesDataImpl extends AeroTablesData{

  private[this] val USERS_N: String = "ETUsers"
  val ADMINS_N: String = USERS_N
  val ADMINS_SET: String = "admins"
  val ADMIN_LOGIN_B: String = "login"
  val ADMIN_PASSWORD_B: String = "password"

}
