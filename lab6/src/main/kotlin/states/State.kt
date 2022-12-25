package states

import Tokenizer
import tokens.Token


abstract class State(first: Char = ' ') {
    protected var value: String = first.toString()

    companion object {
        private const val EOF = (-1).toChar()

        fun nextState(c: Char): State {
            return when {
                c.isWhitespace() -> SpaceState()
                c in listOf('(', ')') -> BracketState(c)
                c in listOf('+', '-', '*', '/') -> OperationState(c)
                c in '0'..'9' -> NumberState(c)
                c == EOF -> EndState()
                else -> throw Error("Unknown symbol: '$c'")
            }
        }
    }

    fun getToken(tokenizer: Tokenizer): Token {
        var c = tokenizer.nextChar()
        while (continuesWith(c)) {
            value += c
            c = tokenizer.nextChar()
        }

        tokenizer.state = nextState(c)

        return form()
    }

    protected abstract fun form(): Token

    protected abstract fun continuesWith(c: Char): Boolean
}