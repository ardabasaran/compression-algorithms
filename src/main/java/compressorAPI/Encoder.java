package compressorAPI;

import java.io.InputStream;
import java.io.OutputStream;

public interface Encoder {
  void encode(InputStream inputStream, OutputStream outputStream);
}
