package spreadsheet;

import common.api.CellLocation;
import common.api.EvaluationContext;
import common.api.Expression;
import java.util.Set;

public class Numbers implements Expression {
  private final double number;

  public Numbers(Number number) {
    this.number = number.doubleValue();
  }

  public double getNumber() {
    return number;
  }

  @Override
  public String toString() {
    return Double.toString(number);
  }

  @Override
  public double evaluate(EvaluationContext context) {
    return number;
  }

  @Override
  public void findCellReferences(Set<CellLocation> dependencies) {

  }
}
