import java.io.*;
import java.net.Socket;

public class StressTest {

    public static String ipAddr = "127.0.0.1";
    public static int port = 9000;
    public static void main(String[] args) {
        new ClientSomthing(ipAddr, port);
    }
}

class ClientSomthing {

    private Socket socket;
    private BufferedReader in;
    private BufferedWriter out;
    private BufferedReader inputUser;
    private String addr;
    private int port;

    public ClientSomthing(String addr, int port) {
        this.addr = addr;
        this.port = port;
        try {
            this.socket = new Socket(addr, port);
        } catch (IOException e) {
            System.err.println("Socket failed");
        }
        try {
            inputUser = new BufferedReader(new InputStreamReader(System.in));
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            new ReadMsg().start();
            new WriteMsg().start();
        } catch (IOException e) {
            ClientSomthing.this.downService();
        }
    }
    private void downService() {
        try {
            if (!socket.isClosed()) {
                socket.close();
                in.close();
                out.close();
            }
        } catch (IOException ignored) {}
    }

    private class ReadMsg extends Thread {
        @Override
        public void run() {

            String str;
            try {
                while (true) {
                    str = in.readLine();
                    if (str.equals("stop")) {
                        ClientSomthing.this.downService();
                        break;
                    }
                    if(str.startsWith("||")){
                        //todo обработка контента с сервера
                        continue;
                    }
                    System.out.println(str);
                }
            } catch (IOException e) {
                ClientSomthing.this.downService();
            }
        }
    }

    public class WriteMsg extends Thread {

        @Override
        public void run() {
            int i=0;
            try {
                out.write("////hentai"+i + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
            while (true) {
                String userWord;
                try {
                    out.write("r hentai"+i + "\n");
                    out.flush();
                    i++;
                } catch (IOException e) {
                    System.out.println(i);
                    ClientSomthing.this.downService();
                }
            }
        }
    }
}
