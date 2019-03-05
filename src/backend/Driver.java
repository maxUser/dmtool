package backend;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;

public class Driver {

    public static void main(String[] args) throws IOException, ParseException {
        Model m = new Model();
        BackendController bc = new BackendController(m);

        /**
         *  Add methods you would like to test
         */
//        addCharToList(bc);
//        printListToConsole(bc);
//        testJSON(bc);
        bc.populateMasterList();
        bc.printMasterList();
    }

    public static void addCharToList(BackendController c) {
        Character p1 = new Character(CharacterType.PLAYER);
        Character p2 = new Character(CharacterType.NONPLAYER);

        c.addCharacter(p1);
        c.addCharacter(p2);
        p1.setName("Maxi");
        p2.setName("Leah");
    }

    public static void printListToConsole(BackendController bc) {
        bc.printMasterList();
    }

    public static void testJSON(BackendController bc) throws IOException {
        bc.makeTestMasterList();
        bc.saveMasterList();
    }
}
