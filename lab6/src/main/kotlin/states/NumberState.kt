package states

import tokens.NumberToken

class NumberState(first: Char): State(first) {
    override fun form()= NumberToken(value.toInt())

    override fun continuesWith(c: Char): Boolean {
        if (c in '0'..'9') {
            return true
        }

        return false
    }
}