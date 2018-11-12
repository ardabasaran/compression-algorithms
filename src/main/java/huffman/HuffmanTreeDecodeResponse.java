package huffman;

import org.immutables.value.Value;

@Value.Immutable
public interface HuffmanTreeDecodeResponse {
  Byte getDecoded();

  @Value.Default
  default String getRemaining() {
    return "";
  }
}
