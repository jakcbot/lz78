//Jake Li 1320187
import java.io.*;
import java.nio.charset.StandardCharsets;

public class LZencode {

    public static void main(String[] args) throws IOException {
        // Check if the correct number of arguments were provided
        if (args.length != 2) {
            System.out.println("Usage: java LZencode <input_file> <output_file>");
            return;
        }

        // Read the input file as a byte array
        File file = new File(args[0]);
        FileInputStream fis = new FileInputStream(file);
        byte[] bytes = new byte[(int) file.length()];
        fis.read(bytes);
        fis.close();

        // Convert the byte array to a hexadecimal string
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            hexString.append(String.format("%02X", b));
        }

        // Convert the byte array to character UTF-8 string
        String s1 = new String(bytes, StandardCharsets.UTF_8);

        // Create an empty Trie data structure to store the dictionary
        Trie dict = new Trie();

        // Loop over the input string and compress it using the LZ algorithm
        BufferedWriter writer = new BufferedWriter(new FileWriter(args[1]));
        int counter = 1;
        for (int i = 0; i < s1.length(); i++) {
            String searchingFor = "";
            int index = 0;
            for (int j = i; j < s1.length(); j++) {
                // Build up a string called "searchingFor" by adding one character at a time
                searchingFor += s1.charAt(j);
                // Check if the Trie already contains this string
                if (dict.contains(searchingFor)) {
                    // If it does, update the "index" variable to the index of the string in the
                    // Trie
                    index = dict.get(searchingFor);
                    // If we've reached the end of the input string, write the index to the output
                    // file and exit the loop
                    if (j + 1 == s1.length()) {
                        writer.write(index + " " + "\n");
                        i = j + 1;
                    }
                } else {
                    // If it doesn't, add the string to the Trie with a new index and write the
                    // index of the previous string and the ASCII code of the current character to
                    // the output file
                    dict.put(counter++, searchingFor);
                    writer.write(index + " " + (int) s1.charAt(j) + "\n");
                    // Move the outer loop to the end of the current string
                    i = j;
                    break;
                }
            }
        }
        // Write a space character to the output file to indicate the end of the
        writer.write(" ");
        writer.close();
    }
}