package com.twitter.scrooge

package object util {

  private val camelToSnakeRegex = "(\\A|[^A-Z_])([A-Z])".r
  /** Convert a string to snake case.
    *
    * To convert to snake case, this converter inserts an underscore before every
    * capital letter, unless that letter is is at the start or is preceded by another
    * capital letter, and then all capitals are converted to lower case.
    *
    * Examples:
    *
    * oneTwo -> one_two
    * One    -> _one
    * showUS -> show_us
    * NBC    -> _nbc
    *
    * Note that CamelToSnake and SnakeToCamel are not always reversible. For example,
    * showUS converts to show_us, which would back convert to showUs.
    *
    * @param in Input string (typically in camal case)
    * @return Output string in snake case
    */
  def camelToSnake(in: String) =
    camelToSnakeRegex.replaceAllIn(in, "$1_$2").toLowerCase
}