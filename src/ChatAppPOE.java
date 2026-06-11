import java.util.Scanner;
import java.util.ArrayList;

public class ChatAppPOE {

    // ================= USERNAME METHOD =================
    public static boolean checkUsername(String username) {
        return username.contains("_") && username.length() <= 8;
    }

    // ================= PASSWORD METHOD =================
    public static boolean checkPassword(String password) {
        boolean hasCapital = false;
        boolean hasNumber = false;
        boolean hasSpecial = false;

        for (int i = 0; i < password.length(); i++) {

            char ch = password.charAt(i);

            if (Character.isUpperCase(ch)) {
                hasCapital = true;
            }

            if (Character.isDigit(ch)) {
                hasNumber = true;
            }

            if ("!@#$%^&*".indexOf(ch) != -1) {
                hasSpecial = true;
            }
        }

        return password.length() >= 8 && hasCapital && hasNumber && hasSpecial;
    }

    // ================= LOGIN METHOD =================
    public static boolean loginUser(String username, String password,
                                    ArrayList<String> usernames,
                                    ArrayList<String> passwords) {

        for (int i = 0; i < usernames.size(); i++) {

            if (username.equals(usernames.get(i)) &&
                    password.equals(passwords.get(i))) {
                return true;
            }
        }

        return false;
    }

    public static void main(String[] args) {

        // CREATE SCANNER
        Scanner scanner = new Scanner(System.in);

        // CREATE ARRAYLISTS TO STORE USERS
        ArrayList<String> usernames = new ArrayList<>();
        ArrayList<String> passwords = new ArrayList<>();

        // MAIN LOOP CONTROL
        boolean running = true;

        Message.loadTestData();

        while (running) {

            // ================= MAIN MENU =================
            System.out.println("\n1. Register");
            System.out.println("2. Login");
            System.out.println("3. Stored Messages");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            int option = scanner.nextInt();
            scanner.nextLine(); // CLEAR BUFFER

            // ================= REGISTER =================
            if (option == 1) {

                System.out.print("Enter username: ");
                String username = scanner.nextLine();

                System.out.print("Enter password: ");
                String password = scanner.nextLine();

                // USERNAME CHECK
                boolean usernameValid = false;

                if (username.contains("_") && username.length() <= 8) {
                    usernameValid = true;
                    System.out.println("Username successfully captured");
                } else {
                    System.out.println("Username must contain '_' and be no more than 8 characters");
                }

                // PASSWORD CHECK
                boolean passwordValid = false;

                boolean hasCapital = false;
                boolean hasNumber = false;
                boolean hasSpecial = false;

                for (int i = 0; i < password.length(); i++) {

                    char ch = password.charAt(i);

                    if (Character.isUpperCase(ch)) {
                        hasCapital = true;
                    }

                    if (Character.isDigit(ch)) {
                        hasNumber = true;
                    }

                    if ("!@#$%^&*".indexOf(ch) != -1) {
                        hasSpecial = true;
                    }
                }

                if (password.length() >= 8 && hasCapital && hasNumber && hasSpecial) {
                    passwordValid = true;
                    System.out.println("Password successfully captured");
                } else {
                    System.out.println("Password must be at least 8 characters long and include a capital letter, number, and special character");
                }

                // CHECK IF USERNAME ALREADY EXISTS
                boolean exists = false;

                for (int i = 0; i < usernames.size(); i++) {
                    if (username.equals(usernames.get(i))) {
                        exists = true;
                        break;
                    }
                }

                // SAVE USER
                if (exists) {
                    System.out.println("Username already exists");
                } else if (usernameValid && passwordValid) {
                    usernames.add(username);
                    passwords.add(password);
                    System.out.println("Registration successful");
                } else {
                    System.out.println("Registration failed");
                }
            }

            // ================= LOGIN =================
            else if (option == 2) {

                System.out.print("Enter username: ");
                String loginUsername = scanner.nextLine();

                System.out.print("Enter password: ");
                String loginPassword = scanner.nextLine();

                boolean found = false;

                // CHECK STORED USERS
                for (int i = 0; i < usernames.size(); i++) {

                    if (loginUsername.equals(usernames.get(i)) &&
                            loginPassword.equals(passwords.get(i))) {

                        found = true;
                        break;
                    }
                }

                // LOGIN RESULT
                if (found) {
                    System.out.println("Welcome " + loginUsername + ", it is great to see you again.");
                    System.out.print("Enter recipient number: ");
                    String recipient = scanner.nextLine();
                    System.out.print("Enter your message. ");
                    String message = scanner.nextLine();
                    Message msg = new Message(recipient, message);
                    String result = msg.sentMessage();
                    System.out.println(result);
                } else {
                    System.out.println("Username or password incorrect, please try again.");
                }
            }
            // Stored Messages
            else if (option == 3) {
                Message.readStoredMessages();
                System.out.println("\na. Display sender + recipient of all stored messages");
                System.out.println("b. Display longest stored message");
                System.out.println("c. Search by message ID");
                System.out.println("d. Search all messages for particular recipient");
                System.out.println("e. Delete a message using message hash");
                System.out.println("f. Display a report of all stored messages");
                System.out.print("Enter your choice: ");
                String subChoice = scanner.nextLine();

                if (subChoice.equals("a")) {
                    ArrayList<String> storedMessages = Message.getStoredMessages();
                    ArrayList<String> recipients = Message.getRecipients();
                    for (int i = 0; i < storedMessages.size(); i++) {
                        System.out.println("Recipient: " + recipients.get(i));
                        System.out.println("Message: " + storedMessages.get(i));
                        System.out.println();
                    }
                }
                if (subChoice.equals("b")) {
                    ArrayList<String> storedMessages = Message.getStoredMessages();
                    int longestIndex = 0;
                    for (int i = 0; i < storedMessages.size(); i++) {
                        if (storedMessages.get(i).length() > storedMessages.get(longestIndex).length()) {
                            longestIndex = i;
                        }
                    }
                    System.out.println("Longest message: " + storedMessages.get(longestIndex));


                } else if (subChoice.equals("c")) {
                    System.out.println("Enter message ID");
                    String searchID = scanner.nextLine();
                    ArrayList<String> messageIDs = Message.getMessageIDs();
                    ArrayList<String> storedMessages = Message.getStoredMessages();
                    ArrayList<String> recipients = Message.getRecipients();
                    boolean found = false;
                    for (int i = 0; i < messageIDs.size(); i++) {
                        if (messageIDs.get(i).equals(searchID)) {
                            System.out.println("Recipient: " + recipients.get(i));
                            System.out.println("Message: " + storedMessages.get(i));
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        System.out.println("Message ID does not exist");
                    }

                } else if (subChoice.equals("d")) {
                    System.out.println("Enter recipient number");
                    ArrayList<String> recipients = Message.getRecipients();
                    ArrayList<String> storedMessages = Message.getStoredMessages();
                    String searchID = scanner.nextLine();
                    boolean found = false;
                    for (int i = 0; i < recipients.size(); i++) {
                        if (recipients.get(i).equals(searchID)) {
                            System.out.println("Message: " + storedMessages.get(i));
                            found = true;
                        }
                    }
                    if (!found) {
                        System.out.println("No messages for that recipient.");
                    }
                } else if (subChoice.equals("e")) {
                    System.out.println("Enter message hash");
                    String searchHash = scanner.nextLine();
                    ArrayList<String> messageHashes = Message.getMessageHashes();
                    ArrayList<String> storedMessages = Message.getStoredMessages();
                    ArrayList<String> messageIDs = Message.getMessageIDs();
                    ArrayList<String> recipients = Message.getRecipients();
                    int index = messageHashes.indexOf(searchHash);
                    if (index != -1) {
                        messageHashes.remove(index);
                        storedMessages.remove(index);
                        recipients.remove(index);
                        messageIDs.remove(index);
                        System.out.println("Message hash deleted");
                    } else {
                        System.out.println("Message hash does not exist");
                    }

                } else if (subChoice.equals("f")) {
                    ArrayList<String> messageHashes = Message.getMessageHashes();
                    ArrayList<String> storedMessages = Message.getStoredMessages();
                    ArrayList<String> recipients = Message.getRecipients();
                    for (int i = 0; i < storedMessages.size(); i++) {
                        System.out.println("Message hash: " + messageHashes.get(i));
                        System.out.println("Message: " + storedMessages.get(i));
                        System.out.println("Recipient:  " + recipients.get(i));
                        System.out.println();
                    }
                }
            }

                // ================= EXIT =================
                else if (option == 4) {
                    System.out.println("Goodbye!");
                    running = false;
                }

                // ================= INVALID =================
                else {
                    System.out.println("Invalid option");
                }
            }

            scanner.close();
        }
    }