package compiler;

import java.util.ArrayList;
import java.util.List;

/**
 * Lexical Analyzer (Tokenizer) - Phase 1 of compilation.
 * Scans through the input string character by character and converts it into tokens.
 * Uses finite automaton concepts to recognize different token types.
 */
public class Lexer {
    private String input;
    private int pos;
    private char current;
    
    public Lexer(String input) {
        this.input = input;
        this.pos = 0;
        this.current = input.length() > 0 ? input.charAt(0) : '\0';
    }
    
    // Move to the next character in the input
    private void advance() {
        pos++;
        current = (pos < input.length()) ? input.charAt(pos) : '\0';
    }
    
    // Look at the next character without consuming it
    private char peek() {
        int nextPos = pos + 1;
        return (nextPos < input.length()) ? input.charAt(nextPos) : '\0';
    }
    
    // Skip over any whitespace characters
    private void skipWhitespace() {
        while (current == ' ' || current == '\t' || current == '\n' || current == '\r') {
            advance();
        }
    }
    
    // Read a complete number (handles both integers and decimals)
    private Token readNumber() {
        StringBuilder num = new StringBuilder();
        
        // Read digits before decimal point
        while (Character.isDigit(current)) {
            num.append(current);
            advance();
        }
        
        // Check if there's a decimal point followed by more digits
        if (current == '.' && Character.isDigit(peek())) {
            num.append(current);
            advance();
            
            // Read digits after decimal point
            while (Character.isDigit(current)) {
                num.append(current);
                advance();
            }
        }
        
        return new Token(TokenType.NUMBER, num.toString());
    }
    
    // Get the next token from the input
    public Token getNextToken() {
        while (current != '\0') {
            
            if (current == ' ' || current == '\t' || current == '\n' || current == '\r') {
                skipWhitespace();
                continue;
            }
            
            if (Character.isDigit(current)) {
                return readNumber();
            }
            
            if (current == '+') {
                advance();
                return new Token(TokenType.PLUS, "+");
            }
            
            if (current == '-') {
                advance();
                return new Token(TokenType.MINUS, "-");
            }
            
            if (current == '*') {
                advance();
                return new Token(TokenType.MULTIPLY, "*");
            }
            
            if (current == '/') {
                advance();
                return new Token(TokenType.DIVIDE, "/");
            }
            
            if (current == '(') {
                advance();
                return new Token(TokenType.LPAREN, "(");
            }
            
            if (current == ')') {
                advance();
                return new Token(TokenType.RPAREN, ")");
            }
            
            throw new RuntimeException("Unexpected character: " + current);
        }
        
        return new Token(TokenType.EOF);
    }
    
    // Convert entire input string into a list of tokens
    public List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();
        Token tok;
        
        do {
            tok = getNextToken();
            tokens.add(tok);
        } while (tok.getType() != TokenType.EOF);
        
        return tokens;
    }
}
