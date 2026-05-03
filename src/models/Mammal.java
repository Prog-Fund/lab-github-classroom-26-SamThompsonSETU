package models;

// Abstract class: cannot be created directly
// Used as a shared base for animals like Dog and Cat
public abstract class Mammal extends Pet {

    private char sex;
    private boolean vaccinated;
    private double weight;
    private boolean neutered;

    public Mammal(String name, int age, Owner owner, int id,
                  char sex, boolean vaccinated,
                  double weight, boolean neutered) {

        // Pass shared Pet attributes to parent constructor
        super(name, age, owner, id);

        this.sex = sex;
        this.vaccinated = vaccinated;
        this.weight = weight;
        this.neutered = neutered;
    }

    public char getSex() {
        return sex;
    }

    public boolean isVaccinated() {
        return vaccinated;
    }

    public double getWeight() {
        return weight;
    }

    public boolean isNeutered() {
        return neutered;
    }

    // -------------------------------
    // INPUT VALIDATION EXAMPLE
    // -------------------------------
    // Ensures sex is always stored consistently as 'M' or 'F'
    public void setSex(char sex) {
        if (sex == 'm' || sex == 'M')
            this.sex = 'M';
        else if (sex == 'f' || sex == 'F')
            this.sex = 'F';
    }

    public void setVaccinated(boolean vaccinated) {
        this.vaccinated = vaccinated;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setNeutered(boolean neutered) {
        this.neutered = neutered;
    }

    // -------------------------------
    // toString override (adds Mammal-specific data)
    // -------------------------------
    // Builds on Pet.toString() and adds shared mammal attributes
    @Override
    public String toString() {
        return super.toString() +
                ", sex: " + sex +
                ", vaccinated: " + (vaccinated ? "Yes" : "No") +
                ", neutered: " + (neutered ? "Yes" : "No") +
                ", weight: " + weight;
    }
}