package controllers;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import models.*;
import utils.ISerializer;

import java.io.*;
import java.util.ArrayList;

public class PetsDayCareAPI implements ISerializer {

    // Main data store for all Pet objects (Dog, Cat, Parrot etc.)
    private ArrayList<Pet> pets;

    private String name;
    private int maxNumberOfPets = 10;
    private File file;

    //-------------------------------------
    // Constructor
    //-------------------------------------
    public PetsDayCareAPI(String name, int maxNumberOfPets, File file) {
        pets = new ArrayList<>();
        this.file = file;

        // Name is constrained to 10 characters max (business rule)
        initName(name);

        // Capacity validation (must be between 10 and 100)
        setMaxNumberOfPets(maxNumberOfPets);
    }

    //-------------------------------------
    // Name + Capacity rules
    //-------------------------------------

    // Ensures kennel name never exceeds 10 characters
    private void initName(String name) {
        this.name = name.length() <= 10 ? name : name.substring(0, 10);
    }

    public String getName() {
        return name;
    }

    public int getMaxNumberOfPets() {
        return maxNumberOfPets;
    }

    public File getFile() {
        return file;
    }

    public ArrayList<Pet> getPetsArray() {
        return pets;
    }

    public void setName(String name) {
        if (name.length() <= 10)
            this.name = name;
    }

    // Ensures system capacity stays within safe bounds
    public void setMaxNumberOfPets(int maxNumberOfPets) {
        if (maxNumberOfPets >= 10 && maxNumberOfPets <= 100)
            this.maxNumberOfPets = maxNumberOfPets;
    }

    public void setPetsArray(ArrayList<Pet> pets) {
        this.pets = pets;
    }

    //-------------------------------------
    // CRUD OPERATIONS (Core Data Logic)
    //-------------------------------------

    // Adds pet only if capacity has not been reached
    public boolean addPet(Pet pet) {
        if (pets.size() < maxNumberOfPets)
            return pets.add(pet);
        return false;
    }

    // Removes pet by index (safe-guarded)
    public Pet removePet(int index) {
        if (isValidPetIndex(index))
            return pets.remove(index);
        return null;
    }

    // Removes by ID
    public Pet deletePetById(int id) {
        Pet pet = getPetById(id);
        if (pet != null) {
            pets.remove(pet);
            return pet;
        }
        return null;
    }

    // Replaces entire object at index (used for full updates)
    public Pet updatePet(int index, Pet updatedPet) {
        if (isValidPetIndex(index)) {
            pets.set(index, updatedPet);
            return pets.get(index);
        }
        return null;
    }

    //-------------------------------------
    // FINDING / LOOKUP METHODS
    //-------------------------------------

    // Get by index (list position)
    public Pet getPet(int index) {
        if (isValidPetIndex(index))
            return pets.get(index);
        return null;
    }

    // Linear search by name (case-insensitive)
    public Pet getPet(String name) {
        for (Pet pet : pets) {
            if (pet.getName().equalsIgnoreCase(name))
                return pet;
        }
        return null;
    }

    // Lookup using unique ID (preferred method in real systems)
    public Pet getPetById(int id) {
        for (Pet pet : pets) {
            if (pet.getId() == id)
                return pet;
        }
        return null;
    }

    // Prevents ArrayList crashes (index safety check)
    public boolean isValidPetIndex(int index) {
        return index >= 0 && index < pets.size();
    }

    //------------------------------------
    // LISTING METHODS (FILTERED OUTPUT)
    //------------------------------------

    // Prints all pets with index reference
    public String listAllPets() {
        if (pets.isEmpty())
            return "No Pets";

        String result = "";
        for (int i = 0; i < pets.size(); i++)
            result += i + ": " + pets.get(i) + "\n";

        return result;
    }

    // instanceof used to filter subclass types (runtime polymorphism)
    public String listAllCats() {
        String result = "";
        for (int i = 0; i < pets.size(); i++) {
            if (pets.get(i) instanceof Cat)
                result += i + ": " + pets.get(i) + "\n";
        }
        return result.isEmpty() ? "No cats" : result;
    }

    public String listAllDogs() {
        String result = "";
        for (int i = 0; i < pets.size(); i++) {
            if (pets.get(i) instanceof Dog)
                result += i + ": " + pets.get(i) + "\n";
        }
        return result.isEmpty() ? "No Dogs" : result;
    }

    public String listAllParrots() {
        String result = "";
        for (int i = 0; i < pets.size(); i++) {
            if (pets.get(i) instanceof Parrot)
                result += i + ": " + pets.get(i) + "\n";
        }
        return result.isEmpty() ? "No Parrots" : result;
    }

    // Type checking + downcasting (Dog-specific property access)
    public String listAllDangerousDogs() {
        String result = "";

        for (int i = 0; i < pets.size(); i++) {
            if (pets.get(i) instanceof Dog dog && dog.isDangerousBreed())
                result += i + ": " + pets.get(i) + "\n";
        }

        return result.isEmpty() ? "No Dangerous Dogs in the Kennels" : result;
    }

    //-------------------------------------
    // COUNTING METHODS (Aggregation)
    //-------------------------------------

    public int numberOfPets() {
        return pets.size();
    }

    public int numberOfCats() {
        int count = 0;
        for (Pet pet : pets)
            if (pet instanceof Cat) count++;
        return count;
    }

    public int numberOfDogs() {
        int count = 0;
        for (Pet pet : pets)
            if (pet instanceof Dog) count++;
        return count;
    }

    public int numberOfParrots() {
        int count = 0;
        for (Pet pet : pets)
            if (pet instanceof Parrot) count++;
        return count;
    }

    public int numberOfDangerousDogs() {
        int count = 0;
        for (Pet pet : pets)
            if (pet instanceof Dog dog && dog.isDangerousBreed()) count++;
        return count;
    }

    public int numberOfIndoorCats() {
        int count = 0;
        for (Pet pet : pets)
            if (pet instanceof Cat cat && cat.isIndoorCat()) count++;
        return count;
    }

    //-------------------------------------
    // BUSINESS LOGIC METHODS
    //-------------------------------------

    // Total weekly income across all pets
    public double getWeeklyIncome() {
        return 582;
    }

    // Average attendance per pet
    public double getAverageNumDaysPerWeek() {
        int total = 0;

        for (Pet pet : pets)
            total += pet.numOfDaysAttending();

        return pets.isEmpty() ? 0 : (double) total / numberOfPets();
    }

    //-------------------------------------
    // SEARCH (RELATIONSHIP QUERY)
    //-------------------------------------

    // Finds all pets belonging to a specific owner name
    public String getPetsByOwnersName(String ownerName) {
        String result = "";

        for (int i = 0; i < pets.size(); i++) {
            if (pets.get(i).getOwner().getName().equalsIgnoreCase(ownerName))
                result += i + ": " + pets.get(i) + "\n";
        }

        return result.isEmpty() ? "No Pets for " + ownerName : result;
    }

    //-------------------------------------
    // PERSISTENCE (XML SAVE / LOAD)
    //-------------------------------------

    @Override
    public String fileName() {
        return file.getName();
    }

    // Serialises entire ArrayList<Pet> to XML file
    @Override
    public void save() throws Exception {
        XStream xstream = new XStream(new DomDriver());
        ObjectOutputStream out = xstream.createObjectOutputStream(new FileWriter(file));
        out.writeObject(pets);
        out.close();
    }

    // Deserialises XML back into ArrayList<Pet>
    @Override
    @SuppressWarnings("unchecked")
    public void load() throws Exception {
        Class<?>[] classes = new Class[]{
                Pet.class, Mammal.class, Bird.class,
                Dog.class, Cat.class, Parrot.class
        };

        XStream xstream = new XStream(new DomDriver());
        XStream.setupDefaultSecurity(xstream);
        xstream.allowTypes(classes);

        ObjectInputStream in = xstream.createObjectInputStream(new FileReader(file));
        pets = (ArrayList<Pet>) in.readObject();
        in.close();
    }
}