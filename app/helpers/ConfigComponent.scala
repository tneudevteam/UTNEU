package helpers

import com.google.inject.{ImplementedBy, Singleton}
import com.typesafe.config.{Config, ConfigFactory}
import play.api.Logger

/**
  * Created by azula on 12.02.17.
  */

@ImplementedBy(classOf[ConfigComponentImpl])
trait ConfigComponent {
  val logger: Logger
  val config: Config
}

@Singleton
class ConfigComponentImpl extends ConfigComponent {

  val logger: Logger = Logger("Global")
  val config: Config = ConfigFactory.load()

}
