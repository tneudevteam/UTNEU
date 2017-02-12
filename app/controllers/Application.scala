package controllers

import com.google.inject.{Inject, Singleton}
import helpers.ConfigComponent
import models.models.NewAdmin
import play.api.mvc._
import services.AdminService
import scala.concurrent.ExecutionContext.Implicits.global


@Singleton
class Application @Inject()(adminService: AdminService,
                            configComponent: ConfigComponent) extends Controller {

  import configComponent.config

  def index = Action {
    val newAdmin = NewAdmin(config.getString("mock.admin.email"), config.getString("mock.admin.password"))
    adminService.create(newAdmin)
      .map(_ ⇒ println("Done"))
      .recover{case ex ⇒ println(ex.getMessage)}
    Ok
  }

}