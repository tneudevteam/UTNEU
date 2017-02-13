package models.aeroModels

import java.util
import java.util.UUID

import Global.UUIDString
import com.aerospike.client.Key
import com.google.inject.{ImplementedBy, Inject, Singleton}
import dbSummary.{AeroTablesData, DBScheme}
import helpers.Roles
import models.models.NewAdmin

import scala.concurrent.{ExecutionContext, Future}

/**
  * Created by azula on 12.02.17.
  */
@ImplementedBy(classOf[AdminsDBImpl])
trait AdminsDB {
  def create(newAdmin: NewAdmin)(implicit ex: ExecutionContext): Future[Unit]
}

@Singleton
class AdminsDBImpl @Inject() (dBScheme: DBScheme,
                              data: AeroTablesData) extends AdminsDB {

  import data._
  import dBScheme._

//  private[this] implicit def toKey(uid: UUID): Key = new Key(ADMINS_N, ADMINS_SET, uid.toString)
  private[this] def adminTableKey(id: UUIDString = UUID.randomUUID().toString): Key =
    new Key(ADMINS_NAMESPACE, ADMINS_SET, id)

  private[this] def rolesTableKey(id: UUIDString): Key =
    new Key(ROLES_NAMESPACE, ROLES_SET, id)

  def create(newAdmin: NewAdmin)(implicit ex: ExecutionContext): Future[Unit] = {
    val id = UUID.randomUUID().toString
    val adminTableBins = Vector(
      bin(ADMIN_EMAIL_B)(newAdmin.email),
      bin(ADMIN_LOGIN_B)(newAdmin.login),
      bin(ADMIN_PASSWORD_B)(newAdmin.password),
      bin(ADMIN_INDEX_B)(ADMIN_INDEX_B)
    )
    val rolesBins = {
      val list = new util.ArrayList[String]
      list.add(Roles.Admin.toString)
      Vector(
        bin(ROLES_B)(list),
        bin(ROLES_USER_ID_B)(id)
      )
    }
    dBScheme.createWithKey(adminTableBins)(adminTableKey(id))
    dBScheme.create(rolesBins)(rolesTableKey(id))
  }

}
