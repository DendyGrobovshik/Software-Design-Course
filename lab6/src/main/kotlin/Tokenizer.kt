import states.State
import tokens.EndToken
import tokens.SpaceToken
import tokens.Token
import java.io.InputStream

class Tokenizer(private val input: InputStream) {
    var state: State = State.nextState(nextChar())

    fun nextChar(): Char = input.read().toChar()

    fun nextToken(): Token {
        return state.getToken(this)
    }

    fun tokenizeWithoutSpaces(): List<Token> {
        val result = mutableListOf<Token>()

        var next = nextToken()
        while (next !is EndToken) {
            if (next !is SpaceToken) {
                result.add(next)
            }
            next = nextToken()
        }

        return result
    }
}