package spreadsheet;

import common.api.CellLocation;
import common.api.EvaluationContext;
import common.api.Expression;
import java.util.Set;

public class CellReferences implements Expression {
  private final CellLocation cellLocation;

  public CellReferences(CellLocation cellLocation) {
    this.cellLocation = cellLocation;
  }

  public CellLocation getCellLocation() {
    return cellLocation;
  }

  @Override
  public double evaluate(EvaluationContext context) {
    return context.getCellValue(cellLocation);
  }

  @Override
  public void findCellReferences(Set<CellLocation> dependencies) {
    dependencies.add(cellLocation);
  }

  @Override
  public String toString() {
    return cellLocation.toString();
  }

}
