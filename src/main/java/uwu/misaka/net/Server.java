package uwu.misaka.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class Server {
    ServerSocket server;
    public static ArrayList<MindustryServer> serversConnector;
    public Server() {
        try {
            server = new ServerSocket(9000);
            serversConnector=new ArrayList<>();
            new Connector().start();
        } catch (Exception e) {
            System.out.println("SERVER STARTING ERROR");
            System.exit(2);
        }

    }
    public class Connector extends Thread{
        @Override
        public void run() {
            while(true){
                System.out.println("[SERVER ACCEPTOR]   Waiting for connection");
                try {
                    new MindustryServer(server.accept());
                    System.out.println("[SERVER ACCEPTOR]   Server accepted");
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
            }
        }
    }
}
