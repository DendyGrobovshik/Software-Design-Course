import visitors.EvalVisitor
import visitors.ParserVisitor
import visitors.PrintVisitor

fun main(args: Array<String>) {
    val input = "(30 + 5) / 7"

    val tokenizer = Tokenizer(input.byteInputStream())
    var tokens = tokenizer.tokenizeWithoutSpaces()

    PrintVisitor(tokens).println()
    tokens = ParserVisitor(tokens).parseTokens()
    PrintVisitor(tokens).println()
    print(EvalVisitor(tokens).eval().value)
}
