package huffman;

public class HuffmanNode implements Comparable<HuffmanNode> {
    private Byte dataByte;
    private Integer value;

    public HuffmanNode(Byte dataByte, Integer value) {
        this.dataByte = dataByte;
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    @Override
    public int compareTo(HuffmanNode o) {
        return this.value - o.value;
    }
}
