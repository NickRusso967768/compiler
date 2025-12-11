package compiler;

/**
 * Enumeration of all possible token types in arithmetic expressions.
 */
public enum TokenType {
    NUMBER,       // Numeric literals (e.g., 42, 3.14)
    PLUS,         // +
    MINUS,        // -
    MULTIPLY,     // *
    DIVIDE,       // /
    LPAREN,       // (
    RPAREN,       // )
    EOF           // End of input
}
