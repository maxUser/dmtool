package backend;

import java.util.Comparator;

public class CharacterInitSortComparator implements Comparator<Character> {
    public int compare(Character c1, Character c2) {
        if (c1.getInitiative() < (c2.getInitiative())) {
            return 1;
        } else if (c1.getInitiative() > (c2.getInitiative())) {
            return -1;
        } else {
            return 0;
        }
    }
}