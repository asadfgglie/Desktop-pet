package ckcsc.asadfgglie.pet.action;

import ckcsc.asadfgglie.main.Main;
import ckcsc.asadfgglie.pet.Pet;
import ckcsc.asadfgglie.util.SpeedVector;

public class Stand extends PetAction{
    public int actionTick = Main.FPS;

    public Stand (Pet pet, int actionTime) {
        super(pet, actionTime, new SpeedVector(0, 0));
        pet.setSpeed(new SpeedVector(0, pet.getSpeed().getSpeedY()));
    }

    @Override
    public int getActionTick () {
        return actionTick;
    }
}
