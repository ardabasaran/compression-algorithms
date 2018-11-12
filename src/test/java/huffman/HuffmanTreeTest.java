package huffman;

import com.google.common.collect.Queues;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import static org.junit.Assert.*;

public class HuffmanTreeTest {
  HuffmanTree tree;

  @Before
  public void setup() {
        /*
        Nodes constructed below produces the following huffman tree:
                   *
                  / \
                 0   *
                    / \
                   *   3
                  / \
                 1   2
         */
    HuffmanNode aNode = new HuffmanNode((byte) 0, 25);
    HuffmanNode bNode = new HuffmanNode((byte) 1, 3);
    HuffmanNode cNode = new HuffmanNode((byte) 2, 7);
    HuffmanNode dNode = new HuffmanNode((byte) 3, 15);
    PriorityQueue<HuffmanNode> queue = Queues.newPriorityQueue();
    queue.add(aNode);
    queue.add(bNode);
    queue.add(cNode);
    queue.add(dNode);
    //testing build from priority queue
    tree = new HuffmanTree(queue);
  }

  @Test
  public void buildFromEncodings() {
    Map<Byte, String> encodings = tree.getEncodings();
    HuffmanTree copyTree = new HuffmanTree(encodings);
    Map<Byte, String> copyEncodings = copyTree.getEncodings();
    for (Map.Entry<Byte, String> entry : copyEncodings.entrySet()) {
      System.out.println("Byte: " + entry.getKey() + "\tEncoding: " + entry.getValue());
    }
  }

  @Test
  public void traverseInOrder() {
    List<HuffmanNode> traversal = tree.traverseInOrder();
    for (HuffmanNode node : traversal) {
      System.out.println("Byte: " + node.getDataByte()
          + "\t\tValue: " + node.getValue()
          + "\t\tLevel: " + node.getLevel());
    }
  }

  @Test
  public void encoding() {
    Map<Byte, String> encodings = tree.getEncodings();
    for (Map.Entry<Byte, String> entry : encodings.entrySet()) {
      System.out.println("Byte: " + entry.getKey() + "\tEncoding: " + entry.getValue());
    }
  }

  @Test
  public void decode() {
    HuffmanTreeDecodeResponse response;
    response = tree.decode("0");
    assertEquals("",response.getRemaining());
    assertEquals(Byte.valueOf((byte) 0), response.getDecoded());

    response = tree.decode("111");
    assertEquals("",response.getRemaining());
    assertEquals(Byte.valueOf((byte) 1), response.getDecoded());


    response = tree.decode("110");
    assertEquals("",response.getRemaining());
    assertEquals(Byte.valueOf((byte) 2), response.getDecoded());


    response = tree.decode("10");
    assertEquals("",response.getRemaining());
    assertEquals(Byte.valueOf((byte) 3), response.getDecoded());


    response = tree.decode("111110");
    assertEquals("110",response.getRemaining());
    assertEquals(Byte.valueOf((byte) 1), response.getDecoded());


    response = tree.decode("000");
    assertEquals("00",response.getRemaining());
    assertEquals(Byte.valueOf((byte) 0), response.getDecoded());

    response = tree.decode("11010");
    assertEquals("10",response.getRemaining());
    assertEquals(Byte.valueOf((byte) 2), response.getDecoded());

    response = tree.decode("1000111");
    assertEquals("00111",response.getRemaining());
    assertEquals(Byte.valueOf((byte) 3), response.getDecoded());

  }
}