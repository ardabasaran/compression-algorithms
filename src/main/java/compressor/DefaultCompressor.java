package compressor;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class DefaultCompressor implements Compressor {
  Encoder encoder;
  Decoder decoder;

  private DefaultCompressor(Encoder encoder, Decoder decoder) {
    this.encoder = encoder;
    this.decoder = decoder;
  }

  public static DefaultCompressor newInstance(Encoder encoder, Decoder decoder) {
    return new DefaultCompressor(encoder, decoder);
  }

  @Override
  public void encode(InputStream input, OutputStream output) {
    encoder.encode(input, output);
  }

  @Override
  public void decode(InputStream input, OutputStream output) {
    decoder.decode(input, output);
  }
}
