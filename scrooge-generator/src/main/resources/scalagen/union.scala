{{#public}}
package {{package}}

import com.twitter.scrooge.{ThriftStruct, ThriftStructCodec3, ThriftStructFieldInfo, ThriftUnion, TFieldBlob}
import org.apache.thrift.protocol._
import java.nio.ByteBuffer
import java.util.Arrays
import scala.collection.immutable.{Map => immutable$Map}
import scala.collection.mutable.{
  ArrayBuffer => mutable$ArrayBuffer, Buffer => mutable$Buffer,
  HashMap => mutable$HashMap, HashSet => mutable$HashSet}
import scala.collection.{Map, Set}

{{#withJson}}
import com.persist.JsonOps._
import com.persist.json._
import com.persist.Exceptions.MappingException
{{/withJson}}

{{/public}}
@javax.annotation.Generated(value = Array("com.twitter.scrooge.Compiler"))
sealed trait {{StructName}} extends {{parentType}}

private object {{StructName}}Decoder {
  def apply(_iprot: TProtocol, newUnknown: TFieldBlob => {{StructName}}): {{StructName}} = {
    var _result: {{StructName}} = null
    _iprot.readStructBegin()
    val _field = _iprot.readFieldBegin()
    _field.id match {
{{#fields}}
{{#readWriteInfo}}
      {{>readUnionField}}
{{/readWriteInfo}}
{{/fields}}
      case _ =>
        if (_field.`type` != TType.STOP) {
          _result = newUnknown(TFieldBlob.read(_field, _iprot))
        } else {
          TProtocolUtil.skip(_iprot, _field.`type`)
        }
    }
    if (_field.`type` != TType.STOP) {
      _iprot.readFieldEnd()
      var _done = false
      var _moreThanOne = false
      while (!_done) {
        val _field = _iprot.readFieldBegin()
        if (_field.`type` == TType.STOP)
          _done = true
        else {
          _moreThanOne = true
          TProtocolUtil.skip(_iprot, _field.`type`)
          _iprot.readFieldEnd()
        }
      }
      if (_moreThanOne) {
        _iprot.readStructEnd()
        throw new TProtocolException("Cannot read a TUnion with more than one set value!")
      }
    }
    _iprot.readStructEnd()
    if (_result == null)
      throw new TProtocolException("Cannot read a TUnion with no set value!")
    _result
  }
}

object {{StructName}}Helper {
{{#fields}}
  type {{FieldName}}Alias = {{>qualifiedFieldType}}

  def withoutPassthroughFields_{{FieldName}}(obj: {{StructName}}.{{FieldName}}): {{StructName}}.{{FieldName}} = {
    val field = obj.{{fieldName}}
{{#passthroughFields}}
      {{StructName}}.{{FieldName}}(
        {{>withoutPassthrough}}
      )
{{/passthroughFields}}
    }

  {{#hasDefaultValue}}val {{FieldName}}DefaultValue = {{defaultFieldValue}}{{/hasDefaultValue}}
{{#fieldKeyType}}
  val {{FieldName}}KeyTypeManifest = Some(implicitly[Manifest[{{fieldKeyType}}]])
{{/fieldKeyType}}
{{^fieldKeyType}}
  val {{FieldName}}KeyTypeManifest = None
{{/fieldKeyType}}
{{#fieldValueType}}
  val {{FieldName}}ValueTypeManifest = Some(implicitly[Manifest[{{fieldValueType}}]])
{{/fieldValueType}}
{{^fieldValueType}}
  val {{FieldName}}ValueTypeManifest = None
{{/fieldValueType}}
{{/fields}}
}

{{docstring}}
@javax.annotation.Generated(value = Array("com.twitter.scrooge.Compiler"))
object {{StructName}} extends ThriftStructCodec3[{{StructName}}] {
  val Union = new TStruct("{{StructNameForWire}}")
{{#fields}}
  val {{fieldConst}} = new TField("{{fieldNameForWire}}", TType.{{constType}}, {{id}})
{{#isEnum}}
  private[this] val {{fieldConst}}I32 = new TField("{{fieldNameForWire}}", TType.I32, {{id}})
{{/isEnum}}
  val {{fieldConst}}Manifest = implicitly[Manifest[{{FieldName}}]]
{{/fields}}

  lazy val structAnnotations: immutable$Map[String, String] =
{{#structAnnotations}}
    immutable$Map[String, String](
{{#pairs}}
        "{{key}}" -> "{{value}}"
{{/pairs|,}}
    )
{{/structAnnotations}}
{{^structAnnotations}}
    immutable$Map.empty[String, String]
{{/structAnnotations}}

  override def encode(_item: {{StructName}}, _oprot: TProtocol) { _item.write(_oprot) }
  override def decode(_iprot: TProtocol): {{StructName}} = {{StructName}}Decoder(_iprot, UnknownUnionField(_))

  import com.twitter.scrooge.serialization.ThriftCodec

  implicit val thriftCodec = new ThriftCodec[{{StructName}}] {
    def encode(obj: {{StructName}}, protocol: TProtocol) { {{StructName}}.encode(obj, protocol) }
    def decode(protocol: TProtocol) = {{StructName}}.decode(protocol)
  }

  def apply(_iprot: TProtocol): {{StructName}} = decode(_iprot)

  import {{StructName}}Helper._

  def withoutPassthroughFields(struct: {{StructName}}): {{StructName}} = {
    struct match {
{{#fields}}
      case obj: {{FieldName}} => withoutPassthroughFields_{{FieldName}}(obj)
{{/fields}}
      case unknown: UnknownUnionField => unknown // by definition pass-through
    }
  }

{{#withJson}}

{{#implicitsToImport}}
  import {{import}}.{{codecs}}
{{/implicitsToImport}}

  implicit object JsonReadCodec{{StructName}} extends ReadCodec[{{StructName}}] {
    def read(json: Json): {{StructName}} = {
      val jsObject = ReadCodec.castOrThrow(json)
      val onlyChild = jsObject.keys.headOption.getOrElse(throw new MappingException(s"Expected json to contain a single child in order to read into union but found: $json"))
      onlyChild match {{{#fields}}
        case "{{snake_case_name}}" => {{FieldName}}({{fieldName}} = com.persist.json.read[{{>qualifiedFieldType}}](jsObject.values.head))
        {{/fields}}
      }
    }
  }
  implicit object JsonWriteCodec{{StructName}} extends WriteCodec[{{StructName}}] {
    def write(obj: {{StructName}}): Json = obj match {{{#fields}}
      case {{FieldName}}(x) => JsonObject("{{snake_case_name}}" -> com.persist.json.toJson(x))
    {{/fields}}
    }
  }
{{/withJson}}

{{#fields}}
  object {{FieldName}} {
    def withoutPassthroughFields(obj: {{FieldName}}): {{FieldName}} = withoutPassthroughFields_{{FieldName}}(obj)

    val fieldInfo =
      new ThriftStructFieldInfo(
        {{fieldConst}},
        false,
        manifest[{{FieldName}}Alias],
        {{FieldName}}KeyTypeManifest,
        {{FieldName}}ValueTypeManifest,
{{#fieldTypeAnnotations}}
        immutable$Map(
{{#pairs}}
          "{{key}}" -> "{{value}}"
{{/pairs|,}}
        ),
{{/fieldTypeAnnotations}}
{{^fieldTypeAnnotations}}
        immutable$Map.empty[String, String],
{{/fieldTypeAnnotations}}
{{#fieldFieldAnnotations}}
        immutable$Map(
{{#pairs}}
          "{{key}}" -> "{{value}}"
{{/pairs|,}}
        )
{{/fieldFieldAnnotations}}
{{^fieldFieldAnnotations}}
        immutable$Map.empty[String, String]
{{/fieldFieldAnnotations}}
      )
  }

  case class {{FieldName}}({{fieldName}}: {{FieldName}}Alias{{#hasDefaultValue}} = {{FieldName}}DefaultValue{{/hasDefaultValue}}) extends {{StructName}} {
    override def write(_oprot: TProtocol) {
{{^isPrimitive}}
      if ({{fieldName}} == null)
        throw new TProtocolException("Cannot write a TUnion with no set value!")
{{/isPrimitive}}
      _oprot.writeStructBegin(Union)
{{#readWriteInfo}}
      {{>writeField}}
{{/readWriteInfo}}
      _oprot.writeFieldStop()
      _oprot.writeStructEnd()
    }
  }

{{/fields}}
  case class UnknownUnionField private[{{StructName}}](private val field: TFieldBlob) extends {{StructName}} {
    override def write(_oprot: TProtocol) {
      _oprot.writeStructBegin(Union)
      field.write(_oprot)
      _oprot.writeFieldStop()
      _oprot.writeStructEnd()
    }
  }
}
