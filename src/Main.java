import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Main {

    public void readAllContent() {

        Path groceriesPath = Paths.get("data", "groceries.txt");
        List<String> groceryList = Files.readAllLines(groceriesPath);

        for (int i = 0; i < groceryList.size(); i += 1) {
            System.out.println((i + 1) + ": " + groceryList.get(i));
        }

    }

    public static void main(String[] args) {

    }

}
