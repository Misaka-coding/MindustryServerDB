package uwu.misaka.net;

import uwu.misaka.sended.PlayerInfo;
import uwu.misaka.sended.RatingInfo;

import java.io.*;
import java.net.Socket;

import static uwu.misaka.Ichi.storage;

public class MindustryServer {
    public String name = "UNDEFINED";
    public Socket socket;
    public BufferedReader br;
    public BufferedWriter bw;

    public MindustryServer(Socket s) throws IOException {
        socket = s;
        br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        new Accept().start();
    }

    public class Accept extends Thread {
        @Override
        public void run() {
            try {
                String s = br.readLine();
                if (s.startsWith("////")) {
                    name = s.substring(4);
                    System.out.println("["+ name+"]" + "    Connected");
                    new Reader().start();
                    Server.serversConnector.add(get());
                } else {
                    br.close();
                    bw.close();
                    return;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

        }
    }
    public class Reader extends Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    String s = br.readLine();
                    handle(s);
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }catch (NullPointerException e){
                    System.out.println("["+ name+"]" + "    Disconnected");
                    Server.serversConnector.remove(get());
                    try {
                        br.close();
                        bw.close();
                    } catch (IOException ignored) { }
                    break;
                }
            }
        }
    }

    public MindustryServer get(){return this;}

    public void handle(String s) throws IOException {
        if (s.startsWith("r ")) {
            System.out.println("[" + name + "]  traced info about " + s.substring(2));
            bw.write("w " + storage.get(s.substring(2)).toJson() + "\n");
            bw.flush();
            return;
        }
        if (s.startsWith("s ")) {
            System.out.println("[" + name + "]  traced rating by " + s.substring(2));
            bw.write("s " + new RatingInfo(s.substring(2)).toJson() + "\n");
            bw.flush();
            return;
        }
        if (s.startsWith("w ")) {
            PlayerInfo info = PlayerInfo.fromJson(s.substring(2));
            System.out.println("[" + name + "]  writed info about " + info.uuid);
            storage.add(info);
            return;
        }
        //TODO remove save
        if (s.equals("save")) {
            System.out.println("[" + name + "]  request save");
            storage.save();
        }
    }

    public void requestAllData() {
        try {
            bw.write("ar\n");
            bw.flush();
        } catch (Exception ignored) {
        }
    }
}
