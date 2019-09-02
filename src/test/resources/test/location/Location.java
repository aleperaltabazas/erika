package location;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Location {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private boolean rain;

    public String getName() {
        return name;
    }
}