package compiler;

import compiler.ast.*;
import java.util.List;

/**
 * Parser - Phase 2 of compilation.
 * Uses recursive descent parsing to build an Abstract Syntax Tree (AST).
 * 
 * Grammar (with operator precedence):
 *   expression  ::= term ((PLUS | MINUS) term)*
 *   term        ::= factor ((MULTIPLY | DIVIDE) factor)*
 *   factor      ::= (PLUS | MINUS) factor | NUMBER | LPAREN expression RPAREN
 */
public class Parser {
    private List<Token> tokens;
    private int pos;
    private Token current;
    private TokenType lastOperator = null;
    
    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        this.pos = 0;
        this.current = tokens.size() > 0 ? tokens.get(0) : new Token(TokenType.EOF);
    }
    
    // Move to the next token
    private void advance() {
        pos++;
        current = (pos < tokens.size()) ? tokens.get(pos) : new Token(TokenType.EOF);
    }
    
    // Verify current token matches expected type and consume it
    private void expect(TokenType type) {
        if (current.getType() == type) {
            advance();
        } else {
            throw new RuntimeException("Expected " + type + " but got " + current.getType());
        }
    }
    
    // Parse a factor: handles numbers, parentheses, and unary operators
    // factor ::= (PLUS | MINUS) factor | NUMBER | LPAREN expression RPAREN
    private ASTNode parseFactor() {
        Token tok = current;
        
        // Handle unary + or -
        if (tok.getType() == TokenType.PLUS) {
            // Check if last operator was also PLUS (prevents ++)
            if (lastOperator == TokenType.PLUS) {
                throw new RuntimeException("Unexpected token: consecutive + operators");
            }
            advance();
            TokenType savedOp = lastOperator;
            lastOperator = TokenType.PLUS;
            ASTNode result = new UnaryOpNode("+", parseFactor());
            lastOperator = savedOp;
            return result;
        }
        
        if (tok.getType() == TokenType.MINUS) {
            // Check if last operator was also MINUS (prevents --)
            if (lastOperator == TokenType.MINUS) {
                throw new RuntimeException("Unexpected token: consecutive - operators");
            }
            advance();
            TokenType savedOp = lastOperator;
            lastOperator = TokenType.MINUS;
            ASTNode result = new UnaryOpNode("-", parseFactor());
            lastOperator = savedOp;
            return result;
        }
        
        // Handle numbers
        if (tok.getType() == TokenType.NUMBER) {
            lastOperator = null;  // Reset after consuming a number
            advance();
            return new NumberNode(Double.parseDouble(tok.getValue()));
        }
        
        // Handle parenthesized expressions
        if (tok.getType() == TokenType.LPAREN) {
            advance();
            TokenType savedOp = lastOperator;
            lastOperator = null;  // Reset for nested expression
            ASTNode expr = parseExpression();
            lastOperator = savedOp;
            if (current.getType() != TokenType.RPAREN) {
                throw new RuntimeException("Expected closing parenthesis");
            }
            advance();
            return expr;
        }
        
        throw new RuntimeException("Unexpected token: " + tok);
    }
    
    // Parse a term: handles * and / operations
    // term ::= factor ((MULTIPLY | DIVIDE) factor)*
    private ASTNode parseTerm() {
        ASTNode left = parseFactor();
        
        while (current.getType() == TokenType.MULTIPLY || current.getType() == TokenType.DIVIDE) {
            String op = current.getValue();
            advance();
            ASTNode right = parseFactor();
            left = new BinaryOpNode(left, op, right);
        }
        
        return left;
    }
    
    // Parse an expression: handles + and - operations
    // expression ::= term ((PLUS | MINUS) term)*
    private ASTNode parseExpression() {
        ASTNode left = parseTerm();
        
        while (current.getType() == TokenType.PLUS || current.getType() == TokenType.MINUS) {
            String op = current.getValue();
            TokenType opType = current.getType();
            advance();
            lastOperator = opType;  // Track the binary operator
            ASTNode right = parseTerm();
            lastOperator = null;  // Reset after parsing the operand
            left = new BinaryOpNode(left, op, right);
        }
        
        return left;
    }
    
    // Main parsing method - returns the root of the AST
    public ASTNode parse() {
        ASTNode tree = parseExpression();
        
        if (current.getType() != TokenType.EOF) {
            throw new RuntimeException("Unexpected tokens after expression");
        }
        
        return tree;
    }
}
