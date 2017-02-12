package helpers

import com.google.inject.{ImplementedBy, Singleton}

/**
  * Created by azula on 12.02.17.
  */
@ImplementedBy(classOf[ErrorMessagesImpl])
trait ErrorMessages{

  val AERO_ERROR: (String) ⇒ String

}

@Singleton
class ErrorMessagesImpl extends ErrorMessages {

  val AERO_ERROR = (message: String) ⇒ s"Aerospike error: $message"

}
