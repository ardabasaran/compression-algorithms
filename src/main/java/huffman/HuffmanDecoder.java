package huffman;

import com.google.common.collect.Maps;
import compressorAPI.Decoder;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class HuffmanDecoder implements Decoder {
    private Map<Byte, String> encoding;
    private HuffmanTree tree;
    private int fileLength;
    private int toSkip;

    public HuffmanDecoder(InputStream inputStream) {
        try {
            toSkip = 0;
            int headerLength = inputStream.read(new byte[4]);
            toSkip += 4;
            byte[] headerBytes = new byte[headerLength];
            inputStream.read(headerBytes);
            toSkip += headerLength;
            String header = new String(headerBytes, "UTF-8");
            encoding = decodeHeader(header);
            tree = new HuffmanTree(encoding);
            fileLength = inputStream.read(new byte[4]);
            toSkip += 4;
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void decode(InputStream inputStream, OutputStream outputStream) {
        try {
            inputStream.skip(toSkip);
            int data = inputStream.read();
            StringBuilder toDecode = new StringBuilder();
            for (int i = 0; i < fileLength; i++) {
                Byte dataByte = (byte) data;
                toDecode.append(dataByte.toString());
                HuffmanTreeDecodeResponse response = tree.decode(toDecode.toString());
                toDecode = new StringBuilder().append(response.remaining);
                if (response.decoded != null) {
                    outputStream.write(response.decoded);
                }
                data = inputStream.read();
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private Map<Byte, String> decodeHeader(String header) {
        Map<Byte, String> map = Maps.newHashMap();
        String[] lines = header.split(",");
        for (int i = 0; i < lines.length - 1; i++) {
            String[] parts = lines[i].split(":");
            Byte byteData = parts[0].getBytes(StandardCharsets.UTF_8)[0];
            String encoding = parts[1];
            map.put(byteData, encoding);
        }
        return map;
    }
}
