package compiler.ast;

/**
 * Represents a unary operation (internal node) in the expression tree.
 * A unary operation has an operator and a single operand.
 * Example: -5 (unary minus)
 */
public class UnaryOpNode extends ASTNode {
    private final String operator;
    private final ASTNode operand;
    
    public UnaryOpNode(String operator, ASTNode operand) {
        this.operator = operator;
        this.operand = operand;
    }
    
    public String getOperator() {
        return operator;
    }
    
    public ASTNode getOperand() {
        return operand;
    }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitUnaryOpNode(this);
    }
    
    @Override
    public String toString() {
        return "(" + operator + operand.toString() + ")";
    }
}
