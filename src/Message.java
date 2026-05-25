import java.util.ArrayList;
import java.util.Scanner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.io.FileWriter;
import java.io.IOException;

public class Message {

    private String messageID;
    private int numMessagesSent;
    private String recipient;
    private String messageHash;
    private String message;
    private static ArrayList<Message> messageList = new ArrayList<>();

    public Message(String recipient, String message) {
        this.message = message;
        this.recipient = recipient;
        this.numMessagesSent = 0;
        this.messageID = "AUTO";
        this.messageHash = "AUTO";
    }

    public String getMessageID() { return messageID; }
    public int getNumMessagesSent() { return numMessagesSent; }
    public String getRecipient() { return recipient; }
    public String getMessageHash() { return messageHash; }
    public String getMessage() { return message; }

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
        if (choice == 1) return "Message successfully sent.";
        else if (choice == 2) return "Press 0 to delete the message.";
        else if (choice == 3) return "Message successfully stored.";
        else return "Invalid choice.";
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
        try (FileWriter file = new FileWriter("messages.json")) {
            file.write(messageArray.toJSONString());
            System.out.println("Message successfully stored.");
        } catch (IOException e) {
            System.out.println("Error storing message.");
        }
    }

}
