package uwu.misaka;

import com.google.gson.Gson;
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
        storage=new Storage();
        new Server();
        timer.scheduleAtFixedRate(new UpdateStorage(),60000l,900000l);
    }
    public static class UpdateStorage extends TimerTask{
        @Override
        public void run() {
            System.out.println("[AUTO-SAVE]    request save");
            storage.save();
        }
    }
}