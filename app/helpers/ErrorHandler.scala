package helpers

import Global.ERight
import com.google.inject.{ImplementedBy, Singleton}

import scala.concurrent.Future
import scala.util.Try

/**
  * Created by azula on 12.02.17.
  */

@ImplementedBy(classOf[ErrorHandlerImpl])
trait ErrorHandler {

  def handleError[T](f: ⇒ T)(optionalMessage: String = ""): ERight[T]
  def tryAsync[T](f: ⇒ T): Future[T]

}

@Singleton
class ErrorHandlerImpl extends ErrorHandler{

  def handleError[T](f: ⇒ T)(optionalMessage: String = ""): ERight[T] =
    Try(Right(f))
      .recover {
        case ex ⇒
          val message =
            if (optionalMessage.isEmpty) ex.getMessage
            else s"$optionalMessage, ${ex.getMessage}"
          Left(message)
      }.get

  def tryAsync[T](f: ⇒ T): Future[T] = Future.fromTry(Try(f))

}
