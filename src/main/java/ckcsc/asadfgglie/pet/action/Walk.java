package ckcsc.asadfgglie.pet.action;

import ckcsc.asadfgglie.pet.Pet;

public class Walk extends PetAction{
    public Walk (Pet pet) {
        super(pet);
    }

    @Override
    public long getActionTick () {
        return 10;
    }
}
