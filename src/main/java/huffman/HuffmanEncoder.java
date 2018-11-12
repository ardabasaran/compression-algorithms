package huffman;

import com.google.common.collect.Maps;
import com.google.common.collect.Queues;
import compressorAPI.Encoder;

import java.io.DataOutputStream;
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
      DataOutputStream os = new DataOutputStream(outputStream);
      printHeader(tree.getEncodings(), fileLength, os);
      StringBuilder encodedByte = new StringBuilder();
      int data = inputStream.read();
      while (data != -1) {
        //
        encodedByte.append(tree.encode((byte) data));

        // if  we have more than 8 bits, we can write to output stream
        if (encodedByte.length() > 8) {
          // we parse int then cast to byte because byte is signed (so 11111111 can cause error)
          byte toWrite = (byte) Integer.parseInt(encodedByte.toString().substring(0, 8), 2);

          // write to output stream
          os.write(toWrite);

          // get the remaining part of the stream
          encodedByte = new StringBuilder().append(encodedByte.toString().substring(8));
        }
        data = inputStream.read();
      }

      // encode and write the remaining data
      while (encodedByte.length() > 8) {
        // we parse int then cast to byte because byte is signed (so 11111111 can cause error)
        byte toWrite = (byte) Integer.parseInt(encodedByte.toString().substring(0, 8), 2);

        // write to output stream
        os.write(toWrite);

        // get the remaining part of the stream
        encodedByte = new StringBuilder().append(encodedByte.toString().substring(8));
      }

      // we might have left over data
      StringBuilder lastByte = new StringBuilder().append(encodedByte.toString());
      if (lastByte.length() > 0) {
        // append to the last part zeros if needed
        while (lastByte.length() < 8) {
          lastByte.append("0");
        }
        //write the last byte
        byte toWrite = (byte) Integer.parseInt(lastByte.toString(), 2);
        os.write(toWrite);
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

  private void printHeader(Map<Byte, String> encodings, int fileLength, DataOutputStream os) {
    try {
      os.writeInt(fileLength);
      os.writeInt(encodings.size());
      for (Map.Entry<Byte, String> entry : encodings.entrySet()) {
        os.writeByte(entry.getKey());
        os.writeUTF(entry.getValue());
      }
    } catch (IOException e) {
      System.err.println(e.getMessage());
    }
  }
}
