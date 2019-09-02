package items;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Inventory {
    @Id
    @GeneratedValue
    private Long id;

    @OneToMany
    private List<Item> items;
}