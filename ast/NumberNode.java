package compiler.ast;

/**
 * Represents a number (leaf node) in the expression tree.
 */
public class NumberNode extends ASTNode {
    private final double value;
    
    public NumberNode(double value) {
        this.value = value;
    }
    
    public double getValue() {
        return value;
    }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitNumberNode(this);
    }
    
    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
