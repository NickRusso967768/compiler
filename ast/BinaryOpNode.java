package compiler.ast;

/**
 * Represents a binary operation (internal node) in the expression tree.
 * A binary operation has a left operand, an operator, and a right operand.
 */
public class BinaryOpNode extends ASTNode {
    private final ASTNode left;
    private final String operator;
    private final ASTNode right;
    
    public BinaryOpNode(ASTNode left, String operator, ASTNode right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }
    
    public ASTNode getLeft() {
        return left;
    }
    
    public String getOperator() {
        return operator;
    }
    
    public ASTNode getRight() {
        return right;
    }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitBinaryOpNode(this);
    }
    
    @Override
    public String toString() {
        return "(" + left.toString() + " " + operator + " " + right.toString() + ")";
    }
}
