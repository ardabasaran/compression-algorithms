package compressor;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Interface for lossless compression.
 */
public interface Compressor {
  /**
   * Compresses/encodes the file specified by the input path and outputs to the file specified by the output path.
   *
   * @param input  The input stream where the data will be gathered from.
   * @param output The output stream that the compressed/encoded data will be written to.
   */
  void encode(InputStream input, OutputStream output);

  /**
   * Decompresses/decodes the file specified by the input path and outputs to the file specified by the output path.
   *
   * @param input  The input stream where the data will be gathered from.
   * @param output The output stream that the decompressed/decoded data will be written to.
   */
  void decode(InputStream input, OutputStream output);
}
