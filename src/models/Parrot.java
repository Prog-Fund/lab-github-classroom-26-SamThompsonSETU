package models;

import utils.BirdUtility;

// Parrot extends Bird (which extends Pet)
// This means it inherits Pet + Bird behaviour automatically
public class Parrot extends Bird {

    // Stored as a descriptive level (not raw number)
    private String vocabularySize;

    public Parrot(String name, int age, Owner owner, int id,
                  double wingSpan, boolean canFly, int vocabularySize) {

        // Pass shared attributes up to Bird (and then Pet)
        super(name, age, owner, id, wingSpan, canFly);

        // Converts numeric input into a readable category using a utility class
        // Example: 5 → "Beginner", 20 → "Advanced"
        this.vocabularySize = BirdUtility.getVocabularyLevel(vocabularySize);
    }

    public String getVocabularySize() {
        return vocabularySize;
    }

    // -------------------------------
    // DATA NORMALISATION USING UTILITY CLASS
    // -------------------------------
    // Instead of storing raw numbers, we convert to a standard label
    // This keeps data consistent across the system
    public void setVocabularySize(int vocabularySize) {
        this.vocabularySize = BirdUtility.getVocabularyLevel(vocabularySize);
    }

    // -------------------------------
    // POLYMORPHISM: weekly fee calculation
    // -------------------------------
    // Parrots have a flat daily rate regardless of attributes
    @Override
    public double calculateWeeklyFee() {

        double total = 0.0;

        // getDaysAttending() comes from Pet class
        for (boolean day : getDaysAttending())
            if (day) total += 10;

        return total;
    }

    // -------------------------------
    // toString override (adds Parrot-specific data)
    // -------------------------------
    @Override
    public String toString() {
        return super.toString() +
                ", vocabularySize: " + vocabularySize;
    }
}