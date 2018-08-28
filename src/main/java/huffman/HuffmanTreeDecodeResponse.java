package huffman;

public class HuffmanTreeDecodeResponse {
    public Byte decoded;
    public String remaining;

    public HuffmanTreeDecodeResponse(String remaining) {
        this.decoded = null;
        this.remaining = remaining;
    }

    public HuffmanTreeDecodeResponse(Byte decoded, String remaining) {
        this.decoded = decoded;
        this.remaining = remaining;
    }
}
