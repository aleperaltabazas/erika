package monster.state;

import monster.Monster;

public class Resting extends MonsterState {
    private int turns;

    public Resting(int turns) {
        this.turns = turns;
    }

    public void rest(Monster monster) {
        monster.gainHp(10);
    }

    public void takeDamage(double damage, Monster monster) {
        monster.takeDamage(damage * 2);
        monster.setMonsterState(new Awake());
    }

    public void takeAction(Monster monster) {
        turns--;

        if (turns == 0)
            monster.setMonsterState(new Awake());
    }
}