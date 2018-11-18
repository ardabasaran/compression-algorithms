package arithmetic;

import compressor.CompressorUtil;
import compressor.Encoder;
import compressor.FrequencyResponse;

import java.io.*;
import java.util.Map;

public class ArithmeticEncoder implements Encoder {
  int fileLength;
  int pendingBits;
  StringBuilder encodedByte;
  Map<Byte, Integer> frequencyMap;

  public ArithmeticEncoder(InputStream inputStream) {
    pendingBits = 0;
    encodedByte = new StringBuilder();
    FrequencyResponse freqResponse = CompressorUtil.getFrequencyMap(inputStream);
    frequencyMap = freqResponse.getFrequencyMap();
    fileLength = freqResponse.getNumberOfBytesRead();

    try {
      inputStream.close();
    } catch (IOException e) {
      System.err.println(e.getMessage());
    }
  }

  @Override
  public void encode(InputStream inputStream, OutputStream outputStream) {
    DataOutputStream os = new DataOutputStream(outputStream);
    ArithmeticInteger high = ArithmeticConstants.MAX_CODE;
    ArithmeticInteger low = ArithmeticInteger.fromInt(0);

    ProbabilityModel model = new ProbabilityModel();

    try {
      os.writeInt(fileLength);
      int data = inputStream.read();
      while (data != -1) {
        byte dataByte = (byte) data;
        //ArithmeticInteger range = high.minus(low).plus(ArithmeticInteger.fromInt(1));
        ArithmeticInteger range = high.minus(low).plus(ArithmeticInteger.fromInt(1));
        ArithmeticProbability probability = model.getProbability(dataByte);

        // high = low + (range * p.upper)/p.denominator;
        high = low.plus(
            (range.times(probability.getUpper()))
                .dividedBy(probability.getDenominator())
        ).minus(ArithmeticInteger.fromInt(1));

        // low = low + (range * p.lower)/p.denominator;
        low = low.plus(
            (range.times(probability.getLower()))
                .dividedBy(probability.getDenominator())
        );

        while(true) {
          if (high.compareTo(ArithmeticConstants.ONE_HALF) < 0) {
            outputBitAndPending(0, os);
          } else if (low.compareTo(ArithmeticConstants.ONE_HALF) >= 0) {
            outputBitAndPending(1, os);
          } else if (low.compareTo(ArithmeticConstants.ONE_FOURTH) >= 0
              && high.compareTo(ArithmeticConstants.THREE_FOURTHS) < 0) {
            pendingBits += 1;
            low = low.minus(ArithmeticConstants.ONE_FOURTH);
            high = high.minus(ArithmeticConstants.ONE_FOURTH);
          } else {
            break;
          }
          low = low.shiftLeft(1);
          high = high.shiftLeft(1);
          high = high.plus(ArithmeticInteger.fromInt(1));

          high = high.and(ArithmeticConstants.MAX_CODE);
          low = low.and(ArithmeticConstants.MAX_CODE);
        }

        data = inputStream.read();
      }
      pendingBits += 1;
      if ( low.compareTo(ArithmeticConstants.ONE_FOURTH) < 0) {
        outputBitAndPending(0, os);
      }
      else {
        outputBitAndPending(1, os);
      }

      printRemaining(os);

      os.close();
      inputStream.close();
      outputStream.close();
    } catch (IOException e) {
      System.err.println(e.getMessage());
    }
  }

  private void outputBitAndPending(int bit, DataOutputStream os) {
    encodedByte.append(bit);
    while (pendingBits > 0) {
      encodedByte.append(1 - bit);
      pendingBits -= 1;
    }
    try {
      // encode and write the remaining data
      while (encodedByte.length() >= 8) {
        // we parse int then cast to byte because byte is signed (so 11111111 can cause error)
        byte toWrite = (byte) Integer.parseInt(encodedByte.toString().substring(0, 8), 2);

        // write to output stream
        os.write(toWrite);

        // get the remaining part of the stream
        encodedByte = new StringBuilder().append(encodedByte.toString().substring(8));
      }
    } catch (IOException e) {
      System.err.println(e.getMessage());
    }
  }

  private void printRemaining(DataOutputStream os) {
    try {
      while (encodedByte.length() < 8) {
        encodedByte.append(0);
      }
      // encode and write the remaining data
      while (encodedByte.length() >= 8) {
        // we parse int then cast to byte because byte is signed (so 11111111 can cause error)
        byte toWrite = (byte) Integer.parseInt(encodedByte.toString().substring(0, 8), 2);

        // write to output stream
        os.write(toWrite);

        // get the remaining part of the stream
        encodedByte = new StringBuilder().append(encodedByte.toString().substring(8));
      }
    } catch (IOException e) {
      System.err.println(e.getMessage());
    }
  }
}


