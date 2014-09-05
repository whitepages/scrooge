package com.twitter.scrooge

import com.persist.json.{WriteCodec, ReadCodec}

case class Info(in: TInfo[ThriftStruct], out: TInfo[ThriftStruct])

case class TInfo[T <: ThriftStruct](is: T => Boolean, companion: ThriftStructCodec[T], readCodec: ReadCodec[T], writeCodec: WriteCodec[T])