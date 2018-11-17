package arithmetic;

import org.immutables.value.Value;

@Value.Immutable
public interface ByteWithProbability {
  ArithmeticProbability getProbability();

  byte getByteValue();
}
