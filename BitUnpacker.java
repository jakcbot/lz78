
public class BitUnpacker {

    private byte[] bytes;
    private int bitsPerValue;
    private int currentValue;
    private int numBitsFilled;

    /**
     * Constructs a BitUnpacker object with the given byte array and number of bits per value.
     *
     * @param bytes        the byte array to unpack bits from
     * @param bitsPerValue the number of bits per value to unpack
     */
    public BitUnpacker(byte[] bytes, int bitsPerValue) {
        this.bytes = bytes;
        this.bitsPerValue = bitsPerValue;
    }

    /**
     * Returns true if there are more bits to unpack, false otherwise.
     *
     * @return true if there are more bits to unpack, false otherwise
     */
    public boolean hasNext() {
        return numBitsFilled < bytes.length * 8;
    }

    /**
     * Unpacks the next value from the byte array.
     *
     * @return the next value from the byte array
     * @throws IllegalStateException if there are no more bits to unpack
     */
    public int unpack() {
        if (!hasNext()) {
            throw new IllegalStateException("No more bits to unpack");
        }
        while (numBitsFilled < bitsPerValue) {
            currentValue |= (bytes[numBitsFilled / 8] & 0xff) << numBitsFilled;
            numBitsFilled += 8;
        }
        int result = currentValue & ((1 << bitsPerValue) - 1);
        currentValue >>= bitsPerValue;
        numBitsFilled -= bitsPerValue;
        return result;
    }
}