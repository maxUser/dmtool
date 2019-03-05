package backend;

import java.util.ArrayList;

public class TieGroup {

    private int initValue;
    private ArrayList<String> characters;

    public TieGroup(int init) {
        initValue = init;
        characters = new ArrayList<>();
    }

    public int getInitValue() {
        return initValue;
    }

    public void addCharacter(String name) {
        characters.add(name);
    }

    public void printTieGroup() {
        System.out.println("init: " + initValue);
        for (String s : characters) {
            System.out.print(s + " ");
        }
        System.out.println();
    }

//    public String toString() {
//        return
//    }

}
