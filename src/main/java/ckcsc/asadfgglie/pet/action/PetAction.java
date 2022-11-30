package ckcsc.asadfgglie.pet.action;

import ckcsc.asadfgglie.Exception.WrongDirectionException;
import ckcsc.asadfgglie.pet.Pet;

public abstract class PetAction {
    Pet pet;
    PetAction(Pet pet){
        this.pet = pet;
    }

    public ActionContainer.ActionList getBehavior (){
        return ActionContainer.ACTION_LIST_VALUES.get(getClass().getSimpleName().toUpperCase());
    }

    /** Return <b>EACH IMAGE</b> tick **/
    public abstract int getActionTick();

    public int getSpeedX() throws WrongDirectionException {
        return 0;
    }

    public int getSpeedY(){
        return 0;
    }
}
