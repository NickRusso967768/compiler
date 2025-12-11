package compiler;

/**
 * Represents a token in the lexical analysis phase.
 * Each token has a type and an optional value.
 */
public class Token {
    private final TokenType type;
    private final String value;
    
    public Token(TokenType type, String value) {
        this.type = type;
        this.value = value;
    }
    
    public Token(TokenType type) {
        this(type, null);
    }
    
    public TokenType getType() {
        return type;
    }
    
    public String getValue() {
        return value;
    }
    
    @Override
    public String toString() {
        if (value != null) {
            return "Token(" + type + ", '" + value + "')";
        }
        return "Token(" + type + ")";
    }
}
