package lzw;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

public class LzwDictionary {
  BiMap<String, Integer> map;
  int nextCode;

  private LzwDictionary() {
    nextCode = Integer.MIN_VALUE;
    map = HashBiMap.create();
    for(int i = 0; i < 256; i++) {

      map.put(LzwUtil.toBinaryString((byte) i), nextCode);
      nextCode += 1;
    }
  }

  public static LzwDictionary newInstance() {
    return new LzwDictionary();
  }

  public boolean isInTable(String s) {
    return map.containsKey(s);
  }

  public void addToTable(String s) {
    map.put(s, nextCode);
    nextCode += 1;
  }

  public int getFromTable(String s) {
    return map.get(s);
  }

  public String getWithCodeFromTable(int i) {
    return map.inverse().get(i);
  }

  public boolean isCodeInTable(int i) {
    return map.inverse().containsKey(i);
  }
}
