package ckcsc.asadfgglie.util;

import java.awt.*;
import java.util.HashMap;

public class ActionImageContainer {
    /**
     * Record all action.
     */
    public static final HashMap<String, ActionList> ACTION_LIST = ActionList.valuesMap();

    /**
     * Store all image sorted by action.
     */
    private final HashMap<ActionList, Image[]> _actionMap;

    public ActionImageContainer (){
        _actionMap = new HashMap<>();
    }

    public void addAction (String actionName, Image[] images) {
        if(ACTION_LIST.containsKey(actionName.toUpperCase())) {
            _actionMap.put(ACTION_LIST.get(actionName), images);
        }
    }

    /**
     * Return image by action and index.
     * @return
     * Action image.
     * If the action doesn't have images, return default action <b>Stand</b>.
     */
    public Image getImage (ActionList action, int index) {
        Image[] images = _actionMap.get(action);
        if(images == null){
            return _actionMap.get(ActionList.STAND)[index % _actionMap.get(ActionList.STAND).length];
        }
        return images[index % _actionMap.get(action).length];
    }

    /**
     * Record all PetAction.
     * Each action should be declared at this enum by its simple class name.
     */
    public enum ActionList {
        STAND(), WALK(), JUMP();

        public static HashMap<String, ActionList> valuesMap(){
            HashMap<String, ActionList> tmp = new HashMap<>();
            for(ActionList b : ActionList.values()){
                tmp.put(b.name().toUpperCase(), b);
            }
            return tmp;
        }
    }
}
