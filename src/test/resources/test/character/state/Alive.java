package character.state;

import character.Character;

import javax.persistence.Entity;

@Entity
public class Alive extends CharacterState {
    public CharacterState call(Character character) {
        if (character.getCurrentHp() == 0)
            return new KnockedOut();

        return this;
    }
}
