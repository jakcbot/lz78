import java.io.*;
import java.util.*;

public class LZunpack {

    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.err.println("Usage: java LZunpack <input-file> <output-file>");
            System.exit(1);
        }

        String inputFile = args[0];
        String outputFile = args[1];

        // Read the input file into a byte array
        byte[] compressed = readInput(inputFile);

        // Decompress the byte array into a list of integers
        List<Integer> decompressed = decompress(compressed);

        // Write the decompressed integers to the output file
        writeOutput(outputFile, decompressed);
    }

    private static byte[] readInput(String inputFile) throws IOException {
        byte[] input;
        try (FileInputStream inputStream = new FileInputStream(inputFile)) {
            input = new byte[inputStream.available()];
            inputStream.read(input);
        }
        return input;
    }

    private static void writeOutput(String outputFile, List<Integer> decompressed) throws IOException {
        try (FileOutputStream outputStream = new FileOutputStream(outputFile)) {
            for (int c : decompressed) {
                outputStream.write(c);
            }
        }
    }

    private static List<Integer> decompress(byte[] input) {
        // Initialize the dictionary with all possible byte values
        Map<Integer, String> dictionary = new HashMap<>();
        int dictSize = 256;
        for (int i = 0; i < 256; i++) {
            dictionary.put(i, Character.toString((char) i));
        }

        // Unpack the input byte array into a list of phrase numbers
        BitUnpacker bitUnpacker = new BitUnpacker(input, 8);
        List<Integer> phraseNumbers = new ArrayList<>();
        while (bitUnpacker.hasNext()) {
            phraseNumbers.add(bitUnpacker.unpack());
        }

        // Decompress the phrase numbers into a list of ASCII values
        List<Integer> decompressed = new ArrayList<>();
        String currentPhrase = "";
        for (int phraseNumber : phraseNumbers) {
            String phrase;
            if (dictionary.containsKey(phraseNumber)) {
                phrase = dictionary.get(phraseNumber);
            } else if (phraseNumber == dictSize) {
                phrase = currentPhrase + currentPhrase.charAt(0);
            } else {
                throw new IllegalStateException("Invalid phrase number: " + phraseNumber);
            }
            decompressed.addAll(stringToAsciiValues(phrase));
            if (!currentPhrase.equals("")) {
                dictionary.put(dictSize++, currentPhrase + phrase.charAt(0));
            }
            currentPhrase = phrase;
        }

        return decompressed;
    }

    private static List<Integer> stringToAsciiValues(String s) {
        List<Integer> asciiValues = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            asciiValues.add((int) s.charAt(i));
        }
        return asciiValues;
    }
}