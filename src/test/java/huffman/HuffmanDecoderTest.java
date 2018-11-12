package huffman;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.*;

public class HuffmanDecoderTest {
  InputStream decodeHs;
  InputStream decodeIs;

  @Before
  public void setUp() throws Exception {
    String inputString = "aaaabbbccbaabcbab";
    InputStream hs = new ByteArrayInputStream(inputString.getBytes(StandardCharsets.UTF_8));
    InputStream is = new ByteArrayInputStream(inputString.getBytes(StandardCharsets.UTF_8));
    HuffmanEncoder encoder = new HuffmanEncoder(hs);

    ByteArrayOutputStream os = new ByteArrayOutputStream();
    encoder.encode(is, os);
    byte[] out = os.toByteArray();
    decodeIs = new ByteArrayInputStream(out);
    decodeHs = new ByteArrayInputStream(out);
  }

  @Test
  public void decode() {
    HuffmanDecoder decoder = new HuffmanDecoder(decodeHs);
    ByteArrayOutputStream os = new ByteArrayOutputStream();
    decoder.decode(decodeIs, os);
    String decoded = os.toString(StandardCharsets.UTF_8);
    System.out.println(decoded);
  }
}