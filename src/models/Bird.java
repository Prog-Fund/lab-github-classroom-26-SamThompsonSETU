package models;

// Abstract class: cannot be instantiated directly
// Used as a base for specific bird types (Parrot, etc.)
public abstract class Bird extends Pet {

    private double wingSpan;
    private boolean canFly;

    public Bird(String name, int age, Owner owner, int id, double wingSpan, boolean canFly) {
        super(name, age, owner, id); // Calls Pet constructor (inherit base attributes)
        this.wingSpan = wingSpan;
        this.canFly = canFly;
    }

    public double getWingSpan() {
        return wingSpan;
    }

    public boolean isCanFly() {
        return canFly;
    }

    public void setWingSpan(double wingSpan) {
        this.wingSpan = wingSpan;
    }

    public void setCanFly(boolean canFly) {
        this.canFly = canFly;
    }

    // equals() override:
    // Compares Bird objects based only on Bird-specific fields
    // (Note: does NOT compare Pet fields like name/id unless Pet equals is used separately)
    @Override
    public boolean equals(Object o) {

        // Fast fail: null or different class = not equal
        if (o == null || getClass() != o.getClass())
            return false;

        Bird bird = (Bird) o;

        // Compare floating point safely + boolean flag
        return Double.compare(wingSpan, bird.wingSpan) == 0
                && canFly == bird.canFly;
    }

    // toString override:
    // Builds on Pet's toString() and adds Bird-specific details
    @Override
    public String toString() {
        return "[Bird] " + super.toString() +
                ", Bird: " +
                "WingSpan: " + wingSpan +
                ", canFly: " + (canFly ? "Yes" : "No");
    }
}