package spreadsheet;

import common.api.BasicSpreadsheet;
import common.api.CellLocation;
import common.api.EvaluationContext;
import common.api.Expression;
import java.util.HashSet;
import java.util.Set;

/**
 * A single cell in a spreadsheet, tracking the expression, value, and other parts of cell state.
 */
public class Cell {
  private final BasicSpreadsheet spreadsheet;
  private final CellLocation location;
  private double value;
  public enum State {
    EMPTY,
    NONEMPTY;
  }
  private State cellState;
  private Expression expression;
  private final Set<CellLocation> dependents = new HashSet<>();

  /**
   * Constructs a new cell.
   *
   * <p>DO NOT CHANGE THE SIGNATURE. The test suite depends on this.
   *
   * @param spreadsheet The parent spreadsheet,
   * @param location The location of this cell in the spreadsheet.
   */
  Cell(BasicSpreadsheet spreadsheet, CellLocation location) {
    this.spreadsheet = spreadsheet;
    this.location = location;
    cellState = State.EMPTY;
    value = 0.0;
  }

  /**
   * Gets the cell's last calculated value.
   *
   * <p>DO NOT CHANGE THE SIGNATURE. The test suite depends on this.
   *
   * @return the cell's value.
   */
  public double getValue() {
    return value;
  }

  public Set<CellLocation> getDependents() {
    return Set.copyOf(dependents);
  }

  /**
   * Gets the cell's last stored expression, in string form.
   *
   * <p>DO NOT CHANGE THE SIGNATURE. The test suite depends on this.
   *
   * @return a string that parses to an equivalent expression to that last stored in the cell; if no
   *     expression is stored, we return the empty string.
   */
  public String getExpression() {
    if (cellState == State.EMPTY) {
      return "";
    } else {
      return expression.toString();
    }
  }

  /**
   * Sets the cell's expression from a string.
   *
   * <p>DO NOT CHANGE THE SIGNATURE. The test suite depends on this.
   *
   * @param input The string representing the new cell expression.
   * @throws InvalidSyntaxException if the string cannot be parsed.
   */
  public void setExpression(String input) throws InvalidSyntaxException {
    Set<CellLocation> locationSet = new HashSet<>();
    if (expression != null) {
      findCellReferences(locationSet);
    }
    locationSet.forEach(p -> spreadsheet.removeDependency(this.location, p));
    if (input.isEmpty()) {
      cellState = State.EMPTY;
    } else {
      cellState = State.NONEMPTY;
      expression = Parser.parse(input);
      Set<CellLocation> locationSet1 = new HashSet<>();
      expression.findCellReferences(locationSet1);
      locationSet1.forEach(p -> spreadsheet.addDependency(this.location, p));
    }
  }

  /** @return a string representing the value, if any, of this cell. */
  @Override
  public String toString() {
    if (cellState == State.EMPTY) {
      return "";
    } else {
      return Double.toString(value);
    }
  }

  /**
   * Adds the given location to this cell's dependents.
   *
   * <p>DO NOT CHANGE THE SIGNATURE. The test suite depends on this.
   *
   * @param location the location to add.
   */
  public void addDependent(CellLocation location) {
    dependents.add(location);
  }

  /**
   * Adds the given location to this cell's dependents.
   *
   * <p>DO NOT CHANGE THE SIGNATURE. The test suite depends on this.
   *
   * @param location the location to add.
   */
  public void removeDependent(CellLocation location) {
    dependents.remove(location);
  }

  /**
   * Adds this cell's expression's references to a set.
   *
   * <p>DO NOT CHANGE THE SIGNATURE. The test suite depends on this.
   *
   * @param target The set that will receive the dependencies for this
   */
  public void findCellReferences(Set<CellLocation> target) {
    expression.findCellReferences(target);
  }

  /**
   * Recalculates this cell's value based on its expression.
   *
   * <p>DO NOT CHANGE THE SIGNATURE. The test suite depends on this.
   */
  public void recalculate() {
    if (cellState != State.EMPTY) {
      value = expression.evaluate(spreadsheet);
    } else {
      value = 0.0;
    }
    dependents.forEach(spreadsheet::recalculate);
  }
}
