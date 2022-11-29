import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ContactList implements  Serializable {
    private List<Contact> contactList;
    private Scanner inputScanner;

    public ContactList(Scanner inputScanner){
        this.inputScanner = inputScanner;
        readAllContent();
    }

    public Contact readInContact(){
        var newContact = new Contact("","");

        System.out.print("Please enter your name: ");
        String personName = this.inputScanner.nextLine();
        System.out.print("Please enter your phone number:");
        String personPhoneNumber = this.inputScanner.nextLine();

        newContact.setName(personName);
        newContact.setPhoneNumber(personPhoneNumber);
        return newContact;
    }

    public void writeAllContacts() {
        try {
            //Get a path to the file
            Path filepath = Paths.get("data", "./contacts.txt");

            //If the file does not exist create it
            if(Files.notExists(filepath)) {
                Files.createFile(filepath);
            }

            //Get an output stream to the file
            FileOutputStream stream = new FileOutputStream(filepath.toString());

            //Create an object output stream so we can write the contact objects
            ObjectOutputStream outStream = new ObjectOutputStream(stream);

            //Iterate through the contacts and save the objects to the file
            outStream.writeObject(this);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void readAllContent() {
        try {
            //Get the path to the file
            Path contactPath = Paths.get("data", "contacts.txt");

            //If the file does not exist, create a file
            if(Files.notExists(contactPath)){
                Files.createFile(contactPath);
            }

            //Get a file input stream using the path
            FileInputStream inStream = new FileInputStream(contactPath.toString());

            //Get an object input stream
            ObjectInputStream objInStream = new ObjectInputStream(inStream);

            //Create a temporary ContactList object from the contents of the file
            ContactList tmp = (ContactList) objInStream.readObject();

            //Steal its contact list and write it into this.contactList
            this.contactList = tmp.contactList;

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    // method to add contact
    public void addContact() {

        Contact newContact = readInContact();
        this.contactList.add(newContact);
        writeAllContacts();

    }

    // method to delete contact
    public void deleteContact(int index) {

        this.contactList.remove(index);
        writeAllContacts();

    }

    // method to edit contact
    public void editContact(int index) {

        System.out.print("Edit the contact name: ");
        String newName = this.inputScanner.nextLine();
        System.out.print("Edit the contact phone number: ");
        String newPhone = this.inputScanner.nextLine();
        this.contactList.get(index).setName(newName);
        this.contactList.get(index).setPhoneNumber(newPhone);
        writeAllContacts();

    }

    // method to create menu
    public int menuDisplay() {
        System.out.println("1. View contacts.");
        System.out.println("2. Add a new contact.");
        System.out.println("3. Search a contact name.");
        System.out.println("4. Delete an existing contact.");
        System.out.println("5. Exit.");
        System.out.println("Enter an option (1, 2, 3, 4, or 5): ");
        System.out.println();
        int userSelect = this.inputScanner.nextInt();
        this.inputScanner.nextLine();
        return userSelect;
    }
    // method to display contacts
    public void displayContacts(boolean displayNumbers) {
        System.out.println("Name | Phone Number");
        System.out.println("--------------------");
        int index = 0;
        for(Contact contact: this.contactList) {
            if (displayNumbers) {
                System.out.printf("%d-", index++);
            }
            System.out.println(contact.getName() + "|" + contact.getPhoneNumber());
        }

    }

    // method to search a contact
    public void searchContact() {
        System.out.print("Enter a name you'd like to search: ");
        String input = this.inputScanner.nextLine();
        var found = this.contactList.stream().filter((Contact contact) -> contact.getName().equals(input));
        found.forEach((Contact contact) -> {
            System.out.println(contact.getName() + "|" + contact.getPhoneNumber());
        });
    }

    // method to call menu and access contacts
    public void menuLoop() {

        boolean inLoop = true;

        while (inLoop) {
            int choice = menuDisplay();
            switch(choice) {
                case 1: displayContacts(false);
                    break;
                case 2: addContact();
                    break;
                case 3: searchContact();
                    break;
                case 4: displayContacts(true);
                    int indexPicked = this.inputScanner.nextInt();
                    this.inputScanner.nextLine();
                    deleteContact(indexPicked);
                    break;
                case 5: inLoop = false;
                    break;
                default:
                    System.out.println("Please enter a valid command.");
                    break;
            }

        }

        System.exit(0);

    }

}
