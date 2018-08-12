package compressor_api;

public interface Compressor {
    Encoder getEncoder();

    Decoder getDecoder();
}
