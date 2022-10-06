package ckcsc.asadfgglie.main;

import ckcsc.asadfgglie.pet.Pet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    public static final int FPS = 30;
    public static final int MAX_SIZE = 200;

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main (String[] args) throws Exception {
        Pet[] pets = Pet.loadPets(args[0], logger);

        logger.info("Resources have loaded.");

        for(Pet pet : pets){
            pet.start();
            Thread.sleep(1000);
        }
    }
}
