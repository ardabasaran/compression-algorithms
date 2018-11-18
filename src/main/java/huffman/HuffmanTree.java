package huffman;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.*;

public class HuffmanTree {
  private HuffmanNode root;
  private Map<Byte, String> encodings;
  private List<HuffmanNode> traversal;

  public HuffmanTree(PriorityQueue<HuffmanNode> queue) {
    // until one node remains in the queue
    // get two smallest one and make an interior node out of them
    while (queue.size() > 1) {
      HuffmanNode smaller = queue.remove();
      HuffmanNode larger = queue.remove();
      HuffmanNode interior = new HuffmanNode(smaller, larger);
      queue.add(interior);
    }

    // root is the remaining node
    root = queue.remove();

    // traverse the tree in order
    traversal = traverseInOrder();

    // get encodings from traversal
    this.encodings = getCanonicalEncoding(traversal);
  }

  public HuffmanTree(Map<Byte, String> encodings) {
    this.encodings = encodings;
    this.root = new HuffmanNode();
    List<ByteWithEncoding> list = Lists.newArrayList();
    for (Map.Entry<Byte, String> entry : encodings.entrySet()) {
      list.add(new ByteWithEncoding(entry.getKey(), entry.getValue()));
    }

    // build the tree from list of bytes and encodings
    root = recursiveBuild(list);

    // traverse the tree in order
    traversal = traverseInOrder();
  }

  private HuffmanNode recursiveBuild(List<ByteWithEncoding> list) {
    HuffmanNode node;

    if (list.size() == 0) {
      return null;
    }

    // if only one item remains in the list, create a node from it and return
    if (list.size() == 1 && list.get(0).getEncoding().length() == 1) {
      node = new HuffmanNode(list.get(0).getDataByte(), 0);
      return node;
    }

    List<ByteWithEncoding> leftList = Lists.newArrayList();
    List<ByteWithEncoding> rightList = Lists.newArrayList();


    if (list.size() == 2 &&
        list.get(0).getEncoding().length() == 1 &&
        list.get(1).getEncoding().length() == 1) {
      // if two node remains one goes to left the other goes to right
      ByteWithEncoding first = list.get(0);
      ByteWithEncoding second = list.get(1);
      if (first.getEncoding().charAt(0) == '0') {
        leftList.add(first);
        rightList.add(second);
      } else {
        leftList.add(second);
        rightList.add(first);
      }
    } else {
      // nodes start with '0' goes to left, others go to right
      for (ByteWithEncoding byteWithEncoding : list) {
        if (byteWithEncoding.getEncoding().charAt(0) == '0') {
          if (byteWithEncoding.getEncoding().length() > 1) {
            byteWithEncoding.setEncoding(byteWithEncoding.getEncoding().substring(1));
          }
          leftList.add(byteWithEncoding);
        } else {
          byteWithEncoding.setEncoding(byteWithEncoding.getEncoding().substring(1));
          rightList.add(byteWithEncoding);
        }
      }
    }

    // recursively build left and right trees
    HuffmanNode leftNode = recursiveBuild(leftList);
    HuffmanNode rightNode = recursiveBuild(rightList);

    // create an interior node from left node and right node
    node = new HuffmanNode(rightNode, leftNode);
    return node;
  }

  private class LevelComparator implements Comparator<HuffmanNode> {
    @Override
    public int compare(HuffmanNode o1, HuffmanNode o2) {
      if (o1.getLevel() != o2.getLevel()) {
        return o1.getLevel() - o2.getLevel();
      } else {
        return o2.getDataByte() - o1.getDataByte();
      }
    }
  }

  public Map<Byte, String> getEncodings() {
    return encodings;
  }

  public String encode(Byte dataByte) {
    return encodings.get(dataByte);
  }

  public HuffmanTreeDecodeResponse decode(String toDecode) {
    HuffmanNode node = root;
    int level = 0;

    // traverse the tree while the next character exist
    while (toDecode.length() > level) {
      if (toDecode.charAt(level) == '0') {
        node = node.getLeftChild();
      } else {
        node = node.getRightChild();
      }
      if (node.isLeaf()) {
        return ImmutableHuffmanTreeDecodeResponse.builder()
            .decoded(node.getDataByte())
            .remaining(toDecode.substring(level+1))
            .build();
      }
      level += 1;
    }
    return ImmutableHuffmanTreeDecodeResponse.builder()
        .decoded(node.getDataByte())
        .remaining(toDecode)
        .build();
  }

  public List<HuffmanNode> traverseInOrder() {
    List<HuffmanNode> traversal = Lists.newArrayList();
    recursiveInOrder(root, traversal, 0);
    traversal.sort(new LevelComparator());
    return traversal;
  }

  private void recursiveInOrder(HuffmanNode node, List<HuffmanNode> traversal, int level) {
    node.setLevel(level);
    if (node.isLeaf()) {
      traversal.add(node);
    } else {
      recursiveInOrder(node.getLeftChild(), traversal, level + 1);
      recursiveInOrder(node.getRightChild(), traversal, level + 1);
    }
  }

  public Map<Byte, String> getCanonicalEncoding(List<HuffmanNode> nodes) {
    Map<Byte, String> map = Maps.newHashMapWithExpectedSize(nodes.size());
    String encoding = "0";
    for (HuffmanNode node : nodes) {
      while (node.getLevel() > encoding.length()) {
        encoding += "0";
      }
      map.put(node.getDataByte(), encoding);
      encoding = addOneToEncoding(encoding);
    }
    return map;
  }

  public List<HuffmanNode> getTraversal() {
    return traversal;
  }

  private String addOneToEncoding(String encoding) {
    StringBuilder builder = new StringBuilder();
    int lastIndex = encoding.length() - 1;
    while (lastIndex > 0 && encoding.charAt(lastIndex) == '1') {
      builder.append('0');
      lastIndex--;
    }
    builder.append('1');
    lastIndex--;
    while (lastIndex >= 0) {
      builder.append(encoding.charAt(lastIndex));
      lastIndex--;
    }
    return builder.reverse().toString();
  }

  private class ByteWithEncoding {
    private Byte dataByte;
    private String encoding;

    public ByteWithEncoding(Byte dataByte, String encoding) {
      this.dataByte = dataByte;
      this.encoding = encoding;
    }

    public Byte getDataByte() {
      return dataByte;
    }

    public String getEncoding() {
      return encoding;
    }

    public void setDataByte(Byte dataByte) {
      this.dataByte = dataByte;
    }

    public void setEncoding(String encoding) {
      this.encoding = encoding;
    }
  }
}
