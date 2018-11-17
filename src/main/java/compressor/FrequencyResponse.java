package compressor;

import org.immutables.value.Value;

import java.util.Map;

@Value.Immutable
public interface FrequencyResponse {
  Map<Byte, Integer> getFrequencyMap();

  int getNumberOfBytesRead();
}
