package models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CatTest {

    // Shared test objects
    Owner owner;
    Cat cat;

    // Runs before each test to create fresh objects
    @BeforeEach
    void setUp() {
        // Create a sample owner
        owner = new Owner(100, "Jane Doe", "0871234567");

        // Create a default indoor cat
        cat = new Cat("Whiskers", 3, owner, 1000, 'F',
                true, 5.0, false, true, "FEATHER WAND");
    }

    // Test that constructor correctly sets all main fields
    @Test
    void testConstructorAndGetters() {
        assertEquals("Whiskers", cat.getName());
        assertEquals(3, cat.getAge());
        assertEquals(owner, cat.getOwner());
        assertEquals(1000, cat.getId());

        // Check indoor/outdoor status
        assertTrue(cat.isIndoorCat());

        // Check favourite toy is stored correctly
        assertEquals("FEATHER WAND", cat.getFavouriteToy());
    }

    // Test creating an outdoor cat (different boolean setup)
    @Test
    void testConstructorOutdoorCat() {
        Cat outdoor = new Cat("Tiger", 5, owner, 1001, 'M',
                false, 8.0, true, false, "LASER POINTER");

        // Outdoor cat should not be marked as indoor
        assertFalse(outdoor.isIndoorCat());

        // Toy should still be set correctly
        assertEquals("LASER POINTER", outdoor.getFavouriteToy());
    }

    // Test updating indoor/outdoor status
    @Test
    void testSetIndoorCat() {
        cat.setIndoorCat(false);
        assertFalse(cat.isIndoorCat());

        cat.setIndoorCat(true);
        assertTrue(cat.isIndoorCat());
    }

    // Test updating favourite toy
    @Test
    void testSetFavouriteToy() {
        cat.setFavouriteToy("LASER POINTER");
        assertEquals("LASER POINTER", cat.getFavouriteToy());
    }

    // Test weekly fee for indoor cat (€25 per day)
    @Test
    void testCalculateWeeklyFeeIndoor() {
        boolean[] days = {true, true, false, false, false, false, false};
        cat.setDaysAttending(days);

        // 2 days * €25
        assertEquals(2 * 25, cat.calculateWeeklyFee());
    }

    // Test weekly fee for outdoor cat (€20 per day)
    @Test
    void testCalculateWeeklyFeeOutdoor() {
        Cat outdoor = new Cat("Tiger", 5, owner, 1001, 'M',
                false, 8.0, true, false, "LASER POINTER");

        boolean[] days = {true, true, true, false, false, false, false};
        outdoor.setDaysAttending(days);

        // 3 days * €20
        assertEquals(3 * 20, outdoor.calculateWeeklyFee());
    }

    // Test weekly fee when cat attends no days
    @Test
    void testCalculateWeeklyFeeNoDays() {
        boolean[] days = {false, false, false, false, false, false, false};
        cat.setDaysAttending(days);

        // Should be €0 if no attendance
        assertEquals(0, cat.calculateWeeklyFee());
    }

    // Test that toString includes key information
    @Test
    void testToString() {
        String result = cat.toString();

        // Should include name, toy, and indoor status
        assertTrue(result.contains("Whiskers"));
        assertTrue(result.contains("FEATHER WAND"));
        assertTrue(result.contains("indoorCat: Yes"));
    }

    // Test toString updates correctly when cat becomes outdoor
    @Test
    void testToStringOutdoorCat() {
        cat.setIndoorCat(false);
        String result = cat.toString();

        // Should reflect outdoor status
        assertTrue(result.contains("indoorCat: No"));
    }
}