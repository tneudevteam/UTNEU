package dbSummary

import com.aerospike.client.async.AsyncClient
import com.aerospike.client.listener.WriteListener
import com.aerospike.client.policy.WritePolicy
import com.aerospike.client.{AerospikeException, Bin, Key}
import com.google.inject.{ImplementedBy, Inject, Singleton}
import helpers.{AeroConfig, ErrorHandler, ErrorMessages}

import scala.concurrent.{ExecutionContext, Future, Promise}
import scala.util.Try

/**
  * Created by azula on 12.02.17.
  */

@ImplementedBy(classOf[DBSchemeImpl])
trait DBScheme {

  def create(bins: Vector[Bin])(key: Key)(implicit ex: ExecutionContext): Future[Unit]
  def createWithKey(bins: Vector[Bin])(key: Key)(implicit ex: ExecutionContext): Future[Unit]
  def bin[T]: (String) ⇒ (T) ⇒ Bin

}

@Singleton
class DBSchemeImpl @Inject()(aeroConfig: AeroConfig,
                             errorHandler: ErrorHandler,
                             errorMessages: ErrorMessages,
                             policiesContainer: PoliciesContainer) extends DBScheme{

  import errorHandler._

  private[this] val asyncClient: Future[AsyncClient] = tryAsync(
    new AsyncClient(policiesContainer.asyncPolicy, aeroConfig.host, aeroConfig.port)
  )

  def create(bins: Vector[Bin])(key: Key)(implicit ex: ExecutionContext): Future[Unit] = {
    createRecord(policiesContainer.create, key, bins)
  }

  def createWithKey(bins: Vector[Bin])(key: Key)(implicit ex: ExecutionContext): Future[Unit] = {
    val policyWithKey = policiesContainer.create
    policyWithKey.sendKey = true
    createRecord(policyWithKey, key, bins)
  }

  def bin[T] = (name: String) ⇒ (value: T) ⇒ new Bin(name, value)

  private[this] def createRecord(policy: WritePolicy, key: Key, bins: Vector[Bin])
                                (implicit ex: ExecutionContext): Future[Unit] = {
    for {
      session ← asyncClient
      _ ← {
        val (writeL, promise) = formWriteListener
        Try(session.put(policy, writeL, key, bins: _*)).recover{ case err ⇒ promise.failure(err) }
        promise.future
      }
    } yield ()
  }

  private[this] def formWriteListener: (WriteListener, Promise[Unit]) = {
    val promise = Promise[Unit]()
    class WriteL() extends WriteListener {
      override def onFailure(exception: AerospikeException) = promise.failure(exception)
      override def onSuccess(key: Key) = promise.success{}
    }
    (new WriteL, promise)
  }
}
