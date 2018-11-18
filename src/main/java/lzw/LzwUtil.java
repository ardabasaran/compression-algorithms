package lzw;

import com.google.common.collect.Lists;

import java.util.List;

public class LzwUtil {
  public static String toBinaryString(byte b) {
    return String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
  }

  public static List<Byte> toByteList(String s) {
    List<Byte> res = Lists.newLinkedList();

    for (int i = 0; i < s.length()/8; i++) {
      String substring = s.substring(i*8, i*8+8);
      int val = Integer.parseInt(substring, 2);
      res.add((byte) val);
    }

    return res;
  }
}
