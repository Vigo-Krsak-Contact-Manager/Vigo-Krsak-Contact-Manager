import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {

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

    public static void main(String[] args) {

    }

}
