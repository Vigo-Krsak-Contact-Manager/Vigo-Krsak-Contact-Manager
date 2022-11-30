import javax.naming.spi.DirectoryManager;
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
        try {
            //Create the directory if it does not exist
            Path dataDirectoryPath = Paths.get("data");
            if (Files.notExists(dataDirectoryPath)) {
                Files.createDirectories(dataDirectoryPath);
            }

            //Create the file if it does not exist
            Path filePath = Paths.get("date","contacts.txt");
            if(Files.notExists(filePath)){
                Files.createFile(filePath);
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        this.contactList = new ArrayList<>();
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
            Path filepath = Paths.get("data", "contacts.txt");

            //If the file does not exist create it
            if(Files.notExists(filepath)) {
                Files.createFile(filepath);
            }

            //Get an output stream to the file
            FileOutputStream stream = new FileOutputStream(filepath.toString());

            //Create an object output stream so we can write the contact objects
            ObjectOutputStream outStream = new ObjectOutputStream(stream);

            //Iterate through the contacts and save the objects to the file
            outStream.writeObject(this.contactList);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void readAllContent() {
        System.out.println("Reading the persisted data file contacts.txt");
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
            List<Contact> tmp = (List<Contact>) objInStream.readObject();

            System.out.println(tmp);

            //Steal its contact list and write it into this.contactList
            this.contactList = tmp;

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    // method to add contact
    // added functionality to check for existing contact name
    public void addContact() {

        Contact newContact = readInContact();
        List<Contact> matches = this.contactList.stream().filter((Contact contact)->contact.getName().equalsIgnoreCase(newContact.getName())).toList();
        if (matches.size() > 0) {
            System.out.println("This contact name already exists, try again.");
            return;
        }

        this.contactList.add(newContact);
        writeAllContacts();

    }

    // method to delete contact
    public void deleteContact(int index) {

        this.contactList.remove(index);
        writeAllContacts();

    }

    // method to edit contact
    public Contact editContact(int index) {
        System.out.print("Edit the contact name: ");
        String newName = this.inputScanner.nextLine();
        System.out.print("Edit the contact phone number: ");
        String newPhone = this.inputScanner.nextLine();
        //Format the number before putting it in the Contact
//        newPhone = formatNumber(extractInt(newPhone));
        //Make the contact
        Contact gatheredContact = new Contact(newName,newPhone);
        return gatheredContact;
    }

    // method to create menu
    public int menuDisplay() {
        System.out.println("1. View contacts.");
        System.out.println("2. Add a new contact.");
        System.out.println("3. Search a contact name.");
        System.out.println("4. Delete an existing contact.");
        System.out.println("5. Edit an existing contact.");
        System.out.println("6. Exit.");
        System.out.print("Enter an option (1, 2, 3, 4, 5, or 6): ");
        int userSelect = this.inputScanner.nextInt();
        this.inputScanner.nextLine();
        return userSelect;
    }

    // method to separate numbers from string
    public String extractInt(String str) {

        str = str.replaceAll("[^\\d]", "");

        str = str.trim();

        str = str.replaceAll(" + ", "");

        if (str.equals(" "))
            return " -1 ";

        return str;
    }

    // method to return formatted phone number
    public String formatNumber(String phoneNumber) {

        String number;

        if (phoneNumber.length() == 7) {
            number = phoneNumber.substring(0,3) + "-" + phoneNumber.substring(3);
        } else {
            number = phoneNumber.replaceFirst("(\\d{3})(\\d{3})(\\d+)", "($1) $2-$3");
        }
        return number;
    }

    // method to display contacts
    public void displayContacts(boolean displayNumbers) {
        System.out.printf("%-20s | %-20s|\n","Name","Phone Number");
        System.out.printf("%s\n","-".repeat(44));
//        System.out.printf("%-42s","-");
        int index = 0;
        for(Contact contact: this.contactList) {
            if (displayNumbers) {
                System.out.printf("%d-", index++);
            }
            System.out.printf("%-20s | %-20s|\n",contact.getName(),formatNumber(extractInt(contact.getPhoneNumber())));
        }

    }

    // method to search a contact
    public void searchContact() {
        System.out.print("Enter a name you'd like to search: ");
        String input = this.inputScanner.nextLine();
        var found = this.contactList.stream().filter((Contact contact) -> contact.getName().equals(input));
        System.out.printf("%-20s | %-20s|\n","Name","Phone Number");
        System.out.printf("%s\n","-".repeat(44));
        found.forEach((Contact contact) -> {
//            System.out.println(contact.getName() + "|" + contact.getPhoneNumber());
            System.out.printf("%-20s | %-20s|\n",contact.getName(),formatNumber(extractInt(contact.getPhoneNumber())));

        });
    }

    // method to call menu and access contacts
    public void menuLoop() {

        boolean inLoop = true;

        while (inLoop) {
            int choice = menuDisplay();
            switch(choice) {
                case 1:
                    displayContacts(false);
                    break;
                case 2: //Add Contact
                    addContact();
                    break;
                case 3:
                    searchContact();
                    break;
                case 4:
                    displayContacts(true);
                    int indexPicked = this.inputScanner.nextInt();
                    this.inputScanner.nextLine();
                    deleteContact(indexPicked);
                    break;
                case 5: //Edit Contact
                    displayContacts(true);
                    System.out.print("Which contact would you like to edit: ");
                    int inP = this.inputScanner.nextInt(); //inP = Index picked
                    this.inputScanner.nextLine();
                    Contact gatheredContact = editContact(inP);

                    var numbersOnly = extractInt(gatheredContact.getPhoneNumber());

                    //Check phone number length is less than 10 digits
                    if(numbersOnly.length() != 7 && numbersOnly.length() != 10){
                        System.out.println("Please enter a valid phone number either 7 or 10 digits long.");
                    }else {
                        this.contactList.get(inP).setName(gatheredContact.getName());
                        this.contactList.get(inP).setPhoneNumber(formatNumber(gatheredContact.getPhoneNumber()));
                        writeAllContacts();
                    }
                    break;
                case 6: inLoop = false;
                    break;
                default:
                    System.out.println("Please enter a valid command.");
                    break;
            }

        }

        System.exit(0);

    }

}
