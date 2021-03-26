package uwu.misaka.sended;

import uwu.misaka.Ichi;

import java.util.ArrayList;

import static uwu.misaka.Ichi.gson;

public class RatingInfo {
    public String rId;
    public int userPlace;
    public ArrayList<String> players;

    public RatingInfo(String pId) {
        rId = pId;
        players = Ichi.storage.top10();
        userPlace = Ichi.storage.userPlace(pId);
    }

    public static RatingInfo fromJson(String json) {
        return gson.fromJson(json, RatingInfo.class);
    }

    public String toJson() {
        return gson.toJson(this);
    }
}
