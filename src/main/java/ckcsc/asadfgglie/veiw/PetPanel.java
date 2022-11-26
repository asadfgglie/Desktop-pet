package ckcsc.asadfgglie.veiw;

import ckcsc.asadfgglie.pet.Pet;
import ckcsc.asadfgglie.pet.action.Side;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class PetPanel extends JPanel {
    private final Pet pet;

    public PetPanel (Pet pet){
        this.pet = pet;

        setBackground(new Color(0, 0,0,0));
    }

    @Override
    protected void paintComponent (Graphics g) {
        super.paintComponent(g);
        Image image = pet.getWindow().getTickImage();

        Graphics2D g2 = (Graphics2D) g.create();
        Dimension d = getSize();

        AffineTransform transform = new AffineTransform();

        double sc = Math.min(d.width / (double)image.getWidth(this), d.height / (double)image.getHeight(this));

        transform.scale(sc, sc);

        if(pet.honSide == Side.Right){
            // flip image horizontally
            AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
            tx.translate(-image.getWidth(this), 0);
            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
            image = op.filter(toBufferedImage(image), null);
        }

        g2.drawImage(image, transform, this);
        g2.dispose();
    }

    private BufferedImage toBufferedImage (Image img) {
        if (img instanceof BufferedImage)
        {
            return (BufferedImage) img;
        }

        // Create a buffered image with transparency
        BufferedImage bufferedImage = new BufferedImage(img.getWidth(this), img.getHeight(this), BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = bufferedImage.createGraphics();
        bGr.drawImage(img, 0, 0, this);
        bGr.dispose();

        // Return the buffered image
        return bufferedImage;
    }
}
