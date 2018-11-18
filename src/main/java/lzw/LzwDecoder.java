package lzw;

import compressor.Decoder;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class LzwDecoder implements Decoder {
  @Override
  public void decode(InputStream inputStream, OutputStream outputStream) {
    LzwDictionary dict = LzwDictionary.newInstance();
    DataInputStream is = new DataInputStream(inputStream);
    try {
      int prev = is.readInt();
      decodeAndPrint(dict, prev, outputStream);

      while (is.available() > 0) {
        int next = is.readInt();
        if (!dict.isCodeInTable(next)) {
          dict.addToTable(
              dict.getWithCodeFromTable(prev) +
                  dict.getWithCodeFromTable(prev).substring(0,8)
          );
        } else {
          dict.addToTable(
              dict.getWithCodeFromTable(prev) +
                  dict.getWithCodeFromTable(next).substring(0,8)
          );
        }
        decodeAndPrint(dict, next, outputStream);
        prev = next;;
      }
      is.close();
      inputStream.close();
      outputStream.close();

    } catch (IOException e) {
      System.err.println(e.getMessage());
    }
  }

  private void decodeAndPrint(LzwDictionary dict, int code, OutputStream os) {
    String str = dict.getWithCodeFromTable(code);
    List<Byte> byteList = LzwUtil.toByteList(str);
    try {
      for (Byte b : byteList) {
        os.write(b);
      }
    } catch (IOException e) {
      System.err.println(e.getMessage());
    }
  }
}
