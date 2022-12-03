package ckcsc.asadfgglie.pet;

import ckcsc.asadfgglie.pet.action.Jump;
import ckcsc.asadfgglie.pet.action.PetAction;
import ckcsc.asadfgglie.pet.action.Stand;
import ckcsc.asadfgglie.pet.action.Walk;
import ckcsc.asadfgglie.util.ActionImageContainer;
import ckcsc.asadfgglie.util.SpeedVector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ActionHandle extends Thread {
    private final Pet _pet;
    private final Logger _logger;
    private volatile PetAction _nowAction;

    public ActionHandle (Pet pet) {
        this._pet = pet;
        _logger = LoggerFactory.getLogger(pet.name);
        this.setName("ActionHandle");
    }

    public void setNowAction (PetAction nowAction) {
        this._nowAction = nowAction;
    }

    public PetAction getNowAction () {
        return _nowAction;
    }

    @Override
    public void run () {
        List<ActionImageContainer.ActionList> actionLists = new java.util.ArrayList<>(List.of(ActionImageContainer.ActionList.values()));
        Random random = new Random(System.currentTimeMillis());

        while (true) {

            Collections.shuffle(actionLists);

            switch (actionLists.get(0)) {
                case STAND: {
                    _pet.startAction(new Stand(_pet, random.nextInt(10) * 1000));
                    break;
                }

                case WALK: {
                    _pet.startAction(new Walk(_pet, (random.nextBoolean()) ? new SpeedVector(-random.nextInt(10) - 1, 0) : new SpeedVector(random.nextInt(10) + 1,0), (random.nextInt(5) + 1) * 1000));
                    break;
                }

                case JUMP:{
                    _pet.startAction(new Jump(_pet, random.nextInt(100) + 1));
                    break;
                }
            }
        }
    }
}
