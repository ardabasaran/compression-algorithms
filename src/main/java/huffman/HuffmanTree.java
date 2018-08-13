package huffman;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class HuffmanTree {
    private HuffmanNode root;
    private Map<Byte, String> encodings;
    private List<HuffmanNode> traversal;

    public HuffmanTree(PriorityQueue<HuffmanNode> queue) {
        while (queue.size() > 1) {
            HuffmanNode smaller = queue.remove();
            HuffmanNode larger = queue.remove();
            HuffmanNode interior = new HuffmanNode(smaller, larger);
            queue.add(interior);
        }
        root = queue.remove();
        traversal = traverseInOrder();
        traversal.sort(new LevelComparator());
        this.encodings = getCanonicalEncoding(traversal);
    }

    private class LevelComparator implements Comparator<HuffmanNode> {
        @Override
        public int compare(HuffmanNode o1, HuffmanNode o2) {
            if (o1.getLevel() != o2.getLevel()) {
                return o1.getLevel() - o2.getLevel();
            } else {
                return o1.getDataByte() - o2.getDataByte();
            }
        }
    }

    public Map<Byte, String> getEncodings() {
        return encodings;
    }

    public String encode(Byte dataByte) {
        return encodings.get(dataByte);
    }

    public List<HuffmanNode> traverseInOrder() {
        List<HuffmanNode> traversal = Lists.newArrayList();
        recursiveInOrder(root, traversal, 0);
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

    public void printHeader(OutputStream output) {
        try {
            byte[] header = getHeader();
            int headerLength = header.length;
            output.write(headerLength);
            output.write(header);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private byte[] getHeader() {
        StringBuilder builder = new StringBuilder();
        for (HuffmanNode node : traversal) {
            builder.append(node.getDataByte() + ":" + node.getLevel() + ",");
        }
        return builder.toString().getBytes(StandardCharsets.UTF_8);
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
}
