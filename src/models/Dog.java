package models;

import java.util.Objects;

// Dog extends Mammal, so it inherits all Pet + Mammal behaviour
public class Dog extends Mammal {

    // Constants: fixed pricing rules for dogs
    // Using static final ensures values never change during runtime
    private static final float DANGEROUS_DAILY_RATE = 40;
    private static final float NONDANGEROUS_DAILY_RATE = 30;

    private String breed;
    private boolean dangerousBreed;

    public Dog(String name, int age, Owner owner, int id,
               char sex, boolean vaccinated, double weight,
               boolean neutered, String breed, boolean dangerousBreed) {

        // Pass shared attributes to parent class (Mammal → Pet)
        super(name, age, owner, id, sex, vaccinated, weight, neutered);

        this.breed = breed;
        this.dangerousBreed = dangerousBreed;
    }

    public String getBreed() {
        return breed;
    }

    public boolean isDangerousBreed() {
        return dangerousBreed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public void setDangerousBreed(boolean dangerousBreed) {
        this.dangerousBreed = dangerousBreed;
    }

    // -------------------------------
    // POLYMORPHISM: fee calculation override
    // -------------------------------
    // Each pet type has its own pricing logic.
    // Dogs charge different rates depending on risk level.
    @Override
    public double calculateWeeklyFee() {

        // Choose rate based on danger classification
        double dailyRate = dangerousBreed
                ? DANGEROUS_DAILY_RATE
                : NONDANGEROUS_DAILY_RATE;

        double total = 0.0;

        // getDaysAttending() is inherited from Pet/Mammal
        // It stores which days the dog attends daycare (boolean array)
        for (boolean day : getDaysAttending())
            if (day) total += dailyRate;

        return total;
    }

    // -------------------------------
    // equals() override (object comparison logic)
    // -------------------------------
    // Two dogs are considered equal if:
    // - same breed
    // - same dangerousBreed status
    @Override
    public boolean equals(Object o) {

        // Fast fail: null or different class means not equal
        if (o == null || getClass() != o.getClass())
            return false;

        Dog dog = (Dog) o;

        return dangerousBreed == dog.dangerousBreed
                && Objects.equals(breed, dog.breed);
    }

    // -------------------------------
    // toString override (adds Dog-specific details)
    // -------------------------------
    @Override
    public String toString() {
        return "[Dog] " + super.toString() +
                ", Breed: " + breed +
                ", dangerous: " + (dangerousBreed ? "Yes" : "No") +
                ", Weekly Fee: €" + calculateWeeklyFee();
    }
}