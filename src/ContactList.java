import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ContactList {
    private List<Contact> contactList;
    private Scanner inputScanner;

    public ContactList(Scanner inputScanner){
        this.inputScanner = inputScanner;
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


    public void writeAllContacts(List<String> contacts) {
        try {

            Path filepath = Paths.get("data", "contacts.txt");
            Files.write(filepath, contacts);

        } catch (Exception e) {

            System.out.println(e.getMessage());

        }
    }

    public List<String> readAllContent() {
        try {
            Path contactPath = Paths.get("data", "contacts.txt");
            List<String> contactList = Files.readAllLines(contactPath);

            return contactList;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }
    }
}
