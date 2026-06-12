import java.util.ArrayList;
import java.util.Scanner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileReader;

public class Message {

    private String messageID;
    private int numMessagesSent;
    private String recipient;
    private String messageHash;
    private String message;
    private static ArrayList<Message> messageList = new ArrayList<>();
    private static ArrayList<String> sentMessages = new ArrayList<>();
    private static ArrayList<String> disregardedMessages = new ArrayList<>();
    private static ArrayList<String> storedMessages = new ArrayList<>();
    private static ArrayList<String> messageHashes = new ArrayList<>();
    private static ArrayList<String> messageIDs = new ArrayList<>();
    private static ArrayList<String> recipients = new ArrayList<>();
    private static ArrayList<String> allMessages = new ArrayList<>();

    public Message(String recipient, String message) {
        this.message = message;
        this.recipient = recipient;
        this.numMessagesSent = 0;
        long id = (long) (Math.random() * 9000000000L) + 1000000000L;
        this.messageID = String.valueOf(id);
        this.messageHash = createMessageHash();
    }

    public String getMessageID() {
        return messageID;
    }

    public int getNumMessagesSent() {
        return numMessagesSent;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getMessageHash() {
        return messageHash;
    }

    public String getMessage() {
        return message;
    }

    public static ArrayList<String> getSentMessages() {
        return sentMessages;
    }

    public static ArrayList<String> getDisregardedMessages() {
        return disregardedMessages;
    }

    public static ArrayList<String> getStoredMessages() {
        return storedMessages;
    }

    public static ArrayList<String> getMessageHashes() {
        return messageHashes;
    }

    public static ArrayList<String> getMessageIDs() {
        return messageIDs;
    }

    public static ArrayList<String> getRecipients() {
        return recipients;
    }

    public static ArrayList<String> getAllMessages() { return  allMessages; }


    public boolean checkMessageID() {
        return this.messageID.length() <= 10;
    }

    public String checkRecipientCell() {
        if (this.recipient.length() <= 15 && this.recipient.startsWith("+")) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number not captured.";
        }
    }

    public String createMessageHash() {
        String idPart = this.messageID.substring(0, 2);
        String[] words = this.message.split(" ");
        return idPart + ":" + this.numMessagesSent + ":" + words[0] + words[words.length - 1];
    }

    public String sentMessage() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("1. Send Message");
        System.out.println("2. Disregard Message");
        System.out.println("3. Store Message");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        if (choice == 1) {
            sentMessages.add(this.message);
            messageHashes.add(this.messageHash);
            messageIDs.add(this.messageID);
            return "Message successfully sent.";
        } else if (choice == 2) {
            disregardedMessages.add(this.message);
            return "Press 0 to delete the message.";
        } else if (choice == 3) {
            storedMessages.add(this.message);
            messageHashes.add(this.messageHash);
            storeMessage();
            return "Message successfully stored.";
        } else return "Invalid choice.";
    }

    public String printMessage() {
        String result = "";
        for (int i = 0; i < messageList.size(); i++) {
            Message msg = messageList.get(i);
            result += "Message ID: " + msg.getMessageID() + "\n";
            result += "Message Hash: " + msg.getMessageHash() + "\n";
            result += "Recipient: " + msg.getRecipient() + "\n";
            result += "Message: " + msg.getMessage() + "\n\n";
        }
        return result;
    }

    public int returnTotalMessages() {
        return messageList.size();
    }

    public void storeMessage() {
        JSONObject messageObject = new JSONObject();
        messageObject.put("MessageID", this.messageID);
        messageObject.put("MessageHash", this.messageHash);
        messageObject.put("Recipient", this.recipient);
        messageObject.put("Message", this.message);
        JSONArray messageArray = new JSONArray();
        messageArray.add(messageObject);
        try (FileWriter file = new FileWriter("messages.json", true)) {
            file.write(messageArray.toJSONString());
            System.out.println("Message successfully stored.");
        } catch (IOException e) {
            System.out.println("Error storing message.");
        }
    }


    public static void readStoredMessages() {
        JSONParser parser = new JSONParser();
        try (FileReader reader = new FileReader("messages.json")) {
            JSONArray messageArray = (JSONArray) parser.parse(reader);
            for (int i = 0; i < messageArray.size(); i++) {
                JSONObject obj = (JSONObject) messageArray.get(i);
                storedMessages.add((String) obj.get("Message"));
                messageHashes.add((String) obj.get("MessageHash"));
                messageIDs.add((String) obj.get("MessageID"));
                recipients.add((String) obj.get("Recipient"));
            }
            System.out.println("Message successfully stored.");
        } catch (IOException | ParseException e) {
            System.out.println("No stored messages found.");
        }
    }
    public static void loadTestData() {
        sentMessages.clear();
        storedMessages.clear();
        disregardedMessages.clear();
        messageHashes.clear();
        messageIDs.clear();
        recipients.clear();
        allMessages.clear();

        sentMessages.add("Did you get the cake?");
        messageHashes.add("AU:0:DidCake");
        messageIDs.add("1234567890");
        recipients.add("+27834557896");
        allMessages.add("Did you get the cake?");

        storedMessages.add("Where are you? You are late! I have asked you to be on time.");
        messageHashes.add("AU:0:WhereTime");
        messageIDs.add("1234567891");
        recipients.add("+27838884567");
        allMessages.add("Where are you? You are late! I have asked you to be on time.");


        disregardedMessages.add("Yohoooo, I am at your gate.");

        sentMessages.add("It is dinner time!");
        messageHashes.add("AU:0:ItTime");
        messageIDs.add("1234567892");
        recipients.add("+27838884567");
        allMessages.add("It is dinner time!");

        storedMessages.add("Ok, I am leaving without you.");
        messageHashes.add("AU:0:OkYou");
        messageIDs.add("1234567893");
        recipients.add("+27838884567");
        allMessages.add("Did you get the cake?");
    }
}