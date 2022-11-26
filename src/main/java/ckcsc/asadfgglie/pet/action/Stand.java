package ckcsc.asadfgglie.pet.action;

import ckcsc.asadfgglie.main.Main;
import ckcsc.asadfgglie.pet.Pet;

public class Stand extends PetAction{
    public int actionTick = Main.FPS;
    public Stand (Pet pet) {
        super(pet);
    }

    @Override
    public int getActionTick () {
        return actionTick;
    }
}
