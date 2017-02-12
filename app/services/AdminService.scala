package services

import com.google.inject.{ImplementedBy, Inject, Singleton}
import models.aeroModels.AdminsDB
import models.models.NewAdmin

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * Created by azula on 12.02.17.
  */

@ImplementedBy(classOf[AdminServiceImpl])
trait AdminService {

  def create(newAdmin: NewAdmin): Future[Unit]

}


@Singleton
class AdminServiceImpl @Inject()(adminsDB: AdminsDB) extends AdminService {

  def create(newAdmin: NewAdmin) = adminsDB.create(newAdmin)

}
