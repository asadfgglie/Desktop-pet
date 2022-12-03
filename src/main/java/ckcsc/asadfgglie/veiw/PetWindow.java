package ckcsc.asadfgglie.veiw;

import ckcsc.asadfgglie.main.Main;
import ckcsc.asadfgglie.pet.Pet;
import ckcsc.asadfgglie.pet.action.PetAction;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;

import static ckcsc.asadfgglie.main.Main.FPS;

public class PetWindow extends JWindow implements Runnable{
    private final Pet _pet;
    private final Logger _logger;

    private PetAction _action;
    private int _actionTick = 0;

    public PetWindow (@NotNull Pet pet) {
        this._pet = pet;
        _logger = LoggerFactory.getLogger(pet.name + "-" + PetWindow.class.getSimpleName());

        setBackground(new Color(0, 0,0,0));
        getContentPane().add(pet.getPanel());
        setAlwaysOnTop(true);
        setLocation(Main.SCREEN_SIZE_X / 2, 0);

        setSize(Main.MAX_SIZE, Main.MAX_SIZE);
    }

    /**
     * Return image by action and tick.
     * @return
     * Action image.
     * If the action doesn't have images, return default action <b>Stand</b>.
     */
    public Image getTickImage(){
        return _pet.getActionImageContainer().getImage(_action.getAction(), _actionTick);
    }

    public void setAction(PetAction action){
        this._action = action;
        _actionTick = 0;
    }

    @Override
    public synchronized void run () {
        _logger.debug(_pet.name + "-window thread start");
        setVisible(true);
        int realTick = 0;

        while(true){
            /*
              use it to refresh the picture instead of panel.updateUI()
              and it has spent me over five hours to fix this "little bug"
             */
            repaint();
            _pet.setFalling(Main.SCREEN_SIZE_Y - Main.MAX_SIZE > getY());
            _pet.doMoveAction();

            realTick++;
            if(realTick % Integer.MAX_VALUE == 0){
                realTick = 0;
            }

            if(realTick % _action.getActionTick() == 0) {
                _actionTick++;
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
