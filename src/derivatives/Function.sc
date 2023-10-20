import $file.Base
import $file.BasicRegularExpression

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
  case BasicRegularExpression.ZERO => BasicRegularExpression.ZERO
  case BasicRegularExpression.ONE => BasicRegularExpression.ZERO
  case BasicRegularExpression.CHAR(d) => if (c == d) BasicRegularExpression.ONE else BasicRegularExpression.ZERO
  case BasicRegularExpression.ALT(r1, r2) => BasicRegularExpression.ALT(der(c, r1), der(c, r2))
  case BasicRegularExpression.SEQ(r1, r2) => 
    if (nullable(r1)) BasicRegularExpression.ALT(BasicRegularExpression.SEQ(der(c, r1), r2), der(c, r2))
    else BasicRegularExpression.SEQ(der(c, r1), r2)
  case BasicRegularExpression.STAR(r1) => BasicRegularExpression.SEQ(der(c, r1), BasicRegularExpression.STAR(r1))
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