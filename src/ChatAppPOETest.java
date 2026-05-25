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
}