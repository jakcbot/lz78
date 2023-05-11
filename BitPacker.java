import java.util.*;

public class BitPacker {
    private int bitsPerValue; // number of bits to use for each value
    private int currentValue; // current value being packed
    private int numBitsFilled; // number of bits filled in the current byte
    private List<Byte> bytes = new ArrayList<>(); // list of packed bytes

    public BitPacker(int bitsPerValue) {
        this.bitsPerValue = bitsPerValue;
    }

    // packs a value into the byte array
    public void pack(int value) {
        currentValue |= value << numBitsFilled; // add the value to the current byte
        numBitsFilled += bitsPerValue; // increment the number of bits filled
        while (numBitsFilled >= 8) { // if the current byte is full
            bytes.add((byte) (currentValue & 0xff)); // add the byte to the list
            currentValue >>= 8; // shift the current value right by 8 bits
            numBitsFilled -= 8; // decrement the number of bits filled
        }
    }

    // converts the packed bytes to a byte array
    public byte[] toByteArray() {
        if (numBitsFilled > 0) { // if there are bits left in the current byte
            bytes.add((byte) (currentValue & 0xff)); // add the byte to the list
        }
        byte[] result = new byte[bytes.size()]; // create a byte array with the same size as the list
        for (int i = 0; i < bytes.size(); i++) { // copy the bytes from the list to the array
            result[i] = bytes.get(i);
        }
        return result;
    }
}