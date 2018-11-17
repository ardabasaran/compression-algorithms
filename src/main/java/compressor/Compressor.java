package compressor;

/**
 * Interface for lossless compression.
 */
public interface Compressor {
  /**
   * Compresses/encodes the file specified by the input path and outputs to the file specified by the output path.
   *
   * @param input  The input path where the data will be gathered from.
   * @param output The output path that the compressed/encoded data will be written to.
   */
  void encode(String input, String output);

  /**
   * Decompresses/decodes the file specified by the input path and outputs to the file specified by the output path.
   *
   * @param input  The input path where the data will be gathered from.
   * @param output The output path that the decompressed/decoded data will be written to.
   */
  void decode(String input, String output);
}
