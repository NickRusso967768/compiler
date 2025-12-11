package compiler.ast;

/**
 * Abstract base class for all nodes in the Abstract Syntax Tree (AST).
 * Each node represents a component of the arithmetic expression.
 */
public abstract class ASTNode {
    /**
     * Accepts a visitor for traversing the AST.
     * This enables the Visitor pattern for evaluation and other operations.
     */
    public abstract <T> T accept(ASTVisitor<T> visitor);
    
    /**
     * Returns a string representation of the AST node.
     */
    @Override
    public abstract String toString();
}
