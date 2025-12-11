package compiler.ast;

/**
 * Visitor interface for traversing the Abstract Syntax Tree.
 * Implements the Visitor design pattern to separate tree traversal
 * from operations performed on the tree (e.g., evaluation, printing).
 */
public interface ASTVisitor<T> {
    T visitNumberNode(NumberNode node);
    T visitBinaryOpNode(BinaryOpNode node);
    T visitUnaryOpNode(UnaryOpNode node);
}
