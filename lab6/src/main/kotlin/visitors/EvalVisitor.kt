package visitors

import tokens.BracketToken
import tokens.NumberToken
import tokens.OperationToken
import tokens.Token

class EvalVisitor(val tokens: List<Token>) : Visitor {
    private val stack: MutableList<Token> = mutableListOf()

    fun eval(): NumberToken {
        tokens.forEach { it.accept(this) }

        return postEvalCheck()
    }

    private fun postEvalCheck(): NumberToken {
        if (stack.size != 1) throw IllegalStateException("Found too many operands")

        val result = stack.first()
        if (result !is NumberToken) throw IllegalStateException("Result is not a number")

        return result
    }

    override fun visit(token: NumberToken) {
        stack.add(token)
    }

    override fun visit(token: BracketToken) {
        throw IllegalArgumentException("Illegal character ${token.type} found while calculating")
    }

    override fun visit(token: OperationToken) {
        val v2 = stack.removeLastOrNull()
        val v1 = stack.removeLastOrNull()

        if (v1 is NumberToken && v2 is NumberToken) {
            stack.add(token.eval(v1, v2))
        } else {
            throw IllegalStateException("Operand $v1 or $v2 are not fit for operation: $token")
        }
    }
}