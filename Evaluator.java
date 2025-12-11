package compiler;

import compiler.ast.*;

/**
 * Evaluator - Phase 3 of compilation.
 * Walks through the AST and computes the result of the expression.
 * Uses the Visitor pattern to traverse different node types.
 */
public class Evaluator implements ASTVisitor<Double> {
    
    // Main method to evaluate the expression tree
    public double evaluate(ASTNode root) {
        return root.accept(this);
    }
    
    // Visit a number node - just return its value
    @Override
    public Double visitNumberNode(NumberNode node) {
        return node.getValue();
    }
    
    // Visit a binary operation node - evaluate both sides and apply operator
    @Override
    public Double visitBinaryOpNode(BinaryOpNode node) {
        double left = node.getLeft().accept(this);
        double right = node.getRight().accept(this);
        String op = node.getOperator();
        
        if (op.equals("+")) return left + right;
        if (op.equals("-")) return left - right;
        if (op.equals("*")) return left * right;
        if (op.equals("/")) {
            if (right == 0) throw new RuntimeException("Division by zero");
            return left / right;
        }
        
        throw new RuntimeException("Unknown operator: " + op);
    }
    
    // Visit a unary operation node - evaluate operand and apply operator
    @Override
    public Double visitUnaryOpNode(UnaryOpNode node) {
        double value = node.getOperand().accept(this);
        String op = node.getOperator();
        
        if (op.equals("+")) return value;
        if (op.equals("-")) return -value;
        
        throw new RuntimeException("Unknown unary operator: " + op);
    }
}
