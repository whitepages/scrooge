package com.twitter.scrooge

import org.apache.thrift.protocol.TProtocol

package object serialization {
  trait ThriftCodec[T] {
    def decode(protocol: TProtocol): T
    def encode(obj: T, protocol: TProtocol)
  }

  def decode[T](protocol: TProtocol)(implicit codec: ThriftCodec[T]) = codec.decode(protocol)
  def encode[T](obj: T, protocol: TProtocol)(implicit codec: ThriftCodec[T]) { codec.encode(obj, protocol) }
}
