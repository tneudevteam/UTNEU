package models.aeroModels

import java.util.UUID

import com.aerospike.client.Key
import com.google.inject.{ImplementedBy, Inject, Singleton}
import dbSummary.{AeroTablesData, DBScheme}
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
  private[this] def newKey: Key = new Key(ADMINS_N, ADMINS_SET, UUID.randomUUID().toString)

  def create(newAdmin: NewAdmin)(implicit ex: ExecutionContext): Future[Unit] = {
    val bins = Vector(bin(ADMIN_LOGIN_B)(newAdmin.login), bin(ADMIN_PASSWORD_B)(newAdmin.password))
    dBScheme.create(bins)(newKey)
  }

}
