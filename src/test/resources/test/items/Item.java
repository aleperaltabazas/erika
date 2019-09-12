package items;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public abstract class Item {
    @Id
    @GeneratedValue
    private Long id;

    private double price;

    public abstract void effectOn(Character character);
}