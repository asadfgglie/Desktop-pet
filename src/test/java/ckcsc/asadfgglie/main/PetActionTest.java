package ckcsc.asadfgglie.main;

import ckcsc.asadfgglie.pet.Pet;
import ckcsc.asadfgglie.util.SpeedVector;
import ckcsc.asadfgglie.pet.action.Walk;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;

public class PetActionTest {
    private final Logger _logger = LoggerFactory.getLogger(PetActionTest.class);

    @Test
    public synchronized void walkTest() throws FileNotFoundException, InterruptedException {
        Pet pet = Pet.loadPet(new File("E:\\program\\Java\\Desktop_pet\\resouces\\pets\\kuro_shime"), _logger);
        pet.generate();
        pet.getPetActionHandle().stop();
        pet.getWindow().setLocation(pet.getWindow().getX(), Main.SCREEN_SIZE_Y - Main.MAX_SIZE);

        SpeedVector speedVector = new SpeedVector(10, 0);
        int x0 = pet.getWindow().getX();
        int time = 1;
        _logger.debug("default x: " + pet.getWindow().getX());
        pet.startAction(new Walk(pet, speedVector, 1000 * time));
        _logger.debug("x1: " + pet.getWindow().getX());
        int x1 = pet.getWindow().getX();
        int deltaX = x1 - x0;
        _logger.debug("delta x: " + deltaX + ", speed: " + deltaX / time + ", speedVector: " + speedVector);

        speedVector = new SpeedVector(10, 0);
        x0 = pet.getWindow().getX();
        _logger.debug("default x: " + pet.getWindow().getX());
        pet.startAction(new Walk(pet, speedVector, 1000 * time));
        _logger.debug("x1: " + pet.getWindow().getX());
        x1 = pet.getWindow().getX();
        deltaX = x1 - x0;
        _logger.debug("delta x: " + deltaX + ", speed: " + deltaX / time + ", speedVector: " + speedVector);

        wait();
    }
}
