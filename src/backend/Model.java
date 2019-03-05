package backend;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Model {
    private ArrayList<Character> masterList;
    private ArrayList<Character> combatList;

    public Model() {
        masterList = new ArrayList<>();
        combatList = new ArrayList<>();
    }

    public ArrayList<Character> getMasterList() { return masterList; }
    public ArrayList<Character> getCombatList() { return combatList; }

}
