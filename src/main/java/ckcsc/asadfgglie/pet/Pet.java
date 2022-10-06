package ckcsc.asadfgglie.pet;

import ckcsc.asadfgglie.main.Main;
import ckcsc.asadfgglie.pet.action.Stand;
import ckcsc.asadfgglie.pet.action.Walk;
import ckcsc.asadfgglie.pet.behavior.BehaviorContainer;
import ckcsc.asadfgglie.veiw.PetPanel;
import ckcsc.asadfgglie.veiw.PetWindow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;

public class Pet extends Thread{
    private final Logger logger;

    public final String name;

    private final BehaviorContainer behavior;


    private final PetPanel panel;
    private final PetWindow window;

    public Pet (String name, BehaviorContainer behavior){
        this.name = name;
        this.behavior = behavior;
        logger = LoggerFactory.getLogger(this.getClass() + "-" + name);

        panel = new PetPanel(this);
        window = new PetWindow(this);

    }

    public PetPanel getPanel (){
        return panel;
    }

    public PetWindow getWindow (){
        return window;
    }

    public static Pet[] loadPets (String path, Logger logger) throws FileNotFoundException{
        File folder = new File(path);

        if(folder.isDirectory()){
            File[] petFolders = folder.listFiles(File::isDirectory);

            if(petFolders == null){
                throw new FileNotFoundException("I Need Directories for your each pet!\nEach your pet must have a folder to store!");
            }

            Pet[] pets = new Pet[petFolders.length];

            for(int i = 0; i < petFolders.length; i++){
                logger.info("load .../" + petFolders[i].getName());

                pets[i] = loadPet(petFolders[i], logger);
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
        behaviors.setLogger(LoggerFactory.getLogger(BehaviorContainer.class.getSimpleName() + "-" + path.getName()));
        return new Pet(path.getName(), behaviors);
    }

    public Image getImage (BehaviorContainer.BehaviorList behavior, int index) {
        return this.behavior.getImage(behavior, index);
    }

    @Override
    public void run () {
        new Thread(() -> {
            logger.debug(name + "-ActionSwitch thread start");
            while (true) {
                window.setAction(new Stand(this));
                try {
                    Thread.sleep(2000);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                window.setAction(new Walk(this));
                try {
                    Thread.sleep(2000);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, name + "-ActionSwitch").start();
        new Thread(window, name + "-window").start();
    }
}
