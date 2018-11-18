import compressor.Compressor;
import compressor.CompressorFactory;

import java.io.*;

public class CompressorApp {
  public static void main(String[] args) {
    if (args.length != 4) {
      System.err.println("Usage: java CompressorApp [algorithm] [operation] [input filename] [output filename]");
      System.err.println("algorithm = arithmetic / huffman / lzw");
      System.err.println("operation = compress / decompress");
      System.exit(0);
    }

    try {
      InputStream hs = new BufferedInputStream(new FileInputStream(args[2]));
      InputStream is = new BufferedInputStream(new FileInputStream(args[2]));
      OutputStream os = new BufferedOutputStream(new FileOutputStream(args[3]));
      String algorithm = args[0];
      String operation = args[1];
      Compressor compressor;
      if (algorithm.equals("arithmetic")) {
        compressor = CompressorFactory.createArithmetic(hs);
      } else if (algorithm.equals("huffman")) {
        compressor = CompressorFactory.createHuffman(hs);
      } else {
        compressor = CompressorFactory.createLZW();
      }

      if (operation.equals("compress")) {
        compressor.encode(is, os);
      } else {
        compressor.decode(is, os);
      }
    } catch (IOException e) {
      System.err.println(e.getMessage());
    }
  }
}
