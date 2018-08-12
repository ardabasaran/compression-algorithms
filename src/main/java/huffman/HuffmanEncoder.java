package huffman;

import com.google.common.collect.Maps;
import com.google.common.collect.Queues;
import compressorAPI.Encoder;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.PriorityQueue;

public class HuffmanEncoder implements Encoder {
    private HuffmanTree tree;
    public HuffmanEncoder(InputStream inputStream) {
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
            int data = inputStream.read();
            while (data != -1) {
                Byte dataByte = (byte) data;
                outputStream.write(tree.encode(dataByte));
                data = inputStream.read();
            }
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
}