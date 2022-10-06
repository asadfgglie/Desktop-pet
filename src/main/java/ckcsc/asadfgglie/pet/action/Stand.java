package ckcsc.asadfgglie.pet.action;

import ckcsc.asadfgglie.pet.Pet;

public class Stand extends PetAction{
    public Stand (Pet pet) {
        super(pet);
    }

    @Override
    public long getActionTick () {
        return 1;
    }
}
