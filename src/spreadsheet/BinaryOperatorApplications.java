package spreadsheet;

import common.api.CellLocation;
import common.api.EvaluationContext;
import common.api.Expression;
import common.lexer.Token.Kind;
import java.util.Set;

public class BinaryOperatorApplications implements Expression {
  private final Expression subExpression1;
  private final Expression subExpression2;
  private final Kind binaryOperator;

  public BinaryOperatorApplications(Expression subExpression1, Expression subExpression2,
      Kind binaryOperator) {
    this.subExpression1 = subExpression1;
    this.subExpression2 = subExpression2;
    this.binaryOperator = binaryOperator;
  }

  @Override
  public String toString() {
    return "(" + subExpression1 + switch (binaryOperator) {
      case PLUS ->  "+";
      case MINUS -> "-";
      case STAR -> "*";
      case SLASH -> "/";
      case CARET -> "^";
      case LANGLE -> "<";
      case RANGLE -> ">";
      case EQUALS -> "=";
      default -> "Error";
    } + subExpression2 + ")";
  }

  @Override
  public double evaluate(EvaluationContext context) {
    double evaluatedExpression1 = subExpression1.evaluate(context);
    double evaluatedExpression2 = subExpression2.evaluate(context);
    switch (binaryOperator) {
      case PLUS -> {
        return evaluatedExpression1 + evaluatedExpression2;
      }
      case MINUS -> {
        return evaluatedExpression1 - evaluatedExpression2;
      }
      case STAR -> {
        return evaluatedExpression1 * evaluatedExpression2;
      }
      case SLASH -> {
        return evaluatedExpression1 / evaluatedExpression2;
      }
      case CARET -> {
        return Math.pow(evaluatedExpression1, evaluatedExpression2);
      }
      case LANGLE -> {
        return evaluatedExpression1 < evaluatedExpression2 ? 1.0 : 0.0;
      }
      case RANGLE -> {
        return evaluatedExpression1 > evaluatedExpression2 ? 1.0 : 0.0;
      }
      case EQUALS -> {
        return evaluatedExpression1 == evaluatedExpression2 ? 1.0 : 0.0;
      }
      default ->
        throw new IllegalArgumentException("This is not a binary operator");
    }
  }

  @Override
  public void findCellReferences(Set<CellLocation> dependencies) {
    subExpression1.findCellReferences(dependencies);
    subExpression2.findCellReferences(dependencies);
  }
}
