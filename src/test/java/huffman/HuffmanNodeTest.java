package huffman;

import org.junit.Test;

import static org.junit.Assert.*;

public class HuffmanNodeTest {
    @Test
    public void isLeaf() {
        HuffmanNode leafNode = new HuffmanNode((byte) 0,0);
        HuffmanNode leafNode2 = new HuffmanNode((byte) 1, 1);
        HuffmanNode interiorNode = new HuffmanNode(leafNode, leafNode2);

        assertTrue(leafNode.isLeaf());
        assertTrue(leafNode2.isLeaf());
        assertFalse(interiorNode.isLeaf());
    }

    @Test
    public void compareTo() {
        HuffmanNode leafNodeSmall = new HuffmanNode((byte) 0,0);
        HuffmanNode leafNodeLarge = new HuffmanNode((byte) 0, 1);
        HuffmanNode leafNodeLarger = new HuffmanNode((byte) 1, 1);

        assertTrue(leafNodeSmall.compareTo(leafNodeLarge) < 0);
        assertEquals(0, leafNodeLarge.compareTo(leafNodeLarge));
        assertTrue(leafNodeLarge.compareTo(leafNodeLarger) < 0);

        HuffmanNode interiorNodeSmall = new HuffmanNode(leafNodeSmall, leafNodeLarge);
        HuffmanNode interiorNodeSmall2 = new HuffmanNode(leafNodeSmall, leafNodeLarger);
        HuffmanNode interiorNodeLarge = new HuffmanNode(leafNodeLarge, leafNodeLarger);

        assertTrue(interiorNodeSmall.compareTo(interiorNodeLarge) < 0);
        assertEquals(0, interiorNodeSmall.compareTo(interiorNodeSmall2));
    }
}