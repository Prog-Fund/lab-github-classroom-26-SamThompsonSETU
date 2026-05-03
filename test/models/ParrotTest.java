package models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ParrotTest {

    // Test objects used across multiple tests
    Owner owner;
    Parrot parrot;

    // Runs before each test to set up fresh objects
    @BeforeEach
    void setUp() {
        // Create a sample owner
        owner = new Owner(100, "John Doe", "0871234567");

        // Create a default parrot with minimal vocabulary
        parrot = new Parrot("Polly", 2, owner, 1000, 0.5, true, 0);
    }

    // Test that constructor correctly sets all fields
    @Test
    void testConstructorAndGetters() {
        assertEquals("Polly", parrot.getName());
        assertEquals(2, parrot.getAge());
        assertEquals(owner, parrot.getOwner());
        assertEquals(1000, parrot.getId());
        assertEquals(0.5, parrot.getWingSpan());
        assertTrue(parrot.isCanFly());

        // Vocabulary is converted from int → string using BirdUtility
        assertEquals("Minimal", parrot.getVocabularySize());
    }

    // Test conversion of vocabulary size to "Basic"
    @Test
    void testVocabularySizeBasic() {
        Parrot p = new Parrot("Tweety", 1, owner, 1001, 0.3, true, 5);
        assertEquals("Basic", p.getVocabularySize());
    }

    // Test conversion of vocabulary size to "Impressive"
    @Test
    void testVocabularySizeImpressive() {
        Parrot p = new Parrot("Tweety", 1, owner, 1001, 0.3, true, 20);
        assertEquals("Impressive", p.getVocabularySize());
    }

    // Test conversion of vocabulary size to "Amazing"
    @Test
    void testVocabularySizeAmazing() {
        Parrot p = new Parrot("Tweety", 1, owner, 1001, 0.3, true, 50);
        assertEquals("Amazing", p.getVocabularySize());
    }

    // Test setter correctly updates vocabulary level
    @Test
    void testSetVocabularySize() {
        parrot.setVocabularySize(20);
        assertEquals("Impressive", parrot.getVocabularySize());
    }

    // Test setter for wing span
    @Test
    void testSetWingSpan() {
        parrot.setWingSpan(1.5);
        assertEquals(1.5, parrot.getWingSpan());
    }

    // Test setter for flying ability
    @Test
    void testSetCanFly() {
        parrot.setCanFly(false);
        assertFalse(parrot.isCanFly());
    }

    // Test weekly fee when attending 2 days (€10 per day)
    @Test
    void testCalculateWeeklyFee() {
        boolean[] days = {true, true, false, false, false, false, false};
        parrot.setDaysAttending(days);

        assertEquals(2 * 10, parrot.calculateWeeklyFee());
    }

    // Test weekly fee when attending all 7 days
    @Test
    void testCalculateWeeklyFeeAllDays() {
        boolean[] days = {true, true, true, true, true, true, true};
        parrot.setDaysAttending(days);

        assertEquals(7 * 10, parrot.calculateWeeklyFee());
    }

    // Test weekly fee when attending no days
    @Test
    void testCalculateWeeklyFeeNoDays() {
        boolean[] days = {false, false, false, false, false, false, false};
        parrot.setDaysAttending(days);

        assertEquals(0, parrot.calculateWeeklyFee());
    }

    // Test that toString contains key information
    @Test
    void testToString() {
        String result = parrot.toString();

        // Should include name and vocabulary details
        assertTrue(result.contains("Polly"));
        assertTrue(result.contains("vocabularySize"));
        assertTrue(result.contains("Minimal"));
    }

    // Test equality logic (based on fields defined in equals method)
    @Test
    void testEquals() {
        // Different values but may still be equal depending on equals() implementation
        Parrot same = new Parrot("Different", 5, owner, 2000, 0.5, true, 10);
        assertEquals(parrot, same);
    }

    // Test inequality when wing span differs
    @Test
    void testEqualsDifferentWingSpan() {
        Parrot different = new Parrot("Polly", 2, owner, 1000, 1.0, true, 0);
        assertNotEquals(parrot, different);
    }

    // Test inequality when flying ability differs
    @Test
    void testEqualsDifferentCanFly() {
        Parrot different = new Parrot("Polly", 2, owner, 1000, 0.5, false, 0);
        assertNotEquals(parrot, different);
    }

    // Test equals method handles null safely
    @Test
    void testEqualsNull() {
        assertFalse(parrot.equals(null));
    }

    // Test equals method returns false for different object types
    @Test
    void testEqualsDifferentClass() {
        assertFalse(parrot.equals(owner));
    }
}