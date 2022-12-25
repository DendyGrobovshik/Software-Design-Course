package tokens

import visitors.Visitor

class EndToken: Token {
    override fun accept(visitor: Visitor) {
//        visitor.visit(this)
    }
}