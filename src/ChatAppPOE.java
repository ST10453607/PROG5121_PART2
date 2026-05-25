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

        while (running) {

            // ================= MAIN MENU =================
            System.out.println("\n1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
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
                    Message msg = new Message(recipient,message);
                    String result = msg.sentMessage();
                    System.out.println(result);
                } else {
                    System.out.println("Username or password incorrect, please try again.");
                }
            }

            // ================= EXIT =================
            else if (option == 3) {
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