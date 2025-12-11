package compiler;

import compiler.ast.ASTNode;
import java.util.List;
import java.util.Scanner;

/**
 * Main Compiler - orchestrates all three compilation phases.
 * Demonstrates how compilers process source code step by step.
 */
public class Compiler {
    
    // Compile and evaluate an arithmetic expression
    public static double compile(String expression) {
        System.out.println("\n========================================");
        System.out.println("Input: " + expression);
        System.out.println("========================================");
        
        // Phase 1: Lexical Analysis
        System.out.println("\nPhase 1: LEXICAL ANALYSIS");
        System.out.println("----------------------------------------");
        Lexer lexer = new Lexer(expression);
        List<Token> tokens = lexer.tokenize();
        
        System.out.print("Tokens: [");
        for (int i = 0; i < tokens.size(); i++) {
            Token tok = tokens.get(i);
            if (tok.getType() != TokenType.EOF) {
                if (i > 0) System.out.print(", ");
                System.out.print(tok.getValue() != null ? tok.getValue() : tok.getType());
            }
        }
        System.out.println("]");
        
        // Phase 2: Parsing
        System.out.println("\nPhase 2: PARSING");
        System.out.println("----------------------------------------");
        Parser parser = new Parser(tokens);
        ASTNode ast = parser.parse();
        
        System.out.println("Parse Result: SUCCESS");
        System.out.println("\nExpression Tree (Linear): " + ast.toString());
        System.out.println();
        TreePrinter.visualizeTree(ast);
        
        // Phase 3: Evaluation
        System.out.println("\nPhase 3: EVALUATION");
        System.out.println("----------------------------------------");
        Evaluator eval = new Evaluator();
        double result = eval.evaluate(ast);
        
        System.out.println("Evaluation Result: " + result);
        System.out.println("========================================\n");
        
        return result;
    }
    
    public static void main(String[] args) {
        System.out.println("==========================================");
        System.out.println("  ARITHMETIC EXPRESSION COMPILER");
        System.out.println("==========================================");
        
        // Test cases - including all required scenarios from assignment
        String[] tests = {
            "3 + 4 * 2",                    // Valid with precedence
            "(1 + 2) * (3 + 4)",           // Valid with parentheses
            "(3 + 2) * 5 - 1",             // Sample from assignment
            "((3))",                        // Nested parentheses
            "-3",                           // Unary operator
            "-5 + 3",                       // Unary with binary
            "-(5 + 3) * 2"                  // Unary on expression
        };
        
        System.out.println("\n=== Valid Test Cases ===\n");
        
        for (String expr : tests) {
            try {
                compile(expr);
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
                System.out.println();
            }
        }
        
        // Invalid test cases
        String[] invalidTests = {
            "3 + * 5",                      // Invalid: consecutive operators
            "()",                            // Invalid: empty parentheses
            "3 + (4 - )"                    // Invalid: incomplete expression
        };
        
        System.out.println("\n=== Invalid Test Cases (Error Handling) ===\n");
        
        for (String expr : invalidTests) {
            System.out.println("\n========================================");
            System.out.println("Input: " + expr);
            System.out.println("========================================");
            
            // Phase 1: Lexical Analysis
            System.out.println("\nPhase 1: LEXICAL ANALYSIS");
            System.out.println("----------------------------------------");
            try {
                Lexer lexer = new Lexer(expr);
                List<Token> tokens = lexer.tokenize();
                
                System.out.print("Tokens: [");
                for (int i = 0; i < tokens.size(); i++) {
                    Token tok = tokens.get(i);
                    if (tok.getType() != TokenType.EOF) {
                        if (i > 0) System.out.print(", ");
                        System.out.print(tok.getValue() != null ? tok.getValue() : tok.getType());
                    }
                }
                System.out.println("]");
                
                // Phase 2: Parsing
                System.out.println("\nPhase 2: PARSING");
                System.out.println("----------------------------------------");
                Parser parser = new Parser(tokens);
                ASTNode ast = parser.parse();
                
                System.out.println("Parse Result: SUCCESS");
                System.out.println("Expression Tree: " + ast.toString());
                
            } catch (Exception e) {
                System.out.println("Parse Result: FAILURE");
                System.err.println("Error: " + e.getMessage());
            }
            System.out.println("========================================\n");
        }
        
        // Interactive mode
        System.out.println("\n=== Interactive Mode ===");
        System.out.println("Enter expressions to evaluate (type 'quit' to exit)\n");
        
        Scanner input = new Scanner(System.in);
        boolean running = true;
        
        while (running) {
            System.out.print(">>> ");
            if (!input.hasNextLine()) {
                break;
            }
            String line = input.nextLine().trim();
            
            if (line.equalsIgnoreCase("quit") || line.equalsIgnoreCase("exit")) {
                System.out.println("Goodbye!");
                break;
            }
            
            if (line.isEmpty()) continue;
            
            try {
                compile(line);
            } catch (Exception e) {
                System.out.println("\n========================================");
                System.out.println("Parse Result: FAILURE");
                System.err.println("Error: " + e.getMessage());
                System.out.println("========================================\n");
            }
        }
        
        input.close();
    }
}
