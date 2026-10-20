package domain;

public class EnemyCharacter extends Character {
    public String skill;
    public boolean defending;

    public EnemyCharacter() {
    }

    public EnemyCharacter(String name, int HP, int attack, int defense, String skill) {
        super(name, HP, attack, defense);
        this.skill = skill;
    }

    @Override
    public void takeDamage(int amount) {
        //如果正在防御，伤害减半
        if(defending) {
            amount = amount>1?amount/2:1;
            defending = false;
        }
        super.takeDamage(amount);
    }
}
