package character.state;

import character.Character;

import javax.persistence.Entity;

@Entity
public class KnockedOut extends CharacterState {
    public CharacterState call(Character character) {
        if (character.getCurrentHp() > 0)
            return new Alive();

        return this;
    }
}
