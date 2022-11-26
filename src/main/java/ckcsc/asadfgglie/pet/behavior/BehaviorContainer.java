package ckcsc.asadfgglie.pet.behavior;

import java.awt.*;
import java.util.HashMap;

public class BehaviorContainer {

    public static final HashMap<String, BehaviorList> BEHAVIOR_LIST_VALUES = BehaviorList.valuesMap();

    private final HashMap<BehaviorList, Image[]> behaviorMap;

    public BehaviorContainer(){
        behaviorMap = new HashMap<>();
    }

    public void addBehavior (String behavior, Image[] images) {
        if(BEHAVIOR_LIST_VALUES.containsKey(behavior.toUpperCase())) {
            behaviorMap.put(BEHAVIOR_LIST_VALUES.get(behavior), images);
            BEHAVIOR_LIST_VALUES.get(behavior.toUpperCase()).actionTick = images.length;
        }
    }

    public Image getImage (BehaviorList behavior, int index) {
        return behaviorMap.get(behavior)[index % behaviorMap.get(behavior).length];
    }

    public enum BehaviorList{
        STAND(), WALK();

        /** Whole action tick **/
        int actionTick;

        public static HashMap<String, BehaviorList> valuesMap(){
            HashMap<String, BehaviorList> tmp = new HashMap<>();
            for(BehaviorList b : BehaviorList.values()){
                tmp.put(b.name(), b);
            }
            return tmp;
        }
    }
}
