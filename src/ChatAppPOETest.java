import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

public class ChatAppPOETest {

    Message textMessage = new Message("+27123456789", "Hi Zeraida, did you recieve the payment?");

    @Test
    public void testValidUsername() {
        assertTrue(ChatAppPOE.checkUsername("user_1"));
    }

    @Test
    public void testInvalidUsername() {
        assertFalse(ChatAppPOE.checkUsername("user1"));
    }

    @Test
    public void testValidPassword() {
        assertTrue(ChatAppPOE.checkPassword("Password1!"));
    }

    @Test
    public void testInvalidPassword() {
        assertFalse(ChatAppPOE.checkPassword("password"));
    }

    @Test
    public void testLoginSuccess() {

        ArrayList<String> usernames = new ArrayList<>();
        ArrayList<String> passwords = new ArrayList<>();

        usernames.add("user_1");
        passwords.add("Password1!");

        assertTrue(ChatAppPOE.loginUser("user_1", "Password1!", usernames, passwords));
    }

    @Test
    public void testLoginFail() {

        ArrayList<String> usernames = new ArrayList<>();
        ArrayList<String> passwords = new ArrayList<>();

        usernames.add("user_1");
        passwords.add("Password1!");

        assertFalse(ChatAppPOE.loginUser("user_1", "wrong", usernames, passwords));
    }
    @Test
    public void testMessageCorrectLength() {
        assertTrue(textMessage.checkRecipientCell().equals("Cell phone number successfully captured."));
    }
    @Test
    public void testMessageTooLong() {
        String longMessage = "a".repeat(251);
        Message longMsg = new Message("+27123456789", longMessage);
        assertTrue(longMessage.length() > 250);

    }
    @Test
    public void testSentMessagePopulated() {
        Message.loadTestData();
        ArrayList<String> sentMessages = Message.getSentMessages();
        assertTrue(sentMessages.contains("Did you get the cake?"));
        assertTrue(sentMessages.contains("It is dinner time!"));
    }
    @Test
    public void testlongestMessage() {
        Message.loadTestData();
        ArrayList<String> storedMessages = Message.getStoredMessages();
        int longestIndex = 0;
        for (int i = 0; i < storedMessages.size(); i++) {
            if (storedMessages.get(i).length() > storedMessages.get(longestIndex).length()) {
                longestIndex = i;
            }
        }
        assertTrue(storedMessages.contains("Where are you? You are late! I have asked you to be on time."));
    }
    @Test
    public void testSearchMessageID() {
        Message.loadTestData();
        ArrayList<String> messageIDs = Message.getMessageIDs();
        ArrayList<String> sentMessages = Message.getSentMessages();
        String result  = "";
        for (int i = 0; i < messageIDs.size(); i++) {
            if (messageIDs.get(i).equals("1234567892")) {
                result = sentMessages.get(i);
                break;

            }
        }
        assertEquals("It is dinner time!", result);
    }
    @Test
    public void testSearchRecipient() {
        Message.loadTestData();
        ArrayList<String> recipients = Message.getRecipients();
        ArrayList<String> storedMessages = Message.getStoredMessages();
        ArrayList<String> results = new ArrayList<>();
        for (int i = 0; i < recipients.size(); i++) {
            if (recipients.get(i).equals("+27838884567")) {
                results.add(storedMessages.get(i));
            }
        }
        assertTrue(results.contains("Where are you? You are late! I have asked you to be on time."));
        assertTrue(results.contains("Ok, I am leaving without you."));
    }
    @Test
    public void testDeleteMessage() {
        Message.loadTestData();
        ArrayList<String> messageIDs = Message.getMessageIDs();
        ArrayList<String> recipients = Message.getRecipients();
        ArrayList<String> storedMessages = Message.getStoredMessages();
        ArrayList<String> messageHashes = Message.getMessageHashes();
        int index = messageHashes.indexOf("AU:0:WhereTime");
        messageHashes.remove(index);
        storedMessages.remove(index);
        recipients.remove(index);
        messageIDs.remove(index);
        assertFalse(storedMessages.contains("Where are you? You are leaving without you."));
    }
    @Test
    public void testDisplayReport() {
        Message.loadTestData();
        ArrayList<String> messageHashes = Message.getMessageHashes();
        ArrayList<String> recipients = Message.getRecipients();
        ArrayList<String> storedMessages = Message.getStoredMessages();
        assertFalse(storedMessages.isEmpty());
        assertFalse(messageHashes.isEmpty());
        assertFalse(recipients.isEmpty());
        assertEquals(messageHashes.size(), storedMessages.size());
        assertEquals(storedMessages.size(), recipients.size());
    }


}