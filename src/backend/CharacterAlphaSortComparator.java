package backend;

import java.util.Comparator;

public class CharacterAlphaSortComparator implements Comparator<Character> {
    public int compare(Character c1, Character c2) {
        if (c1.getName().compareTo(c2.getName()) > 0) {
            return 1;
        } else if (c1.getName().compareTo(c2.getName()) < 0) {
            return -1;
        } else {
            return 0;
        }
    }
}
