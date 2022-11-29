import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {

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
