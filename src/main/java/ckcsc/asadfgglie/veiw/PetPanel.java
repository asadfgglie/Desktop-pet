package ckcsc.asadfgglie.veiw;

import ckcsc.asadfgglie.pet.Pet;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class PetPanel extends JPanel {
    private final Pet pet;

    public PetPanel (Pet pet){
        this.pet = pet;

        setBackground(new Color(0, 0,0,0));
    }

    @Override
    protected void paintComponent (Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();
        Dimension d = getSize();

        AffineTransform transform = new AffineTransform();

        double sc = Math.min(d.width / (double)pet.getWindow().getTickImage().getWidth(null), d.height / (double)pet.getWindow().getTickImage().getHeight(null));

        transform.scale(sc, sc);

        g2.drawImage(pet.getWindow().getTickImage(), transform, this);
        g2.dispose();
    }
}
