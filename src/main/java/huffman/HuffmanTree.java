package huffman;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

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

    public HuffmanTree(Map<Byte, String> encodings) {
        this.encodings = encodings;
        this.root = new HuffmanNode();
        List<ByteWithEncoding> list = Lists.newArrayList();
        for (Map.Entry<Byte, String> entry : encodings.entrySet()) {
            list.add(new ByteWithEncoding(entry.getKey(), entry.getValue()));
        }
        root = recursiveBuild(list);
        traversal = traverseInOrder();
        traversal.sort(new LevelComparator());
    }

    private HuffmanNode recursiveBuild(List<ByteWithEncoding> list) {
        HuffmanNode node;
        if (list.size() == 1 && list.get(0).getEncoding().length() == 1) {
            node = new HuffmanNode(list.get(0).getDataByte(), 0);
            return node;
        } else {
            List<ByteWithEncoding> leftList = Lists.newArrayList();
            List<ByteWithEncoding> rightList = Lists.newArrayList();
            for (ByteWithEncoding byteWithEncoding : list) {
                if (byteWithEncoding.getEncoding().charAt(0) == '0') {
                    leftList.add(byteWithEncoding);
                } else {
                    rightList.add(byteWithEncoding);
                }
            }
            HuffmanNode leftNode = recursiveBuild(leftList);
            HuffmanNode rightNode = recursiveBuild(rightList);
            node = new HuffmanNode(leftNode, rightNode);
            return node;
        }
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

    public HuffmanTreeDecodeResponse decode(String toDecode) {
        HuffmanNode node = root;
        int level = 0;
        while (toDecode.length() > level) {
            if (toDecode.charAt(level) == '0') {
                node = root.getLeftChild();
            } else {
                node = root.getRightChild();
            }
            if (node.isLeaf()) {
                return new HuffmanTreeDecodeResponse(node.getDataByte(), toDecode.substring(level));
            }
        }
        return new HuffmanTreeDecodeResponse(toDecode);
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
        Byte dataByte;
        String encoding;

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
    }
}
