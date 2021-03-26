package uwu.misaka.storage;

import uwu.misaka.sended.PlayerInfo;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class Storage {
    ArrayList<PlayerInfo> players = new ArrayList<>();
    File storageFile = new File("STORAGE.txt");

    public Storage() throws IOException {
        if (!storageFile.exists()) {
            storageFile.createNewFile();
        }
        BufferedReader reader = new BufferedReader(new FileReader(storageFile));
        while (true) {
            String s = reader.readLine();
            if (s != null) {
                players.add(PlayerInfo.fromJson(s));
            } else {
                break;
            }
        }
        System.out.println("[Storage]   Loaded " + players.size() + " player info");
    }

    public PlayerInfo get(String uuid) {
        for (PlayerInfo pi : players) {
            if (pi.uuid.equals(uuid)) return pi;
        }
        PlayerInfo p = new PlayerInfo(uuid);
        players.add(p);
        return p;
    }

    public void add(PlayerInfo info) {
        ArrayList<PlayerInfo> toRm= new ArrayList<>();
        for(PlayerInfo pi: players){if(pi.uuid.equals(info.uuid)){toRm.add(pi);}}
        toRm.forEach(p->players.remove(p));
        players.add(info);
    }

    public void save(){
        try {
            FileWriter fw = new FileWriter(storageFile, false);
            for (PlayerInfo pi : players) {
                fw.append(pi.toJson() + "\n");
            }
            fw.flush();
            System.out.println("[Stage]    Saved " + players.size());
        } catch (IOException e) {
            System.out.println("[Stage]    Saving error");
        }
    }

    public void sort() {
        Collections.sort(players, (a, b) -> {
            if (a.getXp() > b.getXp()) {
                return -1;
            } else {
                return 1;
            }
        });
    }

    public ArrayList<String> top10() {
        ArrayList<String> rtn = new ArrayList<>();
        int i = 1;
        for (PlayerInfo p : players) {
            if (!p.nick.equals("")) {
                rtn.add(i + " " + p.nick);
            } else {
                rtn.add(i + " " + p.loginName);
            }
            i++;
            if (i > 10) break;
        }
        return rtn;
    }

    public int userPlace(String user) {
        int i = 0;
        for (PlayerInfo p : players) {
            i++;
            if (p.uuid.equals(user)) {
                break;
            }
        }
        return i;
    }
}