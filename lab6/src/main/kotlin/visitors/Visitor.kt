package visitors

import tokens.BracketToken
import tokens.NumberToken
import tokens.OperationToken

interface Visitor {
    fun visit(token: NumberToken)
    fun visit(token: BracketToken)
    fun visit(token: OperationToken)
}
