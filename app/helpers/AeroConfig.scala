package helpers

import com.google.inject.{ImplementedBy, Inject, Singleton}

/**
  * Created by azula on 12.02.17.
  */

@ImplementedBy(classOf[AeroConfigImpl])
trait AeroConfig {
  def host: String
  def port: Int
  def maxConcurentCommands: Int
}

@Singleton
class AeroConfigImpl @Inject() (configComponent: ConfigComponent) extends AeroConfig {
  private[this] def aeroConfig = configComponent.config.getConfig("aerospike")
  def host: String = aeroConfig.getString("host")
  def port: Int = aeroConfig.getInt("port")
  def maxConcurentCommands = aeroConfig.getInt("maxConcurrentCommands")
}