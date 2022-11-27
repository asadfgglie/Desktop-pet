package ckcsc.asadfgglie.main;

import ckcsc.asadfgglie.pet.Pet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Locale;

public class Main {
    public static final int FPS = 60;
    public static final int MAX_SIZE = 200;
    public static final int SCREEN_SIZE_X = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    public static final int SCREEN_SIZE_Y = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() - Toolkit.getDefaultToolkit().getScreenInsets(new JFrame().getGraphicsConfiguration()).bottom;

    public static final double GRAVITY = 30.0 / FPS;

    /** hit box */
    public static boolean DEBUG = false;

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main (String[] args) throws Exception {
        ArrayList<Pet> pets = Pet.loadPets(args[0], logger);

        try {
            if (args[1].toLowerCase(Locale.ROOT).equals("debug")) {
                DEBUG = true;
            }
        }
        catch (Exception ignore) {}

        logger.info("Screen size: " + SCREEN_SIZE_X + ", " + SCREEN_SIZE_Y);
        logger.info("Resources have loaded.");

        for(Pet pet : pets){
            pet.start();
            logger.info("Generate " + pet.name);
            Thread.sleep(1000);
        }
    }
}
