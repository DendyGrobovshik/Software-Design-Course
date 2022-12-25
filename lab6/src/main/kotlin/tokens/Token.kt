package tokens

import visitors.Visitor

interface Token {
    fun accept(visitor: Visitor)
}