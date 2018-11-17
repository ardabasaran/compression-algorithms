package arithmetic;

public class ProbabilityModel {
  private ArithmeticInteger[] cumulativeFrequency;
  private boolean modelFrozen;

  public ProbabilityModel() {
    cumulativeFrequency = new ArithmeticInteger[257];
    for (int i = 0; i < 256; i++) {
      cumulativeFrequency[i] = ArithmeticInteger.fromInt(i+1);
    }
    cumulativeFrequency[256] = ArithmeticInteger.fromInt(256);

    modelFrozen = false;
  }

  ArithmeticProbability getProbability(byte b) {
    ArithmeticProbability probability = ImmutableArithmeticProbability.builder()
        .lower(cumulativeFrequency[(int) b & 0xFF])
        .upper(cumulativeFrequency[((int) b+1)& 0xFF])
        .denominator(cumulativeFrequency[256])
        .build();
    if (!modelFrozen) {
      update((int) b);
    }
    return probability;
  }

  private void update(int b) {
    for (int i = b; i < 257; i++) {
      cumulativeFrequency[i] = cumulativeFrequency[i].plus(ArithmeticInteger.fromInt(1));
    }
    if (cumulativeFrequency[256].compareTo(ArithmeticConstants.MAX_FREQ) >= 0) {
      modelFrozen = true;
    }
  }

  public ByteWithProbability getByteProbability(ArithmeticInteger value) {
    for (int i = 0; i < 256; i++) {
      if (value.compareTo(cumulativeFrequency[i+1]) < 0) {
        ArithmeticProbability probability = ImmutableArithmeticProbability.builder()
            .lower(cumulativeFrequency[i])
            .upper(cumulativeFrequency[i+1])
            .denominator(cumulativeFrequency[256])
            .build();
        if (!modelFrozen) {
          update(i);
        }
        return ImmutableByteWithProbability.builder()
            .probability(probability)
            .byteValue((byte) i)
            .build();
      }
    }
    throw new RuntimeException("Byte probability is not found.");
  }

  public ArithmeticInteger getCount() {
    return cumulativeFrequency[256];
  }
}