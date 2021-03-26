package uwu.misaka;

import com.google.gson.Gson;
import uwu.misaka.net.MindustryServer;
import uwu.misaka.net.Server;
import uwu.misaka.storage.Storage;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class Ichi {
    public static Gson gson = new Gson();
    public static Storage storage;
    public static Timer timer = new Timer();

    public static void main(String[] s) throws IOException {
        storage = new Storage();
        new Server();

        timer.scheduleAtFixedRate(new UpdateInfo(), 40000l, 300000l);
        timer.scheduleAtFixedRate(new UpdateRating(), 50000l, 300000l);
        timer.scheduleAtFixedRate(new UpdateStorage(), 60000l, 300000l);
    }

    public static class UpdateStorage extends TimerTask {
        @Override
        public void run() {
            System.out.println("[AUTO-SAVE]    request save");
            storage.save();
        }
    }

    public static class UpdateInfo extends TimerTask {
        @Override
        public void run() {
            System.out.println("[UPDATE-INFO]    request info");
            for (MindustryServer s : Server.serversConnector) {
                s.requestAllData();
            }
            System.out.println("[UPDATE-INFO]    request complete");
        }
    }

    public static class UpdateRating extends TimerTask {
        @Override
        public void run() {
            System.out.println("[UPDATE-RATING]    sorting started");
            storage.sort();
            System.out.println("[UPDATE-RATING]    sorting complete");
        }
    }

}