package spreadsheet;

import common.api.BasicSpreadsheet;
import common.api.CellLocation;
import common.api.Expression;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public class Spreadsheet implements BasicSpreadsheet {
  //
  // start replacing
  //
  private final Map<CellLocation, Cell> cellLocationMap = new HashMap<>();
  /**
   * Construct an empty spreadsheet.
   *
   * <p>DO NOT CHANGE THE SIGNATURE. The test suite depends on this.
   */
  Spreadsheet() {}

  /**
   * Parse and evaluate an expression, using the spreadsheet as a context.
   *
   * <p>DO NOT CHANGE THE SIGNATURE. The test suite depends on this.
   */
  public double evaluateExpression(String expression) throws InvalidSyntaxException {
    Expression parsedExpression = Parser.parse(expression);
    return parsedExpression.evaluate(this);
  }

  /**
   * Assign an expression to a cell.
   *
   * <p>DO NOT CHANGE THE SIGNATURE. The test suite depends on this.
   */
  public void setCellExpression(CellLocation location, String input)
      throws InvalidSyntaxException {
    Cell newCell = new Cell(this, location);
    if (cellLocationMap.get(location) != null) {
      newCell = cellLocationMap.get(location);
    }
    newCell.setExpression(input);
    newCell.recalculate();
    cellLocationMap.put(location, newCell);
  }

  @Override
  public double getCellValue(CellLocation location) {
    if (cellLocationMap.get(location) != null) {
      return cellLocationMap.get(location).getValue();
    } else {
      return 0.0;
    }
  }
  //
  // end replacing
  //

  @Override
  public String getCellExpression(CellLocation location) {
    return (cellLocationMap.get(location) != null) ? cellLocationMap.get(location).getExpression() : "";
  }

  @Override
  public String getCellDisplay(CellLocation location) {
    return (cellLocationMap.get(location) != null) ? cellLocationMap.get(location).toString() : "";
  }

  @Override
  public void addDependency(CellLocation dependent, CellLocation dependency) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  public void removeDependency(CellLocation dependent, CellLocation dependency) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  public void recalculate(CellLocation location) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  public void findCellReferences(CellLocation subject, Set<CellLocation> target) {
    throw new UnsupportedOperationException("Not implemented yet");
  }
}
