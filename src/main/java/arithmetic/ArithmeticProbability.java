package arithmetic;

import org.immutables.value.Value;

@Value.Immutable
public interface ArithmeticProbability {
  ArithmeticInteger getUpper();

  ArithmeticInteger getLower();

  ArithmeticInteger getDenominator();
}
