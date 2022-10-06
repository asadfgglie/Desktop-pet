package ckcsc.asadfgglie.veiw;

import ckcsc.asadfgglie.main.Main;
import ckcsc.asadfgglie.pet.Pet;
import ckcsc.asadfgglie.pet.behavior.BehaviorContainer;
import ckcsc.asadfgglie.pet.action.PetAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PetWindow extends JWindow implements Runnable{
    private final Pet pet;
    private final Logger logger;

    private int offsetX;
    private int offsetY;
    private boolean isPressed;

    private PetAction action;
    private int tick;

    public PetWindow (Pet pet) {
        this.pet = pet;
        tick = 0;
        logger = LoggerFactory.getLogger(pet.name + "-" + PetWindow.class.getSimpleName());

        setBackground(new Color(0, 0,0,0));
        getContentPane().add(pet.getPanel());
        setAlwaysOnTop(true);
        setLocation(960, 540);

        setSize(Main.MAX_SIZE, Main.MAX_SIZE);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed (MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    isPressed = true;
                    offsetX = e.getX();
                    offsetY = e.getY();
                    logger.debug(isPressed + ", (" + offsetX + ", " + offsetY + ")");
                }
            }

            @SuppressWarnings("ConstantConditions")
            @Override
            public void mouseReleased (MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    isPressed = false;
                    logger.debug(String.valueOf(isPressed));
                }
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged (MouseEvent e) {
                if(isPressed){
                    setLocation(e.getXOnScreen() - offsetX, e.getYOnScreen() - offsetY);
                    logger.debug("Dragged to (" + e.getXOnScreen() + ", " +e.getYOnScreen() + ")");
                }
            }
        });
    }

    public Image getTickImage(BehaviorContainer.BehaviorList behavior){
        return pet.getImage(behavior, tick);
    }

    public Image getTickImage(){
        return getTickImage(action.getBehavior());
    }

    public void setAction(PetAction action){
        this.action = action;
    }

    @Override
    public synchronized void run () {
        logger.debug(pet.name + "-window thread start");
        setVisible(true);

        while(true){
            /*
              use it to refresh the picture instead of panel.updateUI()
              and it has spent me over five hours to fix this "little bug"
             */
            repaint();

            tick = (tick + 1 < action.getActionImageTick()) ? tick + 1 : 0;
            try {
                wait((long) 1000 / Main.FPS * action.getActionTick());
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
