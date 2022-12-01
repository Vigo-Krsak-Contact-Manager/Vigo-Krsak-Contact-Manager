import java.util.Scanner;

    public class Main {

        public static void main(String[] args) {
            Scanner myScanner = new Scanner(System.in);
            ContactList myList = new ContactList(myScanner);

            myList.menuLoop();

        }
}
