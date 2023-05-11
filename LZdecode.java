//Jake Li 1320187
import java.io.*;
import java.util.*;

public class LZdecode {
    public static void main(String[] args) throws IOException {
        // Check if the correct number of arguments were provided
        if (args.length != 2) {
            System.out.println("Usage: java LZdecode <input_file> <output_file>");
            return;
        }

        try {
            PrintWriter writer = new PrintWriter(new FileWriter(args[1]));
            // Open the file specified in the command line argument
            BufferedReader br = new BufferedReader(new FileReader(args[0]));
            Scanner sc = new Scanner(br);
            // Create a new object to store the dictionary
            HashMap<Integer, String> dict = new HashMap<Integer, String>();
            // Add the empty string to the dictionary
            dict.put(0, "");
            // Create a StringBuilder to store the decoded message
            StringBuilder result = new StringBuilder();
            // Initialize a counter to keep track of the next available dictionary index
            int counter = 1;
            // Loop through each line of the input file
            while (true) {
                // Read the line into a string
                String line = sc.nextLine();
                // If the line is empty
                if (line.equals(" "))
                    break;
                // Split the line into two parts
                String[] parts = line.split(" ");

                if (parts.length >= 2) {
                    // Convert the second part to a hexSymbol string
                    String hexSymbol = new String(Character.toChars(Integer.parseInt(parts[1])));
                    // Combine the first part and the hex string into a single string
                    String s1 = "<" + parts[0] + "," + hexSymbol + ">";

                    // Initialize variables to store the index and next character
                    int idx = -1;
                    String next = "";
                    // Loop through each character in the line
                    for (int i = 1; i < s1.length();) {
                        StringBuilder x = new StringBuilder();
                        while (s1.charAt(i) != ',' && s1.charAt(i) != '>') {
                            x.append(s1.charAt(i));
                            i++;
                        }
                        i++;
                        if (idx == -1) {
                            idx = Integer.parseInt(x.toString());
                        } else {
                            next = x.toString();
                        }
                    }

                    // Get the string associated with the current index from the dictionary
                    String temp = dict.get(idx);

                    // If next isn't "NULL", append it to temp
                    if (!next.equals("NULL"))
                        temp += next;
                    // Add the new string to the dictionary
                    dict.put(counter, temp);
                    // Increment the counter
                    counter++;
                    // Append the new string to the result StringBuilder
                    result.append(temp);
                }
            }
            // Print the decoded message
            //System.out.println(result);
            writer.println(result);
            writer.flush();
            writer.close();
        }
        // Catch a FileNotFoundException if the specified file doesn't exist
        catch (FileNotFoundException e) {
        }
    }
}