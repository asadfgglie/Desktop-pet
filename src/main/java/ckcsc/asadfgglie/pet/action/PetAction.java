package ckcsc.asadfgglie.pet.action;

import ckcsc.asadfgglie.pet.Pet;
import ckcsc.asadfgglie.pet.behavior.BehaviorContainer;

public abstract class PetAction {
    protected Pet pet;

    protected PetAction(Pet pet){
        this.pet = pet;
    }

    public BehaviorContainer.BehaviorList getBehavior(){
        return BehaviorContainer.BEHAVIOR_LIST_VALUES.get(getClass().getSimpleName().toUpperCase());
    }

    public int getActionImageTick () {
        return getBehavior().getActionTick();
    }

    public abstract long getActionTick ();
}
