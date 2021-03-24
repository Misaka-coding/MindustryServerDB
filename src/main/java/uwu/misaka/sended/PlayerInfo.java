package uwu.misaka.sended;

import java.util.ArrayList;

import static uwu.misaka.Ichi.gson;

public class PlayerInfo {
    public String uuid; //primary key
    public int blocksPlaced=0;
    public int blocksDestroyed=0;
    public long totalPlayedTime =0;
    public int pvpWins=0;
    public int hexedWins=0;
    public int attackWins=0;
    public int totalWaves=0;
    public long discordId=0;
    public String nick = "";
    public ArrayList<String> warnings = new ArrayList<>();

    public PlayerInfo(String uuid){
        this.uuid=uuid;
    }
    public static PlayerInfo fromJson(String json){
        return gson.fromJson(json, PlayerInfo.class);
    }
    public String toJson(){
        return gson.toJson(this);
    }
}
