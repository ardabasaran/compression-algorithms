package compressor;

import arithmetic.ArithmeticDecoder;
import arithmetic.ArithmeticEncoder;
import huffman.HuffmanDecoder;
import huffman.HuffmanEncoder;
import lzw.LzwDecoder;
import lzw.LzwEncoder;

import java.io.InputStream;

public class CompressorFactory {
  public static Compressor createArithmetic(InputStream inputStream) {
    ArithmeticEncoder encoder = new ArithmeticEncoder(inputStream);
    ArithmeticDecoder decoder = new ArithmeticDecoder();
    return DefaultCompressor.newInstance(encoder, decoder);
  }

  public static Compressor createHuffman(InputStream inputStream) {
    HuffmanEncoder encoder = new HuffmanEncoder(inputStream);
    HuffmanDecoder decoder = new HuffmanDecoder();
    return DefaultCompressor.newInstance(encoder, decoder);
  }

  public static Compressor createLZW() {
    LzwEncoder encoder = new LzwEncoder();
    LzwDecoder decoder = new LzwDecoder();
    return DefaultCompressor.newInstance(encoder, decoder);
  }
}
