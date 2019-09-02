package character;

public class Level {
    private int number = 1;
    private double currentExp;
    private double expForNextLevel;

    public void gainExp(double exp, Character character) {
        double totalExp = currentExp + exp;

        if (totalExp > expForNextLevel) {
            character.setMaxHp(character.getMaxHp() * 1.01);
            character.setCurrentHp(character.getCurrentHp() * 1.01);
            number += 1;
            currentExp = totalExp - expForNextLevel;
            expForNextLevel *= 2;
        } else {
            currentExp += exp;
        }
    }
}