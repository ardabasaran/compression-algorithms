package arithmetic;

import compressor.Decoder;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ArithmeticDecoder implements Decoder {
  int pendingBits = 0;
  byte data = 0;

  @Override
  public void decode(InputStream inputStream, OutputStream outputStream) {
    DataInputStream is = new DataInputStream(inputStream);

    ArithmeticInteger high = ArithmeticConstants.MAX_CODE;
    ArithmeticInteger low = ArithmeticInteger.fromInt(0);
    ArithmeticInteger value = ArithmeticInteger.fromInt(0);
    ProbabilityModel model = new ProbabilityModel();

    try {
      int fileLength = is.readInt();
      int decodedData = 0;
      for (int i = 0; i < ArithmeticConstants.CODE_VALUE_BITS; i++) {
        value = value.shiftLeft(1);
        value = value.plus(ArithmeticInteger.fromInt(getNextBit(is)));
      }

      while (true) {
        ArithmeticInteger range = high.minus(low).plus(ArithmeticInteger.fromInt(1));
        //count =  ((value - low + 1) * m_model.getCount() - 1) / range;
        ArithmeticInteger count =  value.minus(low)
            .plus(ArithmeticInteger.fromInt(1))
            .times(model.getCount())
            .minus(ArithmeticInteger.fromInt(1))
            .dividedBy(range);

        ByteWithProbability byteWithProbability = model.getByteProbability(count);
        ArithmeticProbability probability = byteWithProbability.getProbability();
        byte toWrite = byteWithProbability.getByteValue();
        outputStream.write(toWrite);
        decodedData += 1;
        if (decodedData == fileLength) {
          break;
        }
        //    high = low + (range*p.high)/p.count - 1;
        high = low.plus(
            (range.times(probability.getUpper()))
                .dividedBy(probability.getDenominator())
        ).minus(ArithmeticInteger.fromInt(1));

        //    low = low + (range*p.low)/p.count;
        low = low.plus(
            (range.times(probability.getLower()))
                .dividedBy(probability.getDenominator())
        );

        while(true) {
          if (high.compareTo(ArithmeticConstants.ONE_HALF) < 0) {
            // do nothing
          }
          else if (low.compareTo(ArithmeticConstants.ONE_HALF) >= 0) {
            value = value.minus(ArithmeticConstants.ONE_HALF);
            high = high.minus(ArithmeticConstants.ONE_HALF);
            low = low.minus(ArithmeticConstants.ONE_HALF);
          } else if (low.compareTo(ArithmeticConstants.ONE_FOURTH) >= 0
              && high.compareTo(ArithmeticConstants.THREE_FOURTHS) < 0) {
            value = value.minus(ArithmeticConstants.ONE_FOURTH);
            high = high.minus(ArithmeticConstants.ONE_FOURTH);
            low = low.minus(ArithmeticConstants.ONE_FOURTH);
          } else {
            break;
          }
          low = low.shiftLeft(1);
          high = high.shiftLeft(1);
          high = high.plus(ArithmeticInteger.fromInt(1));
          value = value.shiftLeft(1);
          value = value.plus(ArithmeticInteger.fromInt(getNextBit(is)));
        }
      }
      is.close();
      inputStream.close();
      outputStream.close();
    } catch (IOException e) {
      System.err.println(e.getMessage());
    }
  }

  private int getNextBit(DataInputStream is) {
    if (pendingBits == 0) {
      try {
        data = (byte) is.read();
        pendingBits += 8;
      } catch (IOException e) {
        System.err.println(e.getMessage());
      }
    }
    pendingBits--;
    return (data >> pendingBits) & 1;
  }
}