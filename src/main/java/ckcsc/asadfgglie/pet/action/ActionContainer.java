package ckcsc.asadfgglie.pet.action;

import java.awt.*;
import java.util.HashMap;

public class ActionContainer {

    public static final HashMap<String, ActionList> ACTION_LIST_VALUES = ActionList.valuesMap();

    private final HashMap<ActionList, Image[]> actionMap;

    public ActionContainer (){
        actionMap = new HashMap<>();
    }

    public void addAction (String actionName, Image[] images) {
        if(ACTION_LIST_VALUES.containsKey(actionName.toUpperCase())) {
            actionMap.put(ACTION_LIST_VALUES.get(actionName), images);
            ACTION_LIST_VALUES.get(actionName.toUpperCase()).actionTick = images.length;
        }
    }

    public Image getImage (ActionList action, int index) {
        return actionMap.get(action)[index % actionMap.get(action).length];
    }

    public enum ActionList {
        STAND(), WALK();

        /** Whole action tick **/
        int actionTick;

        public static HashMap<String, ActionList> valuesMap(){
            HashMap<String, ActionList> tmp = new HashMap<>();
            for(ActionList b : ActionList.values()){
                tmp.put(b.name(), b);
            }
            return tmp;
        }
    }
}
