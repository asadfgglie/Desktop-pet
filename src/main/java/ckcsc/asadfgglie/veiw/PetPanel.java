package ckcsc.asadfgglie.veiw;

import ckcsc.asadfgglie.main.Main;
import ckcsc.asadfgglie.pet.Pet;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class PetPanel extends JPanel {
    private final Pet _pet;

    public PetPanel (Pet pet){
        this._pet = pet;

        setBackground(new Color(0, 0,0,0));
    }

    @Override
    protected void paintComponent (Graphics g) {
        super.paintComponent(g);
        Image image = _pet.getWindow().getTickImage();
        int imageWidth = image.getWidth(this);
        int imageHeight = image.getHeight(this);

        // when getWidth and getHeight malfunction, don't forget give it a default value
        imageWidth = (imageWidth > 0)? imageWidth : Main.MAX_SIZE;
        imageHeight = (imageHeight > 0)? imageHeight : Main.MAX_SIZE;

        AffineTransform transform = new AffineTransform();

        double sc = Math.min(Main.MAX_SIZE / (double)imageWidth, Main.MAX_SIZE / (double)imageHeight);

        transform.scale(sc, sc);

        if(_pet.getSpeed().getSpeedX() > 0){
            // flip image horizontally
            AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
            tx.translate(-imageWidth, 0);
            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
            image = op.filter(_toBufferedImage(image, imageWidth, imageHeight), null);
        }

        setSize((int) (imageWidth * sc), (int) (imageHeight * sc));

        Graphics2D g2 = (Graphics2D) g.create();
        g2.drawImage(image, transform, this);

        //hit box
        if(Main.DEBUG) {
            Rectangle rectangle = new Rectangle((int) (image.getWidth(this) * sc), (int) (image.getHeight(this) * sc));
            g2.setStroke(new BasicStroke(10));
            g2.setPaint(Color.WHITE);
            g2.draw(rectangle);
        }

        g2.dispose();
    }

    private BufferedImage _toBufferedImage (Image img, int width, int height) {
        if (img instanceof BufferedImage)
        {
            return (BufferedImage) img;
        }

        // Create a buffered image with transparency
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = bufferedImage.createGraphics();
        bGr.drawImage(img, 0, 0, this);
        bGr.dispose();

        // Return the buffered image
        return bufferedImage;
    }
}
