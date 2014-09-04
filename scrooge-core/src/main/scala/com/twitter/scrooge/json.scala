package com.twitter.scrooge

import com.persist.json.{WriteCodec, ReadCodec}

case class Info[T1 <: ThriftStruct, T2 <: ThriftStruct](in: TInfo[T1], out: TInfo[T2])

case class TInfo[T <: ThriftStruct](is: T => Boolean, companion: ThriftStructCodec[T], readCodec: ReadCodec[T], writeCodec: WriteCodec[T])