import $file.Base
/**
* Define some annotation for some basic regular expressions
*/

/**
* r ::= 0
* This defines null language
* There is no any string is contained in this language
*/
case object ZERO extends Base.Rexp

/**
* r ::= 1
* This defines empty string /""/ or []
* The language of this expression only contains empty string
*/
case object ONE extends Base.Rexp

/**
* r ::= c
* This language only contain a single character.
* Eg: "a"
*/

case class CHAR(c: Char) extends Base.Rexp

/**
* r ::= r1 + r2
* The language will optionally match r1 OR r2
*/
case class ALT(r1: Base.Rexp, r2: Base.Rexp) extends Base.Rexp

/**
* r ::= r1 . r2
* The language will match sequence of r1.r2
* Eg: regexp for email as below
* [...]+ · @ · [...]+ · . · [...]{2,6}
*/
case class SEQ(r1: Base.Rexp, r2: Base.Rexp) extends Base.Rexp 


/**
* r ::= r*
* The language will match 0 or more r
*/
case class STAR(r: Base.Rexp) extends Base.Rexp