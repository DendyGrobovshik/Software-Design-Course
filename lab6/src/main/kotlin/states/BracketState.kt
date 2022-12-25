package states

import tokens.BracketToken

class BracketState(type: Char): State(type) {
    override fun form() = BracketToken(value)

    override fun continuesWith(c: Char) = false
}