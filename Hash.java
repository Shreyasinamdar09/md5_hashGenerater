import org.json.JSONObject;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hash {

    public static void main(String[] args) {
        try {
            //Load the input JSON file
            StringBuilder jsonContent = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new FileReader("input.json"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    jsonContent.append(line);
                }
            }

            //Parse the JSON content
            JSONObject json = new JSONObject(jsonContent.toString());

            // Extract the details 
            JSONObject studentInfo = json.getJSONObject("student");
            String firstName = studentInfo.getString("first_name");
            String rollNumber = studentInfo.getString("roll_number");


            // Combine the values into a single string
            String combinedString = firstName + rollNumber;

            // Generate the MD5 hash of the concatenated string
            String hashValue = generateMD5(combinedString);

            saveHashToFile(hashValue);

            System.out.println("Generated MD5 Hash: " + hashValue);

        } catch (IOException | NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
    }

    // Method to generate MD5 hash for a given string
    private static String generateMD5(String input) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("MD5");
        byte[] bytes = digest.digest(input.getBytes());

        // Convert the byte array to a hexadecimal string
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }
    private static void saveHashToFile(String hashValue) {
        try (FileWriter writer = new FileWriter("output.txt")) {
            writer.write(hashValue);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
