package ckcsc.asadfgglie.pet.action;

import ckcsc.asadfgglie.pet.Pet;
import ckcsc.asadfgglie.util.ActionImageContainer;
import ckcsc.asadfgglie.util.SpeedVector;

public abstract class PetAction {
    protected Pet pet;
    protected int actionTime;
    protected SpeedVector speedVector;

    PetAction(Pet pet, int actionTime, SpeedVector speedVector){
        this.pet = pet;
        this.actionTime = actionTime;
        this.speedVector = speedVector;
        pet.setSpeed(SpeedVector.add(pet.getSpeed(), speedVector));
    }

    public ActionImageContainer.ActionList getAction (){
        return ActionImageContainer.ACTION_LIST.get(getClass().getSimpleName().toUpperCase());
    }

    public SpeedVector getSpeedVector () {
        return speedVector;
    }

    /** Return <b>EACH IMAGE</b> tick **/
    public abstract int getActionTick();

    public int getActionTime () {
        return actionTime;
    }
}
