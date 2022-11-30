package ckcsc.asadfgglie.pet.action;

import ckcsc.asadfgglie.Exception.WrongDirectionException;
import ckcsc.asadfgglie.main.Main;
import ckcsc.asadfgglie.pet.Pet;

public class Walk extends PetAction{
    public int actionTick = Main.FPS / 4;
    public SpeedVector speedVector;

    public Walk (Pet pet, SpeedVector speedVector) {
        super(pet);
        if(speedVector == SpeedVector.Left || speedVector == SpeedVector.Right){
            pet.honSpeedVector = speedVector;
        }
        this.speedVector = speedVector;
    }

    @Override
    public int getActionTick () {
        return actionTick;
    }

    @Override
    public int getSpeedX () throws WrongDirectionException {
        return (int) (pet.getSpeedX() + speedVector.getSpeedX());
    }
}
