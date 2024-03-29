package ui;
import java.util.Scanner;
import static ui.EscapeSequences.*;
import client.Client;


public class Repl {
    private final Client client;

    public Repl(String serverUrl) {
        client = new Client(serverUrl);
    }

    public void run() {
        System.out.println(BLACK_KNIGHT + "Welcome to chess! Type help to begin.");

        Scanner scanner = new Scanner(System.in);
        var result = "";
        while (!result.equals("Goodbye!")) {
            printPrompt();
            String line = scanner.nextLine();

            try {
                result = client.eval(line);
                System.out.print(SET_TEXT_COLOR_BLUE + result);
            } catch (Throwable e) {
                var msg = e.toString();
                System.out.print(msg);
            }
        }
        System.out.println();
    }

    private void printPrompt() {
        System.out.print("\n" + SET_BG_COLOR_LIGHT_GREY + ">>> " + SET_TEXT_COLOR_GREEN);
    }

}