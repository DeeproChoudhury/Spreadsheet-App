package spreadsheet;

import static org.junit.Assert.assertThat;
import static spreadsheet.EvaluationMatcher.evaluatesTo;

import org.junit.Test;

public class TestEvaluation {
  @Test
  public void testNumber() {
    assertThat("5", evaluatesTo(5));
    assertThat("10", evaluatesTo(10));
    assertThat("5.678", evaluatesTo(5.678));
  }

  @Test
  public void testCellLocation() {
    assertThat("a1", evaluatesTo(0));
    assertThat("a1", evaluatesTo(5).with("a1", 5));
    assertThat("b1", evaluatesTo(5).with("b1", 5));
    assertThat("abc123", evaluatesTo(-5).with("abc123", -5));
    assertThat("a1", evaluatesTo(0));
  }

  @Test
  public void testBinaryOperator() {
    assertThat("2 + 4", evaluatesTo(6));
    assertThat("2 * 4", evaluatesTo(8));
    assertThat("2 - 4", evaluatesTo(-2));
    assertThat("2 / 4", evaluatesTo(0.5));
    assertThat("2 ^ 4", evaluatesTo(16));
    assertThat("a1 + 3", evaluatesTo(5).with("a1", 2));
    assertThat("a1 + b1", evaluatesTo(6).with("a1", 2).with("b1", 4));
  }

  //Added by me to test multiple operations
  @Test
  public void testMultipleOperationsAndPrecedence() {
    assertThat("2 + 4 + 6", evaluatesTo(12));
    assertThat("2 - 4 + 6", evaluatesTo(4));
    assertThat("2 * 4 / 8", evaluatesTo(1));
    assertThat("2 ^ 4 + 6", evaluatesTo(22));
    assertThat("a1 + b1 + 7", evaluatesTo(12).with("a1", 2).with("b1", 3));
    assertThat("a1 / b1 + c1", evaluatesTo(7)
        .with("a1", 10).with("b1", 2).with("c1", 2));
  }

  //Added by me to test less than and greater than operators which output 1 - true and 0 - false
  @Test
  public void testAngleBrackets() {
    assertThat("1 + 2 < 5", evaluatesTo(1));
    assertThat("7 * 5 > 19", evaluatesTo(1));
    assertThat("3 ^ 6 < 20", evaluatesTo(0));
    assertThat("3 + 6 < 9", evaluatesTo(0));
  }

  //Added by me to test equals operator, returning 1 if true, 0 if false
  @Test
  public void testEquals() {
    assertThat("1 + 2 = 3", evaluatesTo(1.0));
    assertThat("2 + 3 = 7", evaluatesTo(0.0));
    assertThat("2 ^ 3 = 2 ^ 3", evaluatesTo(1.0));
  }

  //Added by me to test that expressions in brackets are evaluated first
  @Test
  public void testBrackets() {
    assertThat("(1 + 2) * 3", evaluatesTo(9));
    assertThat("(1 + 5) ^ 3", evaluatesTo(216));
    assertThat("(1 + 9) / (2 + 3)", evaluatesTo(2));
    assertThat("(2 + 3) ^ (1.5 * 2)", evaluatesTo(125));
  }

}