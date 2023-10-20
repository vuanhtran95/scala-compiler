import $file.Base

/**
* Set of character
* [a-z] -> a + b + c + d ... + z
*/
case class RANGE(cs: Set[Char]) extends Base.Rexp

/**
* 1 or more 
* r+ -> r.r*
*/
case class PLUS(r: Base.Rexp) extends Base.Rexp                      // plus

/**
* Empty string or r
* r? -> 1 + r
*/
case class OPTIONAL(r: Base.Rexp) extends Base.Rexp                  // optional


case class INTER(r1: Base.Rexp, r2: Base.Rexp) extends Base.Rexp          // intersection &

/**
* n-times
* r{n} -> 
*/
case class NTIMES(r: Base.Rexp  , n: Int) extends Base.Rexp

/**
* up n-times
* r{...n} -> 
*/
case class UPTO(r: Base.Rexp, n: Int) extends Base.Rexp

/**
* from n-times
* r{n...} ->
*/
case class FROM(r: Base.Rexp, n: Int) extends Base.Rexp

/**
* between n and m times
* r{n...m} -> 
*/
case class BETWEEN(r: Base.Rexp, n: Int, m: Int) extends Base.Rexp

/**
* not r
*/
case class NOT(r: Base.Rexp) extends Base.Rexp     