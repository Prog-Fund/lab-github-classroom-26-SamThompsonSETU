package models;

// Cat extends Mammal, so it inherits all mammal + pet behaviour
public class Cat extends Mammal {

    private String favouriteToy;
    private boolean indoorCat;

    public Cat(String name, int age, Owner owner, int id,
               char sex, boolean vaccinated, double weight,
               boolean neutered, boolean indoorCat, String favouriteToy) {

        // Pass shared attributes up to Mammal (which also calls Pet)
        super(name, age, owner, id, sex, vaccinated, weight, neutered);

        this.indoorCat = indoorCat;
        this.favouriteToy = favouriteToy;
    }

    public String getFavouriteToy() {
        return favouriteToy;
    }

    public boolean isIndoorCat() {
        return indoorCat;
    }

    public void setFavouriteToy(String favouriteToy) {
        this.favouriteToy = favouriteToy;
    }

    public void setIndoorCat(boolean indoorCat) {
        this.indoorCat = indoorCat;
    }

    // -------------------------------
    // Weekly fee calculation (polymorphism override)
    // -------------------------------
    // Each pet type calculates cost differently.
    // Cats charge per day depending on indoor/outdoor status.
    @Override
    public double calculateWeeklyFee() {

        // Indoor cats are more expensive per day than outdoor cats
        double dailyRate = indoorCat ? 25 : 20;

        double total = 0.0;

        // getDaysAttending() comes from parent class
        // It tracks which days the pet is booked in (boolean array)
        for (boolean day : getDaysAttending())
            if (day) total += dailyRate;

        return total;
    }

    // -------------------------------
    // toString override (adds Cat-specific details)
    // -------------------------------
    @Override
    public String toString() {
        return "[Cat] " + super.toString() +
                ", favouriteToy: " + favouriteToy +
                ", indoorCat: " + (indoorCat ? "Yes" : "No");
    }
}