package tokens

import visitors.Visitor

class NumberToken(val value: Int): Token {
    override fun accept(visitor: Visitor) {
        visitor.visit(this)
    }
}