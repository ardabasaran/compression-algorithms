package arithmetic;

public class ArithmeticConstants {
  static final int PRECISION  = 32;
  static final int CODE_VALUE_BITS = 17;
  static final int FREQUENCY_BITS = PRECISION - CODE_VALUE_BITS;

  static final ArithmeticInteger MAX_CODE = (ArithmeticInteger.fromInt(1).shiftLeft(CODE_VALUE_BITS))
      .minus(ArithmeticInteger.fromInt(1));
  static final ArithmeticInteger MAX_FREQ = (ArithmeticInteger.fromInt(1).shiftLeft(FREQUENCY_BITS))
      .minus(ArithmeticInteger.fromInt(1));
  static final ArithmeticInteger ONE_FOURTH = ArithmeticInteger.fromInt(1).shiftLeft((CODE_VALUE_BITS - 2));
  static final ArithmeticInteger ONE_HALF = ONE_FOURTH.times(ArithmeticInteger.fromInt(2));
  static final ArithmeticInteger THREE_FOURTHS = ONE_FOURTH.times(ArithmeticInteger.fromInt(3));;
}
