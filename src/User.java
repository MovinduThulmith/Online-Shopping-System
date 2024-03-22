import java.io.*;
import java.util.*;

/**
 * Represents a user with a username and password.
 */
public class User {
    private String username;
    private String password;
    private static ArrayList<String> previousUsernames = new ArrayList<>();

    /**
     * Constructs a new User with the specified username and password.
     *
     * @param username The user's username.
     * @param password The user's password.
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Gets the username of the user.
     *
     * @return The username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Adds the current username and password to the "user_credentials.txt" file.
     */
    public void addToBuyingHistory() {
        try {
            // Initialize the PrintWriter to save and append the user credentials data
            PrintWriter writer = new PrintWriter(new FileOutputStream("user_credentials.txt", true));

            // Append username and password to the file
            writer.println(username + "-" + password);

            // Update the list of previous usernames
            previousUsernames.add(username);

            // Close the PrintWriter
            writer.close();
        } catch (IOException e) {
            System.out.println("Error..");
        }
    }

    /**
     * Retrieves and returns a list of previous usernames from the "purchase_history.txt" file.
     *
     * @return The list of previous usernames.
     */
    public static ArrayList<String> getPreviousUsernames() {
        try {
            // Initialize the File object to read from
            File textFile = new File("user_credentials.txt");
            Scanner input = new Scanner(textFile);

            // Read the text file data line by line using Scanner
            while (input.hasNextLine()) {
                String line = input.nextLine();

                // Split the line using "-" as a delimiter
                String[] userData = line.split("-");

                // Check if the split produced valid data
                if (userData.length == 2) {
                    String username = userData[0];
                    previousUsernames.add(username);
                }
            }
        } catch (IOException e) {
            System.out.println("Error...");
        }
        return previousUsernames;
    }
}
