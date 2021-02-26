package spreadsheet;

import common.api.BasicSpreadsheet;
import common.api.CellLocation;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** Detects dependency cycles. */
public class CycleDetector {
  private BasicSpreadsheet spreadsheet;
  private final Set<CellLocation> visited = new HashSet<>();
  /**
   * Constructs a new cycle detector.
   *
   * <p>DO NOT CHANGE THE SIGNATURE. The test suite depends on this.
   *
   * @param spreadsheet The parent spreadsheet, used for resolving cell locations.
   */
  CycleDetector(BasicSpreadsheet spreadsheet) {
    this.spreadsheet = spreadsheet;
  }

  private void visited(CellLocation start) {
    visited.add(start);
  }

  private void removeVisited(CellLocation c) {
    visited.remove(c);
  }
  /**
   * Checks for a cycle in the spreadsheet, starting at a particular cell.
   *
   * <p>DO NOT CHANGE THE SIGNATURE. The test suite depends on this.
   *
   * @param start The cell location where cycle detection should start.
   * @return Whether a cycle was detected in the dependency graph starting at the given cell.
   */
  public boolean hasCycleFrom(CellLocation start) {
    Set<CellLocation> dependencies = new HashSet<>();
    spreadsheet.findCellReferences(start, dependencies);

    if(visited.contains(start)) {
      return true;
    }

    visited(start);

    for (CellLocation c : dependencies) {
      if(hasCycleFrom(c)) {
        return true;
      }
    }
    visited.remove(start);

    return false;
  }




}
