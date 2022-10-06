package ckcsc.asadfgglie.pet.behavior;

import ckcsc.asadfgglie.pet.action.PetAction;
import ckcsc.asadfgglie.pet.action.Stand;
import ckcsc.asadfgglie.pet.action.Walk;
import org.slf4j.Logger;

import java.awt.*;
import java.util.HashMap;

public class BehaviorContainer {
    private Logger logger;

    public static final HashMap<String, BehaviorList> BEHAVIOR_LIST_VALUES = BehaviorList.valuesMap();

    private final HashMap<BehaviorList, Image[]> behaviorMap;

    public BehaviorContainer(){
        behaviorMap = new HashMap<>();
    }

    public void setLogger(Logger logger){
        this.logger = logger;
    }

    public void addBehavior (String behavior, Image[] images) {
        if(BEHAVIOR_LIST_VALUES.containsKey(behavior.toUpperCase())) {
            behaviorMap.put(BEHAVIOR_LIST_VALUES.get(behavior), images);
            BEHAVIOR_LIST_VALUES.get(behavior.toUpperCase()).actionTick = images.length;
        }
    }

    public Image getImage (BehaviorList behavior, int index) {
        return behaviorMap.get(behavior)[index];
    }

    public enum BehaviorList{
        STAND(Stand.class), WALK(Walk.class);

        Class<? extends PetAction> clazz;
        int actionTick;

        BehaviorList (Class<? extends PetAction> clazz) {
            this.clazz = clazz;
        }

        public int getActionTick () {
            return actionTick;
        }

        public static HashMap<String, BehaviorList> valuesMap(){
            HashMap<String, BehaviorList> tmp = new HashMap<>();
            for(BehaviorList b : BehaviorList.values()){
                tmp.put(b.name(), b);
            }
            return tmp;
        }
    }
}
