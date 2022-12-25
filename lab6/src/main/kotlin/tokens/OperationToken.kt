package tokens

import visitors.Visitor

class OperationToken(val operation: Char) : Token {
    override fun accept(visitor: Visitor) {
        visitor.visit(this)
    }

    private fun priority(): Int {
        return when (operation) {
            in listOf('+', '-') -> 1
            in listOf('*', '/') -> 2
            else -> throw Error("Unknown operation")
        }
    }

    fun eval(a: NumberToken, b: NumberToken): NumberToken {
        return NumberToken(
            when (operation) {
                '+' -> a.value + b.value
                '-' -> a.value - b.value
                '*' -> a.value * b.value
                '/' -> a.value / b.value
                else -> throw Error("Unknown operation")
            }
        )
    }

    fun hasNotLessPriorityThan(other: OperationToken): Boolean =
        priority() >= other.priority()
}