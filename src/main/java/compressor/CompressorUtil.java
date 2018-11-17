package compressor;

import com.google.common.collect.Maps;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class CompressorUtil {
  public static FrequencyResponse getFrequencyMap(InputStream inputStream) {
    int fileLength = 0;
    Map<Byte, Integer> frequencies = Maps.newHashMap();
    try {
      int data = inputStream.read();
      while (data != -1) {
        fileLength += 1;
        Byte dataByte = (byte) data;
        frequencies.putIfAbsent(dataByte, 0);
        frequencies.put(dataByte, frequencies.get(dataByte) + 1);
        data = inputStream.read();
      }
    } catch (IOException e) {
      System.err.println(e.getMessage());
    }
    return ImmutableFrequencyResponse.builder()
        .frequencyMap(frequencies)
        .numberOfBytesRead(fileLength)
        .build();
  }
}
