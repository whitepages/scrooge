package {{package}}

import com.twitter.scrooge.{ThriftStruct, ThriftStructCodec}
import com.persist.json._

case class Info(in: TInfo, out: TInfo)

case class TInfo(is: ThriftStruct => Boolean, companion: ThriftStructCodec[_ <: ThriftStruct], codec: ReadWriteCodec[_])