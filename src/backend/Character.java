package backend;

public class Character {
    private String name;
    private CharacterType type;
    private int AC;
    private int initiative;
    private String description;
    private boolean isDuplicate;

    public Character(CharacterType aType) {
        type = aType;
        name = "Unnamed";
        AC = 0;
        initiative = 0;
    }

    public Character() {
        // For temp characters
        name = "Temporary";
        isDuplicate = true;
    }

    public Character(Character c) {
        // copy constructor
        type = c.getType();
        name = c.getName();
        AC = c.getAC();
        initiative = 0;
    }

    public boolean getIsDuplicate() {
        return isDuplicate;
    }

    public void setName(String aName) {
        name = aName;
    }

    public String getName() {
        return name;
    }

    public CharacterType getType() {
        return type;
    }

    public void setAC(int pAC) { AC = pAC; }

    public int getAC() { return AC; }

    public int getInitiative() {
        return initiative;
    }

    public void setInitiative(int aInitiative) {
        initiative = aInitiative;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String aDescription) {
        description = aDescription;
    }
}
