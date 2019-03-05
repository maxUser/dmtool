package backend;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class EditJSON {

    private JSONArray jArray;
    private JSONObject jObj;
    private static final String jsonFile = "characters.json";

    public EditJSON() {
        jArray = new JSONArray();
        jObj = new JSONObject();
    }

    public void saveList(ArrayList<Character> masterList) throws IOException {
        JSONArray ja = new JSONArray();
        for (Character c : masterList) {
            JSONObject jo = new JSONObject();
            jo.put("name", c.getName());
            String type = c.getType().toString();
            jo.put("type", type);
            Integer ac = new Integer(c.getAC());
            jo.put("ac", ac);
            jo.put("description", c.getDescription());
            ja.add(jo);
        }
        Files.write(Paths.get(jsonFile), ja.toString().getBytes());
    }

    public void clearFile() {
        try (FileWriter file = new FileWriter("characters.json", false)) {
            file.write("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
