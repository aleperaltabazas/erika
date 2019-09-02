package monster.state;

import monster.Monster;

public class Awake extends MonsterState {
    public void rest(Monster monster) {
        monster.setMonsterState(new Resting(3));
    }

    public void takeDamage(double damage, Monster monster) {
        monster.loseHp(damage);
    }

    public void takeAction(Monster monster) {
        monster.powerUp(5);
    }
}
