package monster;

import monster.state.MonsterState;

public class Monster {
    private double currentHp;
    private double expYield;
    private double attackPower;
    private double defensePower;
    private double attackCadence;
    private MonsterState monsterState;

    public void attack(Character character) {
        character.loseHp(attackPower);
    }

    public void rest() {
        monsterState.rest(this);
    }

    public double getCurrentHp() {
        return currentHp;
    }

    public double getExpYield() {
        return expYield;
    }

    public double getAttackPower() {
        return attackPower;
    }

    public double getDefensePower() {
        return defensePower;
    }

    public double getAttackCadence() {
        return attackCadence;
    }

    public void gainHp(double someHp) {
        currentHp += someHp;
    }

    public void setMonsterState(MonsterState monsterState) {
        this.monsterState = monsterState;
    }

    public void takeDamage(double damage) {
        monsterState.takeDamage(damage, this);
    }

    public void loseHp(double someHp) {
        currentHp -= someHp;
    }

    public void takeAction() {
        monsterState.takeAction(this);
    }

    public void powerUp(double percentage) {
        this.attackPower += 1 + percentage / 100;
    }
}