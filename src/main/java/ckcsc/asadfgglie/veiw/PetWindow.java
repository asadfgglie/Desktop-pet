package ckcsc.asadfgglie.veiw;

import ckcsc.asadfgglie.main.Main;
import ckcsc.asadfgglie.pet.Pet;
import ckcsc.asadfgglie.pet.action.PetAction;
import ckcsc.asadfgglie.pet.behavior.BehaviorContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;

import static ckcsc.asadfgglie.main.Main.FPS;

public class PetWindow extends JWindow implements Runnable{
    private final Pet pet;
    private final Logger logger;

    private PetAction action;
    private int actionTick = 0;

    public PetWindow (Pet pet) {
        this.pet = pet;
        logger = LoggerFactory.getLogger(pet.name + "-" + PetWindow.class.getSimpleName());

        setBackground(new Color(0, 0,0,0));
        getContentPane().add(pet.getPanel());
        setAlwaysOnTop(true);
        setLocation(Main.SCREEN_SIZE_X / 2, 0);

        setSize(Main.MAX_SIZE, Main.MAX_SIZE);
    }

    public Image getTickImage(BehaviorContainer.BehaviorList behavior){
        return pet.getBehaviorContainer().getImage(behavior, actionTick);
    }

    public Image getTickImage(){
        return getTickImage(action.getBehavior());
    }

    public void setAction(PetAction action){
        this.action = action;
        logger.debug("Change action to: " + action.getBehavior().name());
        actionTick = 0;
    }

    @Override
    public synchronized void run () {
        logger.debug(pet.name + "-window thread start");
        setVisible(true);
        int realTick = 0;

        while(true){
            /*
              use it to refresh the picture instead of panel.updateUI()
              and it has spent me over five hours to fix this "little bug"
             */
            repaint();

            pet.doMoveAction();

            realTick++;
            if(realTick % Integer.MAX_VALUE == 0){
                realTick = 0;
            }

            if(realTick % action.getActionTick() == 0) {
                actionTick++;
            }

            try {
                wait((long) 1000 / FPS);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
