package character.state;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class CharacterState {
    @Id
    @GeneratedValue
    private Long id;

    public abstract CharacterState call(Character character);
}