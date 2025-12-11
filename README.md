# Mini Expression Compiler

An arithmetic expression compiler written in Java. Takes a math expression as input and evaluates it through three compilation phases.


## What It Does

The program processes expressions in three steps:

1. **Lexer** - Breaks input into tokens (numbers, operators, parentheses)
2. **Parser** - Checks if the expression follows proper grammar rules and builds a tree
3. **Evaluator** - Calculates the result by traversing the tree

## Example

Input: `(3 + 2) * 5 - 1`

Output:
```
Tokens: [(, 3, +, 2, ), *, 5, -, 1]
Parse Result: SUCCESS
Expression Tree: (((3.0 + 2.0) * 5.0) - 1.0)
Evaluation Result: 24.0
```

## Supported Operations

- Basic operators: `+`, `-`, `*`, `/`
- Parentheses for grouping
- Unary operators: `-3`, `-(5 + 3)`
- Proper operator precedence (multiplication/division before addition/subtraction)

## Test Cases

Valid:
- `3 + 4 * 2` = 11
- `(1 + 2) * (3 + 4)` = 21
- `-(5 + 3) * 2` = -16

Invalid (shows error):
- `3 + * 5`
- `()`
- `3 + (4 - )`

## Files

- `Compiler.java` - Main program
- `Lexer.java` - Tokenization
- `Parser.java` - Grammar checking and tree building
- `Evaluator.java` - Result calculation
- `Token.java` / `TokenType.java` - Token definitions
- `ast/` folder - Tree node classes
"# compiler-220" 
