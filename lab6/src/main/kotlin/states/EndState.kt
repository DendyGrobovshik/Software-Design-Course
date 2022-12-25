package states

import tokens.EndToken

class EndState: State() {
    override fun form() = EndToken()

    override fun continuesWith(c: Char): Boolean = false
}