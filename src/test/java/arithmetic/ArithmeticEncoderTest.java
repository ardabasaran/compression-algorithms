package arithmetic;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class ArithmeticEncoderTest {
  @Test
  public void encode() {
    String inputString = "aaaabbbccbaabcbab";
    InputStream hs = new ByteArrayInputStream(inputString.getBytes(StandardCharsets.UTF_8));
    InputStream is = new ByteArrayInputStream(inputString.getBytes(StandardCharsets.UTF_8));
    ArithmeticEncoder encoder = new ArithmeticEncoder(hs);

    ByteArrayOutputStream os = new ByteArrayOutputStream();
    encoder.encode(is, os);
    String encoded = os.toString(StandardCharsets.UTF_8);
    System.out.println(encoded);
  }

}