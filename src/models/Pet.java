package models;

// Abstract base class for all pets (Dog, Cat, Bird, etc.)
// Cannot be instantiated directly
public abstract class Pet {

    private String name;
    private int age;
    private Owner owner;

    // Represents which days the pet attends daycare (Mon–Sun)
    private boolean[] daysAttending;

    private int id = 1000;

    public Pet(String name, int age, Owner owner, int id) {

        // Name is truncated if too long (data consistency rule)
        initName(name);

        this.age = age;
        this.owner = owner;

        // Fixed size schedule: 7 days in a week
        this.daysAttending = new boolean[7];

        setId(id);
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public int getId() {
        return id;
    }

    public Owner getOwner() {
        return owner;
    }

    public boolean[] getDaysAttending() {
        return daysAttending;
    }

    // -------------------------------
    // VALIDATION: NAME LENGTH LIMIT
    // -------------------------------
    public void setName(String name) {
        if (name.length() <= 20)
            this.name = name;
    }

    // Age is restricted to realistic pet range
    public void setAge(int age) {
        if (age >= 0 && age <= 20)
            this.age = age;
    }

    // Ensures ID is always valid system-wide (minimum 1000)
    public void setId(int id) {
        if (id >= 1000)
            this.id = id;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public void setDaysAttending(boolean[] daysAttending) {
        this.daysAttending = daysAttending;
    }

    // -------------------------------
    // INITIAL NAME HANDLING
    // -------------------------------
    // Used in constructor to ensure safe truncation immediately
    public void initName(String name) {
        this.name = name.length() <= 20
                ? name
                : name.substring(0, 20);
    }

    // -------------------------------
    // DAY SCHEDULING SYSTEM
    // -------------------------------
    // Marks a pet as attending on a specific day (0–6 = Mon–Sun)
    public void checkIn(int dayIndex) {
        if (dayIndex >= 0 && dayIndex < 7)
            daysAttending[dayIndex] = true;
    }

    // Marks a pet as not attending on a specific day
    public void checkOut(int dayIndex) {
        if (dayIndex >= 0 && dayIndex < 7)
            daysAttending[dayIndex] = false;
    }

    // Counts total days the pet is scheduled to attend
    public int numOfDaysAttending() {
        int count = 0;

        for (boolean day : daysAttending)
            if (day) count++;

        return count;
    }

    // -------------------------------
    // POLYMORPHISM (CORE DESIGN POINT)
    // -------------------------------
    // Each subclass (Dog, Cat, Parrot) implements its own pricing logic
    public abstract double calculateWeeklyFee();

    // -------------------------------
    // toString (human-readable output)
    // -------------------------------
    @Override
    public String toString() {

        // Converts boolean array into readable day names
        String days = "";
        String[] dayNames = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};

        for (int i = 0; i < daysAttending.length; i++) {
            if (daysAttending[i])
                days += dayNames[i] + " ";
        }

        if (days.isEmpty())
            days = "None";

        return "Name: " + name +
                "  age: " + age +
                ", Owner: [" + owner.toString() + "]" +
                ", days attending: " + days.trim();
    }
}