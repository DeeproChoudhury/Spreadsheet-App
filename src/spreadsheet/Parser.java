package spreadsheet;

import common.api.Expression;
import common.lexer.InvalidTokenException;
import common.lexer.Lexer;
import common.lexer.Token;
import common.lexer.Token.Arity;
import common.lexer.Token.Associativity;
import common.lexer.Token.Kind;
import java.util.Stack;

public class Parser {
  /**
   * Parse a string into an Expression.
   *
   * <p>DO NOT CHANGE THE SIGNATURE. The test suite depends on this.
   */

  private final Stack<Expression> expressionStack = new Stack<>();
  private final Stack<Kind> operatorStack = new Stack<>();

  static Expression parse(String input) throws InvalidSyntaxException {
    Parser parser = new Parser();
    Lexer lexer = new Lexer(input);
    while (true) {
      try {
        Token token = lexer.nextToken();
        if (token == null) {
          break;
        } else {
          parser.process(token);
        }
      } catch (InvalidTokenException e) {
        throw new InvalidSyntaxException(e.getMessage());
      }
    }
    while (!parser.operatorStack.isEmpty()) {
      parser.binaryOperatorConstructor();
    }
    return parser.expressionStack.pop();
  }

  public void process(Token token) {
    if (token.kind.arity == Arity.BINARY) {
      if (!operatorStack.isEmpty()) {
        Kind topOfStack = operatorStack.peek();
        if (topOfStack.precedence > token.kind.precedence
            || (topOfStack.precedence == token.kind.precedence
            && topOfStack.associativity == Associativity.LEFT)) {
          binaryOperatorConstructor();
        }
      }
      operatorStack.push(token.kind);
    } else {
      switch (token.kind) {
        case NUMBER -> {
          Expression newNumber = new Numbers(token.numberValue);
          expressionStack.push(newNumber);
        }
        case CELL_LOCATION -> {
          Expression newCell = new CellReferences(token.cellLocationValue);
          expressionStack.push(newCell);
        }
        case RPARENTHESIS -> {
          while (!operatorStack.lastElement().equals(Kind.LPARENTHESIS)) {
            binaryOperatorConstructor();
          }
          operatorStack.pop();
        }
        default -> operatorStack.add(token.kind);
      }
    }
  }

  public void binaryOperatorConstructor() {
    if (expressionStack.size() < 2 || operatorStack.size() < 1) {
      throw new IllegalStateException("Not enough elements in the stack");
    }
    Expression secondExpression = expressionStack.pop();
    Expression firstExpression = expressionStack.pop();
    Kind operator = operatorStack.pop();
    expressionStack.push(new BinaryOperatorApplications(firstExpression,
        secondExpression, operator));
  }
}

