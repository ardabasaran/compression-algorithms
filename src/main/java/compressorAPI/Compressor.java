package compressorAPI;

import java.io.InputStream;
import java.io.OutputStream;

public interface Compressor {
    void encode(InputStream input, OutputStream output);

    void decode(InputStream input, OutputStream output);
}
