package ckcsc.asadfgglie.pet.action;

import ckcsc.asadfgglie.main.Main;
import ckcsc.asadfgglie.pet.Pet;
import ckcsc.asadfgglie.util.SpeedVector;

public class Walk extends PetAction{
    public int actionTick = Main.FPS / 4;

    public Walk (Pet pet, SpeedVector speedVector, int actionTime) {
        super(pet, actionTime, speedVector);
    }

    @Override
    public int getActionTick () {
        return actionTick;
    }

}
