package compressorAPI;

import java.io.InputStream;
import java.io.OutputStream;

public interface Decoder {
    void decode(InputStream inputStream, OutputStream outputStream);
}
