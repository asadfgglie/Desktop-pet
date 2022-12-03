package ckcsc.asadfgglie.pet.action;

import ckcsc.asadfgglie.main.Main;
import ckcsc.asadfgglie.pet.Pet;
import ckcsc.asadfgglie.util.SpeedVector;

public class Jump extends PetAction{
    public int actionTick = Main.FPS;

    public Jump (Pet pet, int height) {
        super(pet, (int) Math.sqrt(8 * height / Main.GRAVITY.getSpeedY()), new SpeedVector(0, -Math.sqrt(2 * Main.GRAVITY.getSpeedY() * height)));
    }

    @Override
    public int getActionTick () {
        return actionTick;
    }
}
