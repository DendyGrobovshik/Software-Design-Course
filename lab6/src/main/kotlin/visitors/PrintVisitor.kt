package visitors

import tokens.BracketToken
import tokens.NumberToken
import tokens.OperationToken
import tokens.Token
import java.util.*

class PrintVisitor(val tokens: List<Token>): Visitor {
    private val buffer = StringJoiner(" ")

    override fun visit(token: NumberToken) {
        buffer.add(token.value.toString())
    }

    override fun visit(token: BracketToken) {
        buffer.add(token.type)
    }

    override fun visit(token: OperationToken) {
        buffer.add(token.operation.toString())
    }

    fun println() {
        tokens.forEach { it.accept(this) }
        println(buffer)
    }
}