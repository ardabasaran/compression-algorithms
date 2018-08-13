package huffman;

import compressorAPI.Compressor;

import java.io.*;

/**
 * Compressor using the Huffman Coding algorithm.
 */
public class HuffmanCompressor implements Compressor {
    @Override
    public void encode(String input, String output) {
        try {
            InputStream frequencyStream = new FileInputStream(input);
            HuffmanEncoder encoder = new HuffmanEncoder(frequencyStream);

            OutputStream outputStream = new FileOutputStream(output);
            InputStream encodeStream = new FileInputStream(input);
            encoder.encode(encodeStream, outputStream);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(0);
        }
    }

    @Override
    public void decode(String input, String output) {
        try {
            InputStream headerStream = new FileInputStream(input);
            HuffmanDecoder decoder = new HuffmanDecoder(headerStream);

            InputStream decodeStream = new FileInputStream(input);
            OutputStream outputStream = new FileOutputStream(output);
            decoder.decode(decodeStream, outputStream);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(0);
        }
    }
}
