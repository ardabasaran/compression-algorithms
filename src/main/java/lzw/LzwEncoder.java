package lzw;

import compressor.Encoder;

import java.io.*;

public class LzwEncoder implements Encoder {
  @Override
  public void encode(InputStream inputStream, OutputStream outputStream) {
    LzwDictionary dict = LzwDictionary.newInstance();
    DataOutputStream os = new DataOutputStream(outputStream);

    try {
      StringBuilder current = new StringBuilder();
      current.append(LzwUtil.toBinaryString((byte) inputStream.read()));
      int data = inputStream.read();
      while (data != -1) {
        byte dataByte = (byte) data;
        String next = LzwUtil.toBinaryString(dataByte);

        if (dict.isInTable(current.toString() + next)) {
          current.append(next);
        } else {
          int toWrite = dict.getFromTable(current.toString());
          os.writeInt(toWrite);
          current.append(next);
          dict.addToTable(current.toString());
          current = (new StringBuilder()).append(next);
        }
        data = inputStream.read();
      }

      int toWrite = dict.getFromTable(current.toString());
      os.writeInt(toWrite);

      os.close();
      outputStream.close();
      inputStream.close();
    } catch (IOException e) {
      System.err.println(e.getMessage());
    }
  }
}
