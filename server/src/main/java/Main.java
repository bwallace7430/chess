import chess.*;
import server.*;

public class Main {
    public static void main(String[] args) {
        var server = new Server();
        var port = server.run(8080);
        System.out.println("Started test HTTP server on " + port);
    }
}