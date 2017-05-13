package Core;


import java.io.IOException;


public class Main {

    public static void main(String[] args) throws IOException {
        PreRegServer server = PreRegServer.getInstance();
        server.init(5050);
        server.start();
    }
}