package huffman;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static org.junit.Assert.*;

public class HuffmanEncoderTest {
  @Test
  public void encode() {
    String inputString = "aaaabbbccbaabcbab";
    InputStream hs = new ByteArrayInputStream(inputString.getBytes(StandardCharsets.UTF_8));
    InputStream is = new ByteArrayInputStream(inputString.getBytes(StandardCharsets.UTF_8));
    HuffmanEncoder encoder = new HuffmanEncoder(hs);

    ByteArrayOutputStream os = new ByteArrayOutputStream();
    encoder.encode(is, os);
    String encoded = os.toString(StandardCharsets.UTF_8);
    System.out.println(encoded);
  }
}