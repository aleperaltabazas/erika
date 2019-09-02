package monster.state;

import monster.Monster;

public abstract class MonsterState {
    public abstract void rest(Monster monster);

    public abstract void takeDamage(double damage, Monster monster);

    public abstract void takeAction(Monster monster);
}