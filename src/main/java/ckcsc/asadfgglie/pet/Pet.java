package ckcsc.asadfgglie.pet;

import ckcsc.asadfgglie.main.Main;
import ckcsc.asadfgglie.pet.action.*;
import ckcsc.asadfgglie.util.ActionImageContainer;
import ckcsc.asadfgglie.util.SpeedVector;
import ckcsc.asadfgglie.veiw.PetPanel;
import ckcsc.asadfgglie.veiw.PetWindow;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Pet{
    private final Pet _this;
    private final Logger _logger;

    private int _offsetX;
    private int _offsetY;
    private boolean _isPressed;
    private boolean _isFalling;

    private SpeedVector _speed = new SpeedVector(0, 0);

    public final String name;

    private final ActionImageContainer _actionImageContainer;

    private final PetPanel _panel;
    private final PetWindow _window;
    private final Thread _petWindowThread;
    private final ActionHandle _petActionHandle;

    private Pet (String name, ActionImageContainer actionImageContainer){
        _this = this;
        this.name = name;
        this._actionImageContainer = actionImageContainer;
        _logger = LoggerFactory.getLogger(this.getClass() + "-" + name);
        _isFalling = false;

        _panel = new PetPanel(this);
        _window = new PetWindow(this);
        _window.setAction(new Stand(this, 0));

        _petWindowThread = new Thread(_window, name + "-window");
        _petActionHandle = new ActionHandle(this);

        _window.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed (MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    _isPressed = true;
                    _offsetX = e.getX();
                    _offsetY = e.getY();
                    _logger.debug("Dragged from (" + e.getXOnScreen() + ", " +e.getYOnScreen() + ")");
                }
            }

            @Override
            public void mouseReleased (MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    _isPressed = false;
                    _logger.debug("Dragged to (" + e.getXOnScreen() + ", " +e.getYOnScreen() + ")");
                    startAction(new Stand(_this,0));
                }
            }
        });

        _window.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged (MouseEvent e) {
                if(_isPressed){
                    _window.setLocation(e.getXOnScreen() - _offsetX, e.getYOnScreen() - _offsetY);
                }
            }
        });
    }

    public PetPanel getPanel (){
        return _panel;
    }

    public PetWindow getWindow (){
        return _window;
    }

    /**
     * Load a bunch of pets from path.
     * Each pet should be separated by folder.
     * @param path
     * pets' folder
     * @return an array loaded pets
     * @throws FileNotFoundException
     * If there is no pet in path.
     */
    public static @NotNull ArrayList<Pet> loadPets (String path, Logger logger) throws FileNotFoundException{
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

    /**
     * Load a pet from pet's folder.
     * @param path
     * The folder which include pet's action's image.
     * All image will be loaded into actionImageContainer.
     * @return A pet with name, actionImage
     * @throws FileNotFoundException
     * If no action folder in pet's folder.
     * If no action image in pet's action folder.
     */
    @Contract("_, _ -> new")
    public static @NotNull Pet loadPet (@NotNull File path, Logger logger) throws FileNotFoundException{
        File[] actionImageFolders = path.listFiles(File::isDirectory);

        if(actionImageFolders == null){
            throw new FileNotFoundException("There must be Directory of where there is action image in your pet's folder!");
        }

        ActionImageContainer actions = new ActionImageContainer();

        for (File actionFolder : actionImageFolders) {
            logger.info("load .../" + path.getName() + "/" + actionFolder.getName());

            File[] actionImages = actionFolder.listFiles((fileName -> fileName.getName().endsWith("png")));

            if (actionImages == null) {
                throw new FileNotFoundException("There must be PNG images in your pet's action Directory!");
            }

            Image[] imgs = new Image[actionImages.length];
            for (int j = 0; j < actionImages.length; j++) {
                logger.info("load .../" + path.getName() + "/" + actionFolder.getName() + "/" + actionImages[j].getName());

                imgs[j] = Toolkit.getDefaultToolkit().getImage(actionImages[j].getPath());
            }

            actions.addAction(actionFolder.getName().toUpperCase(), imgs);
        }
        return new Pet(path.getName(), actions);
    }


    /**
     * Make the pet do action.
     * @param action
     * Except <b>Stand</b> action's actionTime can be 0, other action's actionTime can not be 0.
     * Or it will stick the action thread.
     */
    public synchronized void startAction(PetAction action){
        this.notify();

        if(_isFalling && action instanceof Jump){
            do{
                try {
                    wait(50);
                }
                catch (InterruptedException ignore) {}
            }while (_isFalling);
        }

        if(_isFalling) {
            _speed.setSpeedX(_speed.getSpeedX() - action.getSpeedVector().getSpeedX());
            action.getSpeedVector().setSpeedX(0);
        }

        _petActionHandle.setNowAction(action);
        _window.setAction(action);
        if(action.getActionTime() == 0 && action instanceof Stand){
            return;
        }

        _logger.debug("action: " + action.getAction().name());

        try {
            wait(action.getActionTime());
            if(_petActionHandle.getNowAction() == action) {
                startAction(new Stand(this,0));
            }
        }
        catch (InterruptedException ignore) {}
    }

    public void setFalling (boolean falling) {
        _isFalling = falling;
    }

    public ActionHandle getPetActionHandle () {
        return _petActionHandle;
    }

    public void doMoveAction () {
        if (_isPressed) return;

        if (Main.SCREEN_SIZE_Y - Main.MAX_SIZE > _window.getY()) {
            _speed = SpeedVector.add(_speed, Main.GRAVITY);
        }
        else if(Main.SCREEN_SIZE_Y - Main.MAX_SIZE < _window.getY()){
            _window.setLocation(_window.getX(), Main.SCREEN_SIZE_Y - Main.MAX_SIZE);
            _speed.setSpeedY(0);
        }


        if ((int) (_window.getX() + _speed.getSpeedX()) <= Main.SCREEN_SIZE_X - _panel.getWidth() &&
                (int) (_window.getX() + _speed.getSpeedX()) >= 0) {
            _window.setLocation((int) (_window.getX() + _speed.getSpeedX()), (int) (_window.getY() + _speed.getSpeedY()));
        }

        if(_window.getX() < 0){
            _window.setLocation(0, (int) (_window.getY() + _speed.getSpeedY()));
        }
        if(_window.getX() > Main.SCREEN_SIZE_X - _panel.getWidth()){
            _window.setLocation(Main.SCREEN_SIZE_X - _panel.getWidth(), (int) (_window.getY() + _speed.getSpeedY()));
        }
    }

    public SpeedVector getSpeed () {
        return _speed;
    }

    public void setSpeed (SpeedVector speed) {
        this._speed = speed;
    }

    public ActionImageContainer getActionImageContainer () {
        return _actionImageContainer;
    }

    /**
     * Start all pet's thread and generate the pet.
     */
    public void generate () {
        _petActionHandle.start();
        _petWindowThread.start();
    }
}
