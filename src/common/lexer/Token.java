package common.lexer;

import static common.lexer.Token.Kind.CELL_LOCATION;
import static common.lexer.Token.Kind.NUMBER;

import common.api.CellLocation;
import java.util.Objects;

/**
 * Representation of a token from the input string.
 *
 * <p>If `kind` if NUMBER or CELL_LOCATION, then the represented value may be found respectively in
 * `numberValue` and `cellLocationValue`.
 */
public class Token {
  public final Kind kind;
  public final CellLocation cellLocationValue;
  public final double numberValue;

  Token(Kind kind) {
    this(kind, null, 0);
    assert (kind != NUMBER && kind != CELL_LOCATION);
  }

  Token(double value) {
    this(NUMBER, null, value);
  }

  Token(CellLocation cellLocation) {
    this(CELL_LOCATION, cellLocation, 0);
  }

  private Token(Kind kind, CellLocation cellLocationValue, double numberValue) {
    this.kind = kind;
    this.cellLocationValue = cellLocationValue;
    this.numberValue = numberValue;
  }

  @Override
  public String toString() {
    switch (kind) {
      case CELL_LOCATION:
        return "CELL(" + cellLocationValue.toString() + ")";
      case NUMBER:
        return "NUMBER(" + numberValue + ")";
      default:
        return kind.name();
    }
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Token other = (Token) obj;
    if (this.kind != other.kind) {
      return false;
    }
    switch (this.kind) {
      case NUMBER:
        return this.numberValue == other.numberValue;
      case CELL_LOCATION:
        return this.cellLocationValue.equals(other.cellLocationValue);
      default:
        return true;
    }
  }

  @Override
  public int hashCode() {
    switch (kind) {
      case NUMBER:
        return Objects.hash(kind, numberValue);
      case CELL_LOCATION:
        return Objects.hash(kind, cellLocationValue);
      default:
        return Objects.hash(kind);
    }
  }

  public enum Kind {
    PLUS(3, Associativity.LEFT, Arity.BINARY),
    MINUS(3, Associativity.LEFT, Arity.BINARY),
    STAR(4, Associativity.LEFT, Arity.BINARY),
    SLASH(4, Associativity.LEFT, Arity.BINARY),
    CARET(6, Associativity.RIGHT, Arity.BINARY),
    LPARENTHESIS(0, Associativity.NONE, Arity.ZERO),
    RPARENTHESIS(0, Associativity.NONE, Arity.ZERO),
    LANGLE(2, Associativity.NONE, Arity.BINARY),
    RANGLE(2, Associativity.NONE, Arity.BINARY),
    EQUALS(1, Associativity.NONE, Arity.BINARY),
    NUMBER(7, Associativity.NONE, Arity.ZERO),
    CELL_LOCATION(7, Associativity.NONE, Arity.ZERO);

    public int precedence;
    public Associativity associativity;
    public Arity arity;

    Kind(int precedence, Associativity associativity, Arity arity) {
      this.precedence = precedence;
      this.associativity = associativity;
      this.arity = arity;
    }
  }

  public enum Associativity {
    LEFT,
    RIGHT,
    NONE,
  }

  public enum Arity {
    UNARY,
    BINARY,
    ZERO,
  }
}
