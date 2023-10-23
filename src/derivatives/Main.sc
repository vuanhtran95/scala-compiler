import $file.Base
import $file.BasicRegularExpression
import $file.ExtendedRegularExpression
import $file.Function


// Test Cases
//============


// some syntactic convenience

//def charlist2rexp(s: List[Char]) : Rexp = s match {
//  case Nil => ONE
//  case c::Nil => CHAR(c)
//  case c::s => SEQ(CHAR(c), charlist2rexp(s))
//}
//implicit def string2rexp(s: String) : Rexp = charlist2rexp(s.toList)

//extension (r: Rexp) {
//  def ~ (s: Rexp) = SEQ(r, s)
//  def % = STAR(r)
//}


// Question 5

//println("EMAIL:")
//val LOWERCASE = ('a' to 'z').toSet
//val DIGITS = ('0' to '9').toSet
//val SYMBOLS1 = ("_.-").toSet
//val SYMBOLS2 = (".-").toSet
//val EMAIL = { PLUS(CFUN(LOWERCASE | DIGITS | SYMBOLS1)) ~ "@" ~ 
//              PLUS(CFUN(LOWERCASE | DIGITS | SYMBOLS2)) ~ "." ~
//              BETWEEN(CFUN(LOWERCASE | Set('.')), 2, 6) }

//val my_email = "christian.urban@kcl.ac.uk"

//println(EMAIL);
//println(matcher(EMAIL, my_email))
//println(ders(my_email.toList,EMAIL))


// Question 6
//val ALL = ???
//val COMMENT = """/*""" ~ (NOT(ALL.% ~ """*/""" ~ ALL.%)) ~ """*/"""

//println(matcher(COMMENT, """/**/"""))
//println(matcher(COMMENT, """/*foobar*/"""))
//println(matcher(COMMENT, """/*test*/test*/"""))
//println(matcher(COMMENT, """/*test/*test*/"""))

// Question 7

//val r1 = ???
//val r2 = ???
//val s1 = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
//val s2 = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
//val s3 = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"

//for (s <- List(s1,s2,s3)) {
//  println(matcher(r1, s))
//  println(matcher(r2, s))
//}

