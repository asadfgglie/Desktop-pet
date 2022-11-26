package ckcsc.asadfgglie.pet.action;

import ckcsc.asadfgglie.Exception.WrongDirectionException;
import ckcsc.asadfgglie.pet.Pet;
import ckcsc.asadfgglie.pet.behavior.BehaviorContainer;

public abstract class PetAction {
    Pet pet;
    PetAction(Pet pet){
        this.pet = pet;
    }

    public BehaviorContainer.BehaviorList getBehavior (){
        return BehaviorContainer.BEHAVIOR_LIST_VALUES.get(getClass().getSimpleName().toUpperCase());
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
