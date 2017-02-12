package dbSummary

import com.aerospike.client.async.AsyncClientPolicy
import com.aerospike.client.policy.{RecordExistsAction, WritePolicy}
import com.google.inject.{ImplementedBy, Inject, Singleton}
import helpers.{AeroConfig, ConfigComponent}

/**
  * Created by azula on 12.02.17.
  */

@ImplementedBy(classOf[PoliciesContainerImpl])
trait PoliciesContainer{

  val asyncPolicy: AsyncClientPolicy

  val create: WritePolicy
  val createOrUpdate: WritePolicy
  val update: WritePolicy
  val createOrReplace: WritePolicy
  val replace: WritePolicy

}

@Singleton
class PoliciesContainerImpl @Inject()(aeroConfig: AeroConfig) extends PoliciesContainer {

  import RecordExistsAction._

  private[this] def createWPolicy(enum: RecordExistsAction): WritePolicy = {
    val policy = new WritePolicy()
    policy.expiration = -1
    policy.timeout = 6000
    policy.recordExistsAction = enum
    policy
  }

  val asyncPolicy = new AsyncClientPolicy()
    asyncPolicy.asyncMaxCommands = aeroConfig.maxConcurentCommands

  val create = createWPolicy(CREATE_ONLY)
  val createOrUpdate = createWPolicy(UPDATE)
  val update = createWPolicy(UPDATE_ONLY)
  val createOrReplace = createWPolicy(REPLACE)
  val replace = createWPolicy(REPLACE_ONLY)

}
