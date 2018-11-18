package lzw;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class LzwEncoderTest {
  @Test
  public void encode() {
    String inputString = "aaaabbbccbaabcbab";
    InputStream hs = new ByteArrayInputStream(inputString.getBytes(StandardCharsets.UTF_8));
    InputStream is = new ByteArrayInputStream(inputString.getBytes(StandardCharsets.UTF_8));
    LzwEncoder encoder = new LzwEncoder();

    ByteArrayOutputStream os = new ByteArrayOutputStream();
    encoder.encode(is, os);
    String encoded = os.toString(StandardCharsets.UTF_8);
    System.out.println(encoded);
  }
}