package ckcsc.asadfgglie.pet;

import ckcsc.asadfgglie.Exception.WrongDirectionException;
import ckcsc.asadfgglie.main.Main;
import ckcsc.asadfgglie.pet.action.PetAction;
import ckcsc.asadfgglie.pet.action.Side;
import ckcsc.asadfgglie.pet.action.Stand;
import ckcsc.asadfgglie.pet.action.Walk;
import ckcsc.asadfgglie.pet.behavior.BehaviorContainer;
import ckcsc.asadfgglie.veiw.PetPanel;
import ckcsc.asadfgglie.veiw.PetWindow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

public class Pet extends Thread{
    private final Logger logger;

    public Side honSide = Side.Left;

    private int offsetX;
    private int offsetY;
    private boolean isPressed;

    private double speedX = 0;
    private double speedY = 0;

    public final String name;

    private final BehaviorContainer behaviorContainer;

    private final PetPanel panel;
    private final PetWindow window;

    public Pet (String name, BehaviorContainer behaviorContainer){
        this.name = name;
        this.behaviorContainer = behaviorContainer;
        logger = LoggerFactory.getLogger(this.getClass() + "-" + name);

        panel = new PetPanel(this);
        window = new PetWindow(this);

        window.addMouseListener(new MouseAdapter() {
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

        window.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged (MouseEvent e) {
                if(isPressed){
                    window.setLocation(e.getXOnScreen() - offsetX, e.getYOnScreen() - offsetY);
                    logger.debug("Dragged to (" + e.getXOnScreen() + ", " +e.getYOnScreen() + ")");
                }
            }
        });
    }

    public PetPanel getPanel (){
        return panel;
    }

    public PetWindow getWindow (){
        return window;
    }

    public static ArrayList<Pet> loadPets (String path, Logger logger) throws FileNotFoundException{
        File folder = new File(path);

        if(folder.isDirectory()){
            File[] petFolders = folder.listFiles(File::isDirectory);

            if(petFolders == null){
                throw new FileNotFoundException("I Need Directories for your each pet!\nEach your pet must have a folder to store!");
            }

            ArrayList<Pet> pets = new ArrayList<>();

            for (File petFolder : petFolders) {
                if (petFolder.getName().equals("exclude"))
                    continue;
                logger.info("load .../" + petFolder.getName());

                pets.add(loadPet(petFolder, logger));
            }

            return pets;
        }
        else {
            throw new FileNotFoundException("Need a Directory to find all your pets!\nYou must store all your pets' folder in a Directory!");
        }
    }

    public static Pet loadPet (File path, Logger logger) throws FileNotFoundException{
        File[] behaviorFolders = path.listFiles(File::isDirectory);

        if(behaviorFolders == null){
            throw new FileNotFoundException("There must be Directory of where there is behavior image in your pet's folder!");
        }

        BehaviorContainer behaviors = new BehaviorContainer();

        for (File behaviorFolder : behaviorFolders) {
            logger.info("load .../" + path.getName() + "/" + behaviorFolder.getName());

            File[] behaviorImages = behaviorFolder.listFiles((fileName -> fileName.getName().endsWith("png")));

            if (behaviorImages == null) {
                throw new FileNotFoundException("There must be PNG images in your pet's behavior Directory!");
            }

            Image[] imgs = new Image[behaviorImages.length];
            for (int j = 0; j < behaviorImages.length; j++) {
                logger.info("load .../" + path.getName() + "/" + behaviorFolder.getName() + "/" + behaviorImages[j].getName());

                imgs[j] = Toolkit.getDefaultToolkit().getImage(behaviorImages[j].getPath());
            }

            behaviors.addBehavior(behaviorFolder.getName().toUpperCase(), imgs);
        }
        return new Pet(path.getName(), behaviors);
    }

    public void setNowBehavior (PetAction action) throws WrongDirectionException {
        window.setAction(action);
        speedX = action.getSpeedX();
        speedY = action.getSpeedY();
    }

    @Override
    public void run () {
        new Thread(window, name + "-window").start();
        new Thread(() -> {
            logger.debug(name + "-ActionSwitch thread start");
            while (true) {
                try {
                    setNowBehavior(new Stand(this));
                    Thread.sleep(2000);
                }
                catch (InterruptedException | WrongDirectionException e) {
                    logger.error(e.getMessage());
                    e.printStackTrace();
                }

                try {
                    setNowBehavior(new Walk(this, (new Random().nextBoolean())? Side.Left.setSpeedX(-10) : Side.Right.setSpeedX(10)));
                    Thread.sleep(2000);
                }
                catch (InterruptedException | WrongDirectionException e) {
                    e.printStackTrace();
                }
            }
        }, name + "-ActionSwitch").start();
    }

    public void doMoveAction () {
        if (isPressed) return;

        logger.debug("speedX: " + speedX);
        logger.debug("x, y: " + window.getX() + ", " + window.getY());
        // X direction
        if((window.getX() >= 0 && window.getX() <= Main.SCREEN_SIZE_X - panel.getWidth()) && ((int) (window.getX() + speedX) <= Main.SCREEN_SIZE_X - panel.getWidth() && (int) (window.getX() + speedX) >= 0)){
            window.setLocation((int) (window.getX() + speedX), window.getY());
        }
        else if(window.getX() < 0){
            window.setLocation(0, window.getY());
        }
        else if(window.getX() > Main.SCREEN_SIZE_X - panel.getWidth()){
            window.setLocation(Main.SCREEN_SIZE_X - panel.getWidth(), window.getY());
        }

        // Y direction
        if(window.getY() < Main.SCREEN_SIZE_Y - Main.MAX_SIZE){

            speedY += Main.GRAVITY;
            window.setLocation(window.getX(), (int) (window.getY() + speedY));
        }
        else{
            window.setLocation(window.getX(), Main.SCREEN_SIZE_Y - Main.MAX_SIZE);
            speedY = 0;
        }
    }

    public double getSpeedX () {
        return speedX;
    }

    public double getSpeedY () {
        return speedY;
    }

    public void setSpeedX (double speedX) {
        this.speedX = speedX;
    }

    public void setOffsetY (int offsetY) {
        this.offsetY = offsetY;
    }

    public BehaviorContainer getBehaviorContainer () {
        return behaviorContainer;
    }
}
