import java.io.*;
import java.util.*;

public class LZpack {
    public static void main(String[] args) throws IOException {
        // Check that the user has provided the correct number of arguments
        if (args.length != 2) {
            System.err.println("Usage: java LZpack <input-file> <output-file>");
            System.exit(1);
        }

        // Get the input and output file names from the command line arguments
        String inputFile = args[0];
        String outputFile = args[1];

        // Read the input file into a list of integers
        List<Integer> input = readInput(inputFile);

        // Compress the input and write the compressed data to the output file
        byte[] compressed = compress(input);
        writeOutput(outputFile, compressed);
    }

    private static List<Integer> readInput(String inputFile) throws IOException {
        // Create a new list to hold the input data
        List<Integer> input = new ArrayList<>();

        // Open the input file for reading
        try (FileInputStream inputStream = new FileInputStream(inputFile)) {
            int c;
            // Read each byte from the input file and add it to the list
            while ((c = inputStream.read()) != -1) {
                input.add(c);
            }
        }

        // Return the list of input data
        return input;
    }

    private static void writeOutput(String outputFile, byte[] compressed) throws IOException {
        // Open the output file for writing
        try (FileOutputStream outputStream = new FileOutputStream(outputFile)) {
            // Write the compressed data to the output file
            outputStream.write(compressed);
        }
    }

    private static byte[] compress(List<Integer> input) {
        // Create a new trie to hold the dictionary
        Trie dictionary = new Trie();

        // Initialize the dictionary with all possible single-byte phrases
        int dictSize = 256;
        for (int i = 0; i < 256; i++) {
            dictionary.put(i, Character.toString((char) i));
        }

        // Create a new bit packer to hold the compressed data
        BitPacker bitPacker = new BitPacker(8);

        // Create a new list to hold the phrase numbers
        List<Integer> phraseNumbers = new ArrayList<>();

        // Initialize the current phrase to the empty string
        String currentPhrase = "";

        // Loop over each byte in the input data
        for (int c : input) {
            // Append the current byte to the current phrase
            String nextPhrase = currentPhrase + (char) c;

            // If the dictionary contains the next phrase, update the current phrase
            if (dictionary.contains(nextPhrase)) {
                currentPhrase = nextPhrase;
            } else {
                // Otherwise, add the current phrase to the phrase numbers list,
                // add the next phrase to the dictionary, and update the current phrase
                phraseNumbers.add(dictionary.get(currentPhrase));
                dictionary.put(dictSize++, nextPhrase);
                currentPhrase = Character.toString((char) c);
            }
        }

        // If there is a current phrase at the end of the input data, add it to the
        // phrase numbers list
        if (!currentPhrase.equals("")) {
            phraseNumbers.add(dictionary.get(currentPhrase));
        }

        // Pack each phrase number into the bit packer
        for (int phraseNumber : phraseNumbers) {
            bitPacker.pack(phraseNumber);
        }

        // Return the compressed data as a byte array
        return bitPacker.toByteArray();
    }
}