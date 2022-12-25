package states

import tokens.SpaceToken

class SpaceState : State() {
    override fun form() = SpaceToken()

    override fun continuesWith(c: Char) = c.isWhitespace()
}