package com.twitter.scrooge

import com.persist.json.{WriteCodec, ReadCodec}

case class Info(in: TInfo, out: TInfo)

case class TInfo(is: ThriftStruct => Boolean, companion: ThriftStructCodec[_ <: ThriftStruct], readCodec: ReadCodec[_], writeCodec: WriteCodec[_])