package models;

import java.util.Objects;

// Simple data class representing a pet owner
public class Owner {

    private int id = 100; // default ID (must be 3 digits)
    private String name;  // max 30 characters
    private String phoneNumber = "087302000";

    public Owner(int id, String name, String phoneNumber) {
        setId(id);
        initName(name);
        setPhoneNumber(phoneNumber);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    // -------------------------------
    // ID VALIDATION
    // -------------------------------
    // Ensures ID is always a 3-digit number (100–999)
    public void setId(int id) {
        if (id >= 100 && id <= 999)
            this.id = id;
    }

    // -------------------------------
    // NAME INITIALISATION (safe truncation)
    // -------------------------------
    // Used in constructor to enforce max length rule immediately
    public void initName(String name) {
        this.name = (name.length() <= 30)
                ? name
                : name.substring(0, 30);
    }

    public void setName(String name) {
        if (name.length() <= 30)
            this.name = name;
    }

    // -------------------------------
    // PHONE VALIDATION
    // -------------------------------
    // Only allows numeric strings (no spaces or symbols)
    private boolean onlyContainsNumbers(String text) {
        return text.matches("[0-9]+");
    }

    public void setPhoneNumber(String phoneNumber) {
        if (onlyContainsNumbers(phoneNumber))
            this.phoneNumber = phoneNumber;
    }

    // -------------------------------
    // equals() override (identity logic)
    // -------------------------------
    // Two owners are considered equal if:
    // - same ID
    // - same name
    @Override
    public boolean equals(Object o) {

        // Fast identity check
        if (this == o) return true;

        // Ensure same class type
        if (o == null || getClass() != o.getClass())
            return false;

        Owner owner = (Owner) o;

        return getId() == owner.getId()
                && Objects.equals(getName(), owner.getName());
    }

    // -------------------------------
    // toString override (display format)
    // -------------------------------
    @Override
    public String toString() {
        return "Id: " + id +
                ", name: " + name +
                ", phone: " + phoneNumber;
    }
}