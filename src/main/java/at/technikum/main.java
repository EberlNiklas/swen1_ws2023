package at.technikum;

import at.technikum.apps.display.DisplayApp;
import at.technikum.server.Server;

public class main {

    public static void main(String[] args) {

        Server server = new Server(new DisplayApp());
        server.start();
    }
}