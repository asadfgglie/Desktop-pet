package ckcsc.asadfgglie.pet.action;

import ckcsc.asadfgglie.Exception.WrongDirectionException;
import ckcsc.asadfgglie.main.Main;
import ckcsc.asadfgglie.pet.Pet;

public class Walk extends PetAction{
    public int actionTick = Main.FPS / 4;
    public Side side;

    public Walk (Pet pet, Side side) {
        super(pet);
        if(side == Side.Left || side == Side.Right){
            pet.honSide = side;
        }
        this.side = side;
    }

    @Override
    public int getActionTick () {
        return actionTick;
    }

    @Override
    public int getSpeedX () throws WrongDirectionException {
        return (int) (pet.getSpeedX() + side.getSpeedX());
    }
}
