package huffman;

import com.google.common.collect.Maps;
import compressor.Decoder;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

public class HuffmanDecoder implements Decoder {
  private Map<Byte, String> encoding;
  private HuffmanTree tree;
  private int fileLength;

  public HuffmanDecoder(InputStream inputStream) {
    DataInputStream is = new DataInputStream(inputStream);
    encoding = Maps.newHashMap();
    decodeHeader(is);
    tree = new HuffmanTree(encoding);
  }

  @Override
  public void decode(InputStream inputStream, OutputStream outputStream) {
    try {
      DataInputStream is = new DataInputStream(inputStream);
      decodeHeader(is);
      int decodedData = 0;
      int data = is.read();
      StringBuilder toDecode = new StringBuilder();
      while (data != -1) {
        // get the byte in string
        String dataByteBinary = String.format("%8s", Integer.toBinaryString((byte) data & 0xFF)).replace(' ', '0');
        toDecode.append(dataByteBinary);

        //decode first char - may or may not exist
        HuffmanTreeDecodeResponse response = tree.decode(toDecode.toString());
        toDecode = new StringBuilder().append(response.getRemaining());

        // keep decoding until you can't decode with the string we have
        while (response.getDecoded() > -1) {
          outputStream.write(response.getDecoded());
          decodedData += 1;

          // decode the string
          response = tree.decode(toDecode.toString());
          toDecode = new StringBuilder().append(response.getRemaining());

          if (decodedData == fileLength) {
            break;
          }
        }

        // break if we decoded the whole file
        if (decodedData == fileLength) {
          break;
        }

        // read next byte
        data = is.read();
      }

      is.close();
      inputStream.close();
      outputStream.close();
    } catch (IOException e) {
      System.err.println(e.getMessage());
    }
  }

  private void decodeHeader(DataInputStream is) {
    try {
      fileLength = is.readInt();
      int headerLength = is.readInt();
      for (int i = 0; i < headerLength; i++) {
        Byte byteData = is.readByte();
        String encoded = is.readUTF();
        encoding.put(byteData, encoded);
      }
      is.close();
    } catch (IOException e) {
      System.err.println(e.getMessage());
    }
  }
}
