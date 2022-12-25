package tokens

import visitors.Visitor

class BracketToken(val type: String): Token {
    val isOpen = type == "("

    override fun accept(visitor: Visitor) {
        visitor.visit(this)
    }
}