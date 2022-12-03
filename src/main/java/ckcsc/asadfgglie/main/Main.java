package ckcsc.asadfgglie.main;

import ckcsc.asadfgglie.pet.Pet;
import ckcsc.asadfgglie.pet.action.Jump;
import ckcsc.asadfgglie.util.SpeedVector;
import ckcsc.asadfgglie.pet.action.Stand;
import ckcsc.asadfgglie.pet.action.Walk;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {
    public static final int FPS = 60;
    public static final int MAX_SIZE = 200;
    public static final int SCREEN_SIZE_X = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    public static final int SCREEN_SIZE_Y = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() - Toolkit.getDefaultToolkit().getScreenInsets(new JFrame().getGraphicsConfiguration()).bottom;

    public static final SpeedVector GRAVITY = new SpeedVector(0, 30.0 / FPS);

    public static ArrayList<Pet> PETS;

    public static boolean INIT_DONE = false;

    /** hit box */
    public static boolean DEBUG = false;

    private static final Logger _logger = LoggerFactory.getLogger(Main.class);

    private static final Thread _commandThread = new Thread(Main.class.getSimpleName() + "-command"){
        @Override
        public void run () {
            while (true){
                try {
                    String command = new Scanner(System.in).nextLine();
                    if (command.equalsIgnoreCase("stop")) {
                        System.exit(0);
                    }
                    if(command.equalsIgnoreCase("walk")){
                        PETS.get(0).startAction(new Walk(PETS.get(0), new SpeedVector(5, 0), 2000));
                    }
                    if(command.equalsIgnoreCase("stand")){
                        PETS.get(0).startAction(new Stand(PETS.get(0), 1000));
                    }
                    if(command.equalsIgnoreCase("leftTest")){
                        PETS.get(0).getWindow().setLocation(-10, SCREEN_SIZE_Y);
                    }
                    if (command.equalsIgnoreCase("rightTest")) {
                        PETS.get(0).getWindow().setLocation(SCREEN_SIZE_X + 10, SCREEN_SIZE_Y);
                    }
                    if(command.equalsIgnoreCase("jump")){
                        PETS.get(0).startAction(new Jump(PETS.get(0), 500));
                    }
                }
                catch (ArrayIndexOutOfBoundsException | NoSuchElementException ignore){}
            }
        }
    };

    public static void main (String[] args) throws Exception {
        PETS = Pet.loadPets(args[0], _logger);

        try {
            if (args[1].toLowerCase(Locale.ROOT).equals("debug")) {
                DEBUG = true;
            }
        }
        catch (Exception ignore) {}

        _logger.info("Screen size: " + SCREEN_SIZE_X + ", " + SCREEN_SIZE_Y);
        _logger.info("Resources have loaded.");

        for(Pet pet : PETS){
            pet.generate();
            _logger.info("Generate " + pet.name);
            Thread.sleep(1000);
        }

        INIT_DONE = true;

        _commandThread.start();
    }


}
