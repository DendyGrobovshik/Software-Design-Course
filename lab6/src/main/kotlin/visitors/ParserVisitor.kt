package visitors

import tokens.BracketToken
import tokens.NumberToken
import tokens.OperationToken
import tokens.Token

class ParserVisitor(val tokens: List<Token>): Visitor {
    private val result: MutableList<Token> = mutableListOf()
    private val operationStack: MutableList<Token> = mutableListOf()

    override fun visit(token: NumberToken) {
        result.add(token)
    }

    override fun visit(token: BracketToken) {
        if (token.isOpen) {
            operationStack.add(token)
            return
        }
        while (true) {
            val last = operationStack.lastOrNull()
                ?: throw IllegalStateException("Excessive close brace found")

            if (last is BracketToken && last.isOpen) {
                operationStack.removeLast()
                return
            }

            relocate()
        }
    }

    override fun visit(token: OperationToken) {
        while (true) {
            val last = operationStack.lastOrNull() ?: break
            if (last is OperationToken && last.hasNotLessPriorityThan(token)) {
                relocate()
            } else {
                break
            }
        }
        operationStack.add(token)
    }

    private fun relocate() {
        result.add(operationStack.removeLast())
    }

    fun parseTokens(): List<Token> {
        tokens.forEach { it.accept(this) }
        while (operationStack.isNotEmpty()) {
            val last = operationStack.last()
            if (last is BracketToken) {
                throw IllegalStateException("Excessive brace ${last.type} found")
            }
            relocate()
        }
        return result
    }
}