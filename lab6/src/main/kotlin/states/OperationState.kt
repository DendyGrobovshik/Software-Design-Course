package states

import tokens.OperationToken

class OperationState(first: Char) : State(first) {
    private val type: Char = first

    override fun form() = OperationToken(type)

    override fun continuesWith(c: Char): Boolean = false
}