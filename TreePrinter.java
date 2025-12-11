package compiler;

import compiler.ast.*;

/**
 * Utility class to print the Abstract Syntax Tree (AST) in a visual format.
 * Displays the parse tree structure with branches and indentation.
 */
public class TreePrinter implements ASTVisitor<String> {
    
    private static final String BRANCH = "├── ";
    private static final String LAST_BRANCH = "└── ";
    private static final String VERTICAL = "│   ";
    private static final String EMPTY = "    ";
    
    /**
     * Print the AST as a visual tree
     */
    public static void printTree(ASTNode root) {
        System.out.println("Parse Tree Visualization:");
        System.out.println(root.accept(new TreePrinter()));
    }
    
    /**
     * Internal method to print with indentation
     */
    private static String printWithIndent(ASTNode node, String indent, boolean isLast) {
        StringBuilder sb = new StringBuilder();
        
        String prefix = isLast ? LAST_BRANCH : BRANCH;
        String nodeStr = node.accept(new TreePrinter());
        
        sb.append(indent).append(prefix).append(nodeStr).append("\n");
        
        String newIndent = indent + (isLast ? EMPTY : VERTICAL);
        
        if (node instanceof BinaryOpNode) {
            BinaryOpNode binNode = (BinaryOpNode) node;
            sb.append(printWithIndent(binNode.getLeft(), newIndent, false));
            sb.append(printWithIndent(binNode.getRight(), newIndent, true));
        } else if (node instanceof UnaryOpNode) {
            UnaryOpNode unaryNode = (UnaryOpNode) node;
            sb.append(printWithIndent(unaryNode.getOperand(), newIndent, true));
        }
        
        return sb.toString();
    }
    
    @Override
    public String visitNumberNode(NumberNode node) {
        long val = (long) node.getValue();
        if (val == node.getValue()) {
            return "NUMBER: " + val;
        }
        return "NUMBER: " + node.getValue();
    }
    
    @Override
    public String visitBinaryOpNode(BinaryOpNode node) {
        return "OP: " + node.getOperator();
    }
    
    @Override
    public String visitUnaryOpNode(UnaryOpNode node) {
        return "UNARY_OP: " + node.getOperator();
    }
    
    /**
     * Print tree starting from root with full visualization
     */
    public static void visualizeTree(ASTNode root) {
        System.out.println("Parse Tree Structure:");
        System.out.println(printWithIndent(root, "", true));
    }
}
