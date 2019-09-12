package weapons;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Weapon {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private double sellPrice;
    private double damage;

    public void apply(Character character) {
        character.loseHp(damage);
    }
}