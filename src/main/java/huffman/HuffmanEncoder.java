package huffman;

import com.google.common.collect.Maps;
import com.google.common.collect.Queues;
import compressorAPI.Encoder;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class HuffmanEncoder implements Encoder {
    private int fileLength;
    private HuffmanTree tree;

    public HuffmanEncoder(InputStream inputStream) {
        fileLength = 0;
        Map<Byte, Integer> frequencyMap = getFrequencyMap(inputStream);
        try {
            inputStream.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        PriorityQueue<HuffmanNode> nodeHeap = buildNodeHeap(frequencyMap);
        tree = new HuffmanTree(nodeHeap);
    }

    @Override
    public void encode(InputStream inputStream, OutputStream outputStream) {
        try {
            printHeader(tree.getTraversal(), fileLength, outputStream);
            StringBuilder encodedByte = new StringBuilder();
            int data = inputStream.read();
            while (data != -1) {
                Byte dataByte = (byte) data;
                encodedByte.append(tree.encode(dataByte));
                if (encodedByte.length() > 8) {
                    outputStream.write(encodedByte.toString().substring(0,8).getBytes(StandardCharsets.UTF_8)[0]);
                    encodedByte = new StringBuilder().append(encodedByte.toString().substring(8));
                }
                data = inputStream.read();
            }
            outputStream.write(encodedByte.toString().getBytes(StandardCharsets.UTF_8)[0]);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private PriorityQueue<HuffmanNode> buildNodeHeap(Map<Byte, Integer> frequencyMap) {
        PriorityQueue<HuffmanNode> queue = Queues.newPriorityQueue();
        for (Map.Entry<Byte, Integer> entry : frequencyMap.entrySet()) {
            HuffmanNode node = new HuffmanNode(entry.getKey(), entry.getValue());
            queue.add(node);
        }
        return queue;
    }

    private Map<Byte, Integer> getFrequencyMap(InputStream inputStream) {
        Map<Byte, Integer> frequencies = Maps.newHashMap();
        try {
            int data = inputStream.read();
            while (data != -1) {
                fileLength += 1;
                Byte dataByte = (byte) data;
                frequencies.putIfAbsent(dataByte, 0);
                frequencies.put(dataByte, frequencies.get(dataByte) + 1);
                data = inputStream.read();
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return frequencies;
    }

    public void printHeader(List<HuffmanNode> traversal, int fileLength, OutputStream output) {
        try {
            byte[] header = getHeader(traversal);
            int headerLength = header.length;
            output.write(headerLength);
            output.write(header);
            output.write(fileLength);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }


    private byte[] getHeader(List<HuffmanNode> traversal) {
        StringBuilder builder = new StringBuilder();
        for (HuffmanNode node : traversal) {
            builder.append(node.getDataByte() + ":" + node.getLevel() + ",");
        }
        return builder.toString().getBytes(StandardCharsets.UTF_8);
    }
}
