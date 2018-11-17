package arithmetic;

public class ArithmeticInteger {
  private long value;

  private ArithmeticInteger(long value) {
    this.value = value;
  }

  public static ArithmeticInteger fromInt(int v) {
    return new ArithmeticInteger((long) v);
  }

  public static ArithmeticInteger fromLong(long v) {
    return new ArithmeticInteger(v);
  }

  public ArithmeticInteger plus(ArithmeticInteger a) {
    return fromLong((this.value + a.value) & 0xFFFFFFFFL);
  }

  public ArithmeticInteger minus(ArithmeticInteger a) {
    return fromLong((this.value - a.value) & 0xFFFFFFFFL);
  }

  public ArithmeticInteger times(ArithmeticInteger a) {
    return fromLong((this.value * a.value) & 0xFFFFFFFFL);
  }

  public ArithmeticInteger dividedBy(ArithmeticInteger a) {
    return fromLong((this.value / a.value) & 0xFFFFFFFFL);
  }

  public int compareTo(ArithmeticInteger a) {
    if (this.value - a.value > 0) {
      return 1;
    } else if (this.value - a.value < 0) {
      return -1;
    } else {
      return 0;
    }
  }

  public ArithmeticInteger shiftLeft(int i) {
    return fromLong((this.value << i) & 0xFFFFFFFFL);
  }

  public ArithmeticInteger shiftRight(int i) {
    return fromLong((this.value >> i) & 0xFFFFFFFFL);
  }

  public String toBinaryString() {
    return String.format("%32s", Long.toBinaryString(value)).replace(' ', '0');
  }

  public ArithmeticInteger and(ArithmeticInteger a) {
    return fromLong((this.value & a.value) & 0xFFFFFFFFL);
  }

  public ArithmeticInteger or(ArithmeticInteger a) {
    return fromLong((this.value | a.value) & 0xFFFFFFFFL);
  }

  public long getValue() {
    return  value;
  }
}
