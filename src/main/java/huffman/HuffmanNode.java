package huffman;

public class HuffmanNode implements Comparable<HuffmanNode> {
    private Byte dataByte;
    private Integer value;
    private int level;
    private HuffmanNode leftChild;
    private HuffmanNode rightChild;

    public HuffmanNode(Byte dataByte, Integer value) {
        this.dataByte = dataByte;
        this.value = value;
        this.rightChild = null;
        this.leftChild = null;
    }

    public HuffmanNode(HuffmanNode smaller, HuffmanNode larger) {
        this.value = smaller.getValue() + larger.getValue();
        this.dataByte = 0;
        this.setLeftChild(smaller);
        this.setRightChild(larger);
    }

    public boolean isLeaf() {
        return leftChild == null && rightChild == null;
    }

    public Integer getValue() {
        return value;
    }

    public Byte getDataByte() {
        return dataByte;
    }

    public HuffmanNode getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(HuffmanNode leftChild) {
        this.leftChild = leftChild;
    }

    public HuffmanNode getRightChild() {
        return rightChild;
    }

    public void setRightChild(HuffmanNode rightChild) {
        this.rightChild = rightChild;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public int compareTo(HuffmanNode o) {
        if (this.value.equals(o.value)) {
            return this.dataByte - o.dataByte;
        }
        return this.value - o.value;
    }
}
