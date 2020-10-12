package seedu.duke.human;

import org.junit.jupiter.api.Test;
import seedu.duke.exception.AniException;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserTest {

    @Test
    public void testProfileCreation() throws ParseException, AniException {
        User testProfile = new User("Tim", "1/11/1960", "Other");
        assertEquals("\nName: Tim\nBirthdate: 01/11/1960\nGender: Other", testProfile);
    }
}
