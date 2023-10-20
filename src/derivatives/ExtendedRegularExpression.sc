import $file.Base

/**
*
*
*/
case class RANGE(cs: Set[Char]) extends Base.Rexp             // set of characters


case class PLUS(r: Base.Rexp  ) extends Base.Rexp                      // plus
case class OPTIONAL(r: Base.Rexp  ) extends Base.Rexp                  // optional
case class INTER(r1: Base.Rexp  , r2: Base.Rexp  ) extends Base.Rexp          // intersection &
case class NTIMES(r: Base.Rexp  , n: Int) extends Base.Rexp            // n-times
case class UPTO(r: Base.Rexp  , n: Int) extends Base.Rexp              // up n-times
case class FROM(r: Base.Rexp  , n: Int) extends Base.Rexp              // from n-times
case class BETWEEN(r: Base.Rexp  , n: Int, m: Int) extends Base.Rexp   // between nm-times
case class NOT(r: Base.Rexp) extends Base.Rexp     