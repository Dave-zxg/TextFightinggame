package domain;

import java.util.ArrayList;

public class HeroCharacter extends Character {
    public ArrayList<String> skillList;

    public HeroCharacter() {
        this.skillList = new ArrayList<>();
    }

    public HeroCharacter(String name, int HP, int attack, int defense) {
        super(name, HP, attack, defense);
        this.skillList = new ArrayList<>();
    }
}
