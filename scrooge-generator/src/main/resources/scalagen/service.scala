package {{package}}

import com.twitter.scrooge.{
  TFieldBlob, ThriftService, ThriftStruct, ThriftStructCodec, ThriftStructCodec3, ThriftStructFieldInfo, ThriftUtil}
import java.nio.ByteBuffer
import java.util.Arrays
import org.apache.thrift.protocol._
import org.apache.thrift.transport.TTransport
import org.apache.thrift.TApplicationException
import org.apache.thrift.transport.TMemoryBuffer
import scala.collection.immutable.{Map => immutable$Map}
import scala.collection.mutable.{
  Builder,
  ArrayBuffer => mutable$ArrayBuffer, Buffer => mutable$Buffer,
  HashMap => mutable$HashMap, HashSet => mutable$HashSet}
import scala.collection.{Map, Set}

{{docstring}}
@javax.annotation.Generated(value = Array("com.twitter.scrooge.Compiler"))
trait {{ServiceName}}[+MM[_]] {{#genericParent}}extends {{genericParent}} {{/genericParent}}{
{{#genericFunctions}}
  {{>function}}
{{/genericFunctions}}
}

{{docstring}}
object {{ServiceName}} {
{{#internalStructs}}
{{#internalArgsStruct}}
  {{>struct}}
{{/internalArgsStruct}}
{{#internalResultStruct}}
  {{>struct}}
{{/internalResultStruct}}
{{/internalStructs}}

{{#withScalaWebService}}
  import akka.actor.ActorRefFactory

  class Client(actorRefFactory: ActorRefFactory, endPoint: String) extends ClassSupport {
    val clientName = "{{ServiceName}}"

    private[this] implicit val ec = actorRefFactory.dispatcher
    val thriftClient = Client(actorRefFactory, clientName)

    {{#asyncFunctions}}
    {{>function}} = {
      val args = {{ServiceName}}.{{funcName}}$args({{#fieldParams}}{{name}}{{/fieldParams|,}})
      val startTime  System.nanoTime()
      thriftClient.postThrift({{snake_func_name}}, args, noId, uriString = endPoint)
        .mapTp[Mcs.{{funcName}}$result]
        .map( _.success.get)
        .andThen { _ => monitor ! {{ServiceName}}{{funcName}}Latency(System.nanoTime() - startTime)}
    }
    {{/asynFunctions}}
  }

  import com.codahale.metrics.MetricRegistry

  object {{ServiceName}}Monitor {
    sealed trait {{ServiceName}}Messages
    {{#functions}}
    case class {{funcName}}Latency(duration: Long) extends {{ServiceName}}Messages

    def ext(metrics: MetricRegistry): PartialFunction[Any, Unit] = {
      val
    }
  }
{{/withScalaWebService}}

{{#withFinagle}}
  import com.twitter.util.Future

  trait FutureIface extends {{#futureIfaceParent}}{{futureIfaceParent}} with{{/futureIfaceParent}} {{ServiceName}}[Future] {
{{#asyncFunctions}}
    {{>function}}
{{/asyncFunctions}}
  }

  class FinagledClient(
      service: com.twitter.finagle.Service[com.twitter.finagle.thrift.ThriftClientRequest, Array[Byte]],
      protocolFactory: TProtocolFactory = new TBinaryProtocol.Factory,
      serviceName: String = "",
      stats: com.twitter.finagle.stats.StatsReceiver = com.twitter.finagle.stats.NullStatsReceiver)
    extends {{ServiceName}}$FinagleClient(
      service,
      protocolFactory,
      serviceName,
      stats)
    with FutureIface

  class FinagledService(
      iface: FutureIface,
      protocolFactory: TProtocolFactory)
    extends {{ServiceName}}$FinagleService(
      iface,
      protocolFactory)
{{/withFinagle}}
}
