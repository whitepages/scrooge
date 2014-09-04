package com.twitter.scrooge

import com.persist.json.{WriteCodec, ReadCodec}

case class Info[T <: ThriftStruct](in: TInfo[T], out: TInfo[T])

case class TInfo[T <: ThriftStruct](is: T => Boolean, companion: ThriftStructCodec[T], readCodec: ReadCodec[T], writeCodec: WriteCodec[T])