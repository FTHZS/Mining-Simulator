import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class FileHandler {
    public static final String PATH = "userbase.txt";
    public static int TotalAccounts;
    static final File Userbase = new File(PATH);
    static {
        Userbase.setReadable(true, false);
    }

    public static User getUser(String Username) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(Userbase));
            String line;
            boolean found = false;
            String password = "", joinDate = "", RarestOreDiscovered = "";
            int blocksMined = 0, eventsActivated = 0;
            boolean toggleDelay = true;
            ArrayList<Object[]> inventory = new ArrayList<Object[]>();
            int lineN = 0;
            int lineNo = 0;

            while ((line = reader.readLine()) != null) {
                lineN++;

                // Check if the username block is found
                if (line.startsWith("|| " + Username + " {")) {
                    found = true;
                    lineNo = lineN;
                    continue; // Skip the user block start line
                }

                // Parse user data after finding the block
                if (found) {
                    if (line.startsWith("Password = ")) {
                        password = line.split("\"")[1];
                    } else if (line.startsWith("JoinDate = ")) {
                        joinDate = line.split("\"")[1];
                    } else if (line.startsWith("BlocksMined = ")) {
                        blocksMined = Integer.parseInt(line.split("= ")[1]);
                    } else if (line.startsWith("EventsActivated = ")) {
                        eventsActivated = Integer.parseInt(line.split("= ")[1]);
                    } else if (line.startsWith("toggleDelay = ")) {
                        toggleDelay = Boolean.parseBoolean(line.split("= ")[1]);
                    } else if (line.startsWith("Rarest ore discovered = ")) {
                        RarestOreDiscovered = line.split("= ")[1];
                    } else if (line.startsWith("Inventory = ")) {
                        // Extract inventory data from the line
                        String inventoryData = line.substring(line.indexOf("[") + 1, line.indexOf("]"));
                        String[] inventoryItems = inventoryData.split("\\), \\(");

                        if (inventoryData.equals("") == false) {
                            // Parse each inventory item
                            for (String item : inventoryItems) {
                                String[] itemDetails = item.replace("(", "").replace(")", "").split(", ");
                                String mineralName = itemDetails[0];
                                int normalAmount = Integer.parseInt(itemDetails[1]);
                                int spectralAmount = Integer.parseInt(itemDetails[2]);
                                int ionizedAmount = Integer.parseInt(itemDetails[3]);
                                int transdimensionalAmount = Integer.parseInt(itemDetails[4]);

                                Object[] mineralData = new Object[] {
                                        mineralName, normalAmount, spectralAmount, ionizedAmount, transdimensionalAmount
                                };
                                inventory.add(mineralData);
                            }
                        }
                    } else if (line.startsWith("}")) {
                        // End of user block, return the user object
                        reader.close();
                        return new User(Username, password, joinDate, blocksMined, eventsActivated, inventory, lineNo,
                                toggleDelay, RarestOreDiscovered);
                    }
                }
            }
            reader.close();
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return null; // User not found
    }

    public static void writeUser(User user) {
        try {
            FileWriter writer = new FileWriter(Userbase, true);
            writer.write("\n" + user.toFileFormat(true));
            writer.close();
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    public static void saveUser(User user) {
        int lineNumber = user.getLineNo();

        try {
            // Read the entire file into a String array
            BufferedReader reader = new BufferedReader(new FileReader(Userbase));
            List<String> lineList = new ArrayList<String>();
            String line;
            while ((line = reader.readLine()) != null) {
                lineList.add(line);
            }
            String[] lines = lineList.toArray(new String[0]);
            reader.close();

            // Ensure the line number is valid
            if (lineNumber < 0 || lineNumber >= lines.length) {
                System.err.println("Invalid line number: " + lineNumber);
                return;
            }

            // Find the start and end of the user's block
            int startLine = -1;
            for (int i = lineNumber - 1; i >= 0; i--) {
                if (lines[i].startsWith("|| " + user.getUsername() + " {")) {
                    startLine = i;
                    break;
                }
            }

            if (startLine == -1) {
                System.err.println("User start block not found.");
                return;
            }

            int endLine = startLine;
            while (endLine < lines.length && !lines[endLine].equals("}")) {
                endLine++;
            }

            // Replace the user block with new data
            List<String> updatedLines = new ArrayList<String>();
            for (int i = 0; i < startLine; i++) {
                updatedLines.add(lines[i]);
            }
            updatedLines.add(user.toFileFormat(true));
            for (int i = endLine + 1; i < lines.length; i++) {
                updatedLines.add(lines[i]);
            }

            // Write the updated content back to the file
            BufferedWriter writer = new BufferedWriter(new FileWriter(Userbase));
            for (String Line : updatedLines) {
                writer.write(Line);
                writer.newLine();
            }
            writer.close();

        } catch (IOException e) {
            System.err.println("Error updating file: " + e.getMessage());
        }
    }
}
