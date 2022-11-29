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
}
