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
        Nodes constructred below produces the following huffman tree:
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
        tree = new HuffmanTree(queue);
    }

    @Test
    public void inOrderTraversal() {
        List<HuffmanNode> traversal = tree.traverseInOrder();
        for (HuffmanNode node : traversal) {
            System.out.println("Byte: " + node.getDataByte()
                    + "\tValue: " + node.getValue()
                    + "\tLevel: " + node.getLevel());
        }
    }


    @Test
    public void encoding() {
        Map<Byte, String> encodings = tree.getEncodings();
        for(Map.Entry<Byte, String> entry : encodings.entrySet()) {
            System.out.println("Byte: " + entry.getKey() + "\tEncoding: " + entry.getValue());
        }
    }
}