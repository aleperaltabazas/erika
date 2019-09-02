package character;

import character.state.CharacterState;
import items.*;
import location.Location;
import weapons.Weapon;

import javax.persistence.*;

@Entity
public class Character {
    @Id
    @GeneratedValue
    private Long id;

    private double maxHp;
    private double currentHp;

    private Level level;

    @ManyToMany
    private Location currentLocation;

    @OneToOne
    private CharacterState characterState;

    @OneToOne
    private Inventory inventory;

    @OneToOne
    private Weapon equippedWeapon;

    public void gainHp(double gain) {
        this.currentHp += gain;
        characterState = characterState.call(this);
    }

    public void loseHp(double damage) {
        this.currentHp = Math.max(0, currentHp - damage);
        characterState = characterState.call(this);
    }

    public int getCurrentHp() {
        return 0;
    }

    public double getMaxHp() {
        return maxHp;
    }

    public void gainExp(double exp) {
        level.gainExp(exp, this);
    }

    public void setMaxHp(double maxHp) {
        this.maxHp = maxHp;
    }

    public void setCurrentHp(double currentHp) {
        this.currentHp = currentHp;
    }
}