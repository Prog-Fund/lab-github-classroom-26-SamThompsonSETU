package main;

import controllers.PetsDayCareAPI;
import controllers.OwnerAPI;
import models.*;
import utils.ScannerInput;

import java.io.File;
import java.util.Scanner;

public class Driver {
    private Scanner scanner = new Scanner(System.in);
    private PetsDayCareAPI petsDaycareAPI = new PetsDayCareAPI("Urban Tails", 50, new File("pets.xml"));
    private OwnerAPI ownerAPI = new OwnerAPI(new File("owners.xml"));

    public static void main(String[] args) {
        new Driver();
    }

    public Driver() {
        runMenu();
    }

    // Main menu
    private int mainMenu() {
        System.out.println("""
             -------Pet Day Care --------
            |  1) Pets CRUD MENU
            |  2) Owners CRUD MENU
            |  3) Reports MENU
            |------------------------------
            |  4) Search Pets
            |  5) Search Owners
            |------------------------------
            |  6) Save all
            |  7) Load all
            |------------------------------
            |  0) Exit
             -------------------------------""");
        return ScannerInput.readNextInt("==>> ");
    }

    // Main loop
    private void runMenu() {
        int option = mainMenu();
        while (option != 0) {
            switch (option) {
                case 1 -> runPetsAPIMenu();
                case 2 -> runOwnersAPIMenu();
                case 3 -> runPetReportsMenu();
                case 4 -> searchPets();
                case 5 -> searchOwners();
                case 6 -> {
                    saveOwners();
                    savePets();
                }
                case 7 -> {
                    loadPets();
                    loadOwners();
                }
                default -> System.out.println("Invalid option entered: " + option);
            }
            System.out.println("\nPress enter key to continue...");
            scanner.nextLine();
            scanner.nextLine();
            option = mainMenu();
        }
        System.out.println("Exiting...bye");
        System.exit(0);
    }

    //------------------------------------
    // PET MENU
    //------------------------------------

    private int petsAPIMenu() {
        System.out.println("""
                 -----Pets CRUD Menu-----
                | 1) Add a new Pet
                | 2) Delete a Pet
                | 3) List all Pet
                | 4) Update a Pet
                | 0) Return to main menu
                 ------------------------""");
        return ScannerInput.readNextInt("==>> ");
    }

    private void runPetsAPIMenu() {
        int option = petsAPIMenu();
        while (option != 0) {
            switch (option) {
                case 1 -> addPet();
                case 2 -> deletePet();
                case 3 -> System.out.println(petsDaycareAPI.listAllPets());
                case 4 -> updatePet();
                default -> System.out.println("Invalid option entered: " + option);
            }
            ScannerInput.readNextLine("\n Press the enter key to continue");
            option = petsAPIMenu();
        }
    }

    // Choose pet type
    private void addPet() {
        int pType = ScannerInput.readNextInt("""
                1) Dog
                2) Cat
                3) Parrot
                ==>> """);

        switch (pType) {
            case 1 -> addDog();
            case 2 -> addCat();
            case 3 -> addParrot();
            default -> System.out.println("Invalid pet type");
        }
    }

    // Get owner helper
    private Owner getOwner() {
        System.out.println(ownerAPI.listOwners());
        String ownerName = ScannerInput.readNextLine("Enter owner's name: ");
        Owner owner = ownerAPI.getOwnerByName(ownerName);
        if (owner == null)
            System.out.println("Owner not found");
        return owner;
    }

    // Add dog
    private void addDog() {
        String name = ScannerInput.readNextLine("Enter dog's name: ");
        int age = ScannerInput.readNextInt("Enter dog's age: ");
        Owner owner = getOwner();
        if (owner == null) return;

        int id = ScannerInput.readNextInt("Enter dog's id: ");
        char sex = ScannerInput.readNextChar("Enter sex (M/F): ");
        boolean vaccinated = ScannerInput.readNextChar("Vaccinated? (y/n): ") == 'y';
        double weight = ScannerInput.readNextDouble("Enter weight: ");
        boolean neutered = ScannerInput.readNextChar("Neutered? (y/n): ") == 'y';
        String breed = ScannerInput.readNextLine("Enter breed: ");
        boolean dangerous = ScannerInput.readNextChar("Dangerous breed? (y/n): ") == 'y';

        if (petsDaycareAPI.addPet(new Dog(name, age, owner, id, sex, vaccinated, weight, neutered, breed, dangerous)))
            System.out.println("Dog added successfully");
        else
            System.out.println("Dog could not be added");
    }

    // Add cat
    private void addCat() {
        String name = ScannerInput.readNextLine("Enter cat's name: ");
        int age = ScannerInput.readNextInt("Enter cat's age: ");
        Owner owner = getOwner();
        if (owner == null) return;

        int id = ScannerInput.readNextInt("Enter cat's id: ");
        char sex = ScannerInput.readNextChar("Enter sex (M/F): ");
        boolean vaccinated = ScannerInput.readNextChar("Vaccinated? (y/n): ") == 'y';
        double weight = ScannerInput.readNextDouble("Enter weight: ");
        boolean neutered = ScannerInput.readNextChar("Neutered? (y/n): ") == 'y';
        boolean indoor = ScannerInput.readNextChar("Indoor cat? (y/n): ") == 'y';
        String toy = ScannerInput.readNextLine("Enter favourite toy: ");

        if (petsDaycareAPI.addPet(new Cat(name, age, owner, id, sex, vaccinated, weight, neutered, indoor, toy)))
            System.out.println("Cat added successfully");
        else
            System.out.println("Cat could not be added");
    }

    // Add parrot
    private void addParrot() {
        String name = ScannerInput.readNextLine("Enter parrot's name: ");
        int age = ScannerInput.readNextInt("Enter parrot's age: ");
        Owner owner = getOwner();
        if (owner == null) return;

        int id = ScannerInput.readNextInt("Enter parrot's id: ");
        double wingspan = ScannerInput.readNextDouble("Enter wingspan: ");
        boolean canFly = ScannerInput.readNextChar("Can fly? (y/n): ") == 'y';
        int vocabSize = ScannerInput.readNextInt("Enter vocabulary size: ");

        if (petsDaycareAPI.addPet(new Parrot(name, age, owner, id, wingspan, canFly, vocabSize)))
            System.out.println("Parrot added successfully");
        else
            System.out.println("Parrot could not be added");
    }

    // Delete pet
    private void deletePet() {
        System.out.println(petsDaycareAPI.listAllPets());
        int index = ScannerInput.readNextInt("Enter index to delete: ");
        Pet p = petsDaycareAPI.removePet(index);

        if (p != null)
            System.out.println("Deleted: " + p);
        else
            System.out.println("No pet deleted");
    }

    // Update pet (name + age only)
    private void updatePet() {
        System.out.println(petsDaycareAPI.listAllPets());
        int index = ScannerInput.readNextInt("Enter index to update: ");

        if (petsDaycareAPI.isValidPetIndex(index)) {
            Pet existing = petsDaycareAPI.getPet(index);
            String name = ScannerInput.readNextLine("Enter new name: ");
            int age = ScannerInput.readNextInt("Enter new age: ");

            existing.setName(name);
            existing.setAge(age);

            System.out.println("Pet updated: " + existing);
        } else {
            System.out.println("Invalid index");
        }
    }

    //------------------------------------
    // OWNER MENU
    //------------------------------------

    private int ownersAPIMenu() {
        System.out.println("""
                 -----Owners CRUD Menu----
                | 1) Add a new Owner
                | 2) Delete an owner
                | 3) List all Owners
                | 4) update an Owner
                | 0) Return to main menu
                 -----------------------""");
        return ScannerInput.readNextInt("==>> ");
    }

    private void runOwnersAPIMenu() {
        int option = ownersAPIMenu();
        while (option != 0) {
            switch (option) {
                case 1 -> addOwner();
                case 2 -> deleteOwner();
                case 3 -> System.out.println(ownerAPI.listOwners());
                case 4 -> updateOwner();
                default -> System.out.println("Invalid option entered: " + option);
            }
            ScannerInput.readNextLine("\n Press the enter key to continue");
            option = ownersAPIMenu();
        }
    }

    private void addOwner() {
        int id = ScannerInput.readNextInt("Enter id: ");
        String name = ScannerInput.readNextLine("Enter owner name: ");
        String phone = ScannerInput.readNextLine("Enter phone number: ");

        Owner owner = new Owner(id, name, phone);

        if (ownerAPI.addOwner(owner))
            System.out.println("Successfully added owner: " + owner);
        else
            System.out.println("Owner could not be added");
    }

    private void deleteOwner() {
        System.out.println(ownerAPI.listOwners());
        int index = ScannerInput.readNextInt("Enter index to delete: ");
        Owner ow = ownerAPI.getOwnerByIndex(index);

        if (ownerAPI.removeOwner(ow))
            System.out.println("Successfully removed: " + ow);
        else
            System.out.println("No owner removed");
    }

    private void updateOwner() {
        System.out.println(ownerAPI.listOwners());
        int index = ScannerInput.readNextInt("Enter index to update: ");
        Owner ow = ownerAPI.getOwnerByIndex(index);

        if (ow != null) {
            String name = ScannerInput.readNextLine("Enter updated name: ");
            String phone = ScannerInput.readNextLine("Enter updated phone: ");

            ownerAPI.updateOwner(index, name, phone);
            System.out.println("Owner updated successfully");
        } else {
            System.out.println("Invalid index");
        }
    }

    //------------------------------------
    // REPORTS
    //------------------------------------

    private int petsReportsMenu() {
        System.out.println("""
                 ---------- Pet Reports Menu --------
                | 1) List all Pets
                | 2) List all Dogs
                | 3) List all cats
                | 4) List all Parrots
                | 5) List all Dangerous Dogs
                | 6) List all Indoor Cats
                | 7) List pets by owner (Not done)
                | 9) Weekly income Report
                | 10) Avg days per week (Not done)
                | 0) Return
                 -------------------------------------""");
        return ScannerInput.readNextInt("==>> ");
    }

    private void runPetReportsMenu() {
        int option = petsReportsMenu();
        while (option != 0) {
            switch (option) {
                case 1 -> System.out.println(petsDaycareAPI.listAllPets());
                case 2 -> System.out.println(petsDaycareAPI.listAllDogs());
                case 3 -> System.out.println(petsDaycareAPI.listAllCats());
                case 4 -> System.out.println(petsDaycareAPI.listAllParrots());
                case 5 -> System.out.println(petsDaycareAPI.listAllDangerousDogs());
                case 6 -> System.out.println(petsDaycareAPI.numberOfIndoorCats() + " indoor cats");
                case 7 -> System.out.println("Not done");
                case 9 -> System.out.println("Weekly income: €" + petsDaycareAPI.getWeeklyIncome());
                case 10 -> System.out.println("Average days per week: " + petsDaycareAPI.getAverageNumDaysPerWeek());
                default -> System.out.println("Invalid option entered: " + option);
            }
            ScannerInput.readNextLine("\n Press enter to continue");
            option = petsReportsMenu();
        }
    }

    //------------------------------------
    // SEARCH
    //------------------------------------

    private void searchPets() {
        String name = ScannerInput.readNextLine("Enter pet name: ");
        Pet pet = petsDaycareAPI.getPet(name);

        if (pet != null)
            System.out.println(pet);
        else
            System.out.println("No pet found");
    }

    private void searchOwners() {
        String prefix = ScannerInput.readNextLine("Enter owner prefix: ");
        System.out.println(ownerAPI.listOwnersStartsWith(prefix));
    }

    //------------------------------------
    // SAVE / LOAD
    //------------------------------------

    private void saveOwners() {
        try {
            ownerAPI.save();
        } catch (Exception e) {
            System.err.println("Error saving owners");
        }
    }

    private void loadOwners() {
        try {
            ownerAPI.load();
        } catch (Exception e) {
            System.err.println("Error loading owners");
        }
    }

    private void savePets() {
        try {
            petsDaycareAPI.save();
        } catch (Exception e) {
            System.err.println("Error saving pets");
        }
    }

    private void loadPets() {
        try {
            petsDaycareAPI.load();
        } catch (Exception e) {
            System.err.println("Error loading pets");
        }
    }
}