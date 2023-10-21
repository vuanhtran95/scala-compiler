import $file.Base
import $file.BasicRegularExpression
import $file.ExtendedRegularExpression

/** 
* Simplification
*/
def simp(r: Base.Rexp) : Base.Rexp = r match {
  case BasicRegularExpression.ALT(r1, r2) => (simp(r1), simp(r2)) match {
    case (BasicRegularExpression.ZERO, r2s) => r2s
    case (r1s, BasicRegularExpression.ZERO) => r1s
    case (r1s, r2s) => if (r1s == r2s) r1s else BasicRegularExpression.ALT (r1s, r2s)
  }

  case BasicRegularExpression.SEQ(r1, r2) =>  (simp(r1), simp(r2)) match {
    case (BasicRegularExpression.ZERO, _) => BasicRegularExpression.ZERO
    case (_, BasicRegularExpression.ZERO) => BasicRegularExpression.ZERO
    case (BasicRegularExpression.ONE, r2s) => r2s
    case (r1s, BasicRegularExpression.ONE) => r1s
    case (r1s, r2s) => BasicRegularExpression.SEQ(r1s, r2s)
  }
  case r => r
}

/**
* Derivative character c of regexp r
*/
def der(c: Char, r: Base.Rexp) : Base.Rexp = r match {
  /**
  * der c 0 = 0
  */
  case BasicRegularExpression.ZERO => BasicRegularExpression.ZERO

  /**
  * der c 1 = 0
  */
  case BasicRegularExpression.ONE => BasicRegularExpression.ZERO

  /**
  * der c d = if (c == d) ? 1 : 0
  */
  case BasicRegularExpression.CHAR(d) => if (c == d) BasicRegularExpression.ONE else BasicRegularExpression.ZERO

  /**
  * der c (r1 + r2) = (der c r1) + (der c r2)
  */
  case BasicRegularExpression.ALT(r1, r2) => BasicRegularExpression.ALT(der(c, r1), der(c, r2))

  /**
  * der c (r1 . r2) = 
  */
  case BasicRegularExpression.SEQ(r1, r2) => 
    if (nullable(r1)) BasicRegularExpression.ALT(BasicRegularExpression.SEQ(der(c, r1), r2), der(c, r2))
    else BasicRegularExpression.SEQ(der(c, r1), r2)

  /**
  * der c (r*) = (der c r) . r*
  */
  case BasicRegularExpression.STAR(r) => BasicRegularExpression.SEQ(der(c, r), BasicRegularExpression.STAR(r))

  /**
  * N times
  * der c (r{n}) =
  * IF (n == 0) THEN 0
  * ELSE (der c r) . r{n-1}
  */
  case ExtendedRegularExpression.NTIMES(r, n) => 
    if (n == 0) BasicRegularExpression.ZERO
    else BasicRegularExpression.SEQ(der(c, r), ExtendedRegularExpression.NTIMES(r, n - 1))

  /**
  * Plus: 1 or more
  * der c (r+) = (der c r) . r*
  */
  case ExtendedRegularExpression.PLUS(r) => 
    BasicRegularExpression.SEQ(der(c, r), BasicRegularExpression.STAR(r))

  /**
  * Between
  * der c ( r{n, m} ) = 
  * IF (n == 0 && m == 0) THEN 0
  * ELSE (n == 0) ??? not sure
  *
  * TODO
  */
  case ExtendedRegularExpression.BETWEEN(r) => ExtendedRegularExpression.BETWEEN(r)

  /**
  * Range [c1, c2, ... cn]
  * der c ([c1, c2, ... cn]) = 
  * IF (c in [c1, c2, ... cn]) THEN 1
  * ELSE 0
  */
  case ExtendedRegularExpression.RANGE(cs: Set[Char]) => 
    if (cs.contains(r)) BasicRegularExpression.ONE 
    else BasicRegularExpression.ZERO;

  /**
  *
  */
  case ExtendedRegularExpression.OPTIONAL(r) => ExtendedRegularExpression.OPTIONAL(r)
  case ExtendedRegularExpression.UPTO(r) => ExtendedRegularExpression.UPTO(r)
  case ExtendedRegularExpression.FROM(r) => ExtendedRegularExpression.FROM(r)
  case ExtendedRegularExpression.NOT(r) => ExtendedRegularExpression.NOT(r)

  // case ExtendedRegularExpression.CFUN(r) => ExtendedRegularExpression.CFUN(r)

}

/** 
* the derivative w.r.t. a string (iterates der)
*/
def ders(s: List[Char], r: Base.Rexp) : Base.Rexp = s match {
  case Nil => r
  case c::s => ders(s, simp(der(c, r)))
}

/**
* The main matcher function
*/
def matcher(r: Base.Rexp, s: String) : Boolean = 
  nullable(ders(s.toList, r))

/**
* Testing whether a regex can match the empty string
*/
def nullable (r: Base.Rexp) : Boolean = r match {
  case BasicRegularExpression.ZERO => false
  case BasicRegularExpression.ONE => true
  case BasicRegularExpression.CHAR(_) => false
  case BasicRegularExpression.ALT(r1, r2) => nullable(r1) || nullable(r2)
  case BasicRegularExpression.SEQ(r1, r2) => nullable(r1) && nullable(r2)
  case BasicRegularExpression.STAR(_) => true
}