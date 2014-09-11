package {{package}}

import com.twitter.scrooge.ThriftEnum

{{#withJson}}
import com.persist.JsonOps._
import com.persist.json._
{{/withJson}}

{{docstring}}
@javax.annotation.Generated(value = Array("com.twitter.scrooge.Compiler"))
case object {{EnumName}} {
{{#values}}
  {{valuedocstring}}
  case object {{name}} extends {{package}}.{{EnumName}} {
    val value = {{value}}
    val name = "{{name}}"
    val originalName = "{{originalName}}"
  }
{{/values}}

  /**
   * Find the enum by its integer value, as defined in the Thrift IDL.
   * @throws NoSuchElementException if the value is not found.
   */
  def apply(value: Int): {{package}}.{{EnumName}} = {
    value match {
{{#values}}
      case {{value}} => {{package}}.{{EnumName}}.{{name}}
{{/values}}
      case _ => throw new NoSuchElementException(value.toString)
    }
  }

  /**
   * Find the enum by its integer value, as defined in the Thrift IDL.
   * Returns None if the value is not found
   */
  def get(value: Int): Option[{{package}}.{{EnumName}}] = {
    value match {
{{#values}}
      case {{value}} => scala.Some({{package}}.{{EnumName}}.{{name}})
{{/values}}
      case _ => scala.None
    }
  }

  def valueOf(name: String): Option[{{package}}.{{EnumName}}] = {
    name.toLowerCase match {
{{#values}}
      case "{{unquotedNameLowerCase}}" => scala.Some({{package}}.{{EnumName}}.{{name}})
{{/values}}
      case _ => scala.None
    }
  }

  lazy val list: List[{{package}}.{{EnumName}}] = scala.List[{{package}}.{{EnumName}}](
{{#values}}
    {{package}}.{{EnumName}}.{{name}}
{{/values|,}}
  )

{{#withJson}}
import shapeless._
import syntax.typeable._

  implicit object JsonReadCodec extends ReadCodec[{{EnumName}}] {
    def read (json: Json) = {
      val stringValue = json.cast[String].getOrElse(throw new MappingException(s"Expected json to be a String to parse into an Enum but found $json"))
      valueOf(stringValue).getOrElse(throw new MappingException(s"Could not parse enum {{EnumName}} from String $stringValue"))
    }
  }
  implicit object JsonWriteCodec extends WriteCodec[{{EnumName}}] {
    def write(obj: {{EnumName}}) = obj.toString
  }
{{/withJson}}
}


{{docstring}}
@javax.annotation.Generated(value = Array("com.twitter.scrooge.Compiler"))
sealed trait {{EnumName}} extends ThriftEnum with Serializable
