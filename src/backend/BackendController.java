package backend;

import frontend.CharacterDetailView;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class BackendController {
    private final Model model;
    private final EditJSON JSONctrlr;

    public BackendController(Model aModel) {
        model = aModel;
        JSONctrlr = new EditJSON();
    }

    public Character getCharacterFromNameCombat (String charName) {
        for (Character c : model.getCombatList()) {
            if (c.getName().equals(charName)) {
                return c;
            }
        }
        return null;
    }

    public ImageView getImage(String filePath) throws IOException {
        FileInputStream inputstream = new FileInputStream(filePath); //barf
        Image image = new Image(inputstream);
        ImageView aImage = new ImageView(image);
        aImage.setFitHeight(20);
        aImage.setPreserveRatio(true);
        return aImage;
    }

    public void doubleClickForDetails(ListView<String> aList) {
        aList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            // double click to view details
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                    if(mouseEvent.getClickCount() == 2){
                        try {
                            ObservableList<String> selected = aList.getSelectionModel().getSelectedItems();
                            CharacterDetailView.display(viewDetails(selected), new BackendController(model));
                        } catch (IOException x) {
                            x.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    public Character viewDetails(ObservableList<String> selected) {
        Character temp = new Character();
        if (selected.size() != 1) {
            return temp;
        }
        for (String listC : selected) {
            for (Character arrC : getMasterListCopy()) {
                if (listC.equals(arrC.getName())) {
                    temp = arrC;
                }
            }
        }
        return temp;
    }

    public void addCharacterToCombatList(Character c) {
        model.getCombatList().add(c);
    }

    public void removeCharacterFromCombatList(Character c) {
        model.getCombatList().remove(c);
    }

    public void populateCombatList(ArrayList<Character> combatants) {
        for (Character c : combatants) {
            addCharacterToCombatList(c);
        }
    }

    public void addCharacter(Character c) {
        model.getMasterList().add(c);
    }

    public void removeCharacter(Character c) {
        model.getMasterList().remove(c);
    }

    /**
     * Called upon program start up
     * It populates the masterList from data with a JSON file
     */
    public void populateMasterList() throws IOException, ParseException {
        File a = new File("characters.json");

        if (a.length() > 0) {
            // this code returns an error if the json file is empty
            Character c;
            JSONParser parser = new JSONParser();
            JSONArray ja = (JSONArray) parser.parse(new FileReader("characters.json"));
            for (int i = 0; i < ja.size(); i++) {
                JSONObject obj = (JSONObject) ja.get(i);
                if (obj.get("type").equals("PLAYER")) {
                    c = new Character(CharacterType.PLAYER);
                } else {
                    c = new Character(CharacterType.NONPLAYER);
                }
                c.setName((String) obj.get("name"));
                Long intAC = (Long)obj.get("ac");
                c.setAC(Math.toIntExact(intAC));
                addCharacter(c);
            }
        }
    }

    /**
     * Called upon program exit
     * Saves data from masterList into JSON file
     */
    public void saveMasterList() throws IOException {
        JSONctrlr.clearFile();
        JSONctrlr.saveList(getMasterListCopy());
    }

    /**
     * @return a copy of the masterList
     */
    public ArrayList<Character> getMasterListCopy() {
        ArrayList<Character> temp = new ArrayList<>();
        for (Character c : model.getMasterList()) {
            temp.add(c);
        }
        return temp;
    }

    /**
     * @return a copy of the combatList
     */
    public ArrayList<Character> getCombatListCopy() {
        ArrayList<Character> temp = new ArrayList<>();
        for (Character c : model.getCombatList()) {
            temp.add(c);
        }
        return temp;
    }

    /**
     * @return actual combat list.
     * This is required because I need to alter the real combat list
     */
    public ArrayList<Character> getCombatList() {
        return model.getCombatList();
    }

    public void printMasterList() {
        for (Character c : getMasterListCopy()) {
            System.out.println(c.getName() + "[" +c.getType() + "]");
        }
    }

    public Character makeTestCharacter(CharacterType type) {
        Character c = new Character(type);
        c.setName("TestCharacter");
        return c;
    }

    public void makeTestMasterList() {
        Character testChar;
        for (int i = 1; i < 11; i++) {
            if (i % 2 == 0) {
                testChar = makeTestCharacter(CharacterType.PLAYER);
            } else {
                testChar = makeTestCharacter(CharacterType.NONPLAYER);
            }
            testChar.setName("TestCharacter" + i);

            model.getMasterList().add(testChar);
        }
    }

}
