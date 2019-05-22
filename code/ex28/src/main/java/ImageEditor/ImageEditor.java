package ImageEditor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.commons.lang3.RandomStringUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class ImageEditor {

    private Logger logger = LogManager.getLogger(ImageEditor.class);
    private BufferedImage imageTemp;

    // private Stack<Operationdone> operationDone;

    public ImageEditor() {
    }

    public void loadImage(String filename) {
        if (filename != null && !filename.isEmpty()) {
            try {
                imageTemp = ImageIO.read(new File(filename));
                logger.info("Loaded file from pathstring: " + filename);
            } catch (IOException e) {
                logger.error("Error during file loading", e);
            }
        } else {
            logger.warn("Filename is empty.");
        }
    }

    public void saveImage() {
        String path = "D:\\img\\";
        String filename = (RandomStringUtils.randomAlphanumeric(15));
        String fullPath = path + filename + ".jpg";

        try {
            ImageIO.write(imageTemp, "jpg", new File(fullPath));
            logger.info("Saved file to pathstring: " + fullPath);
        } catch (IOException e) {
            logger.error("Error during file saving", e);
        }
    }

    public void addTextToTopLeftCorner(String text) {
        logger.info("Printing \'" + text + "\' on image");

        Graphics2D g = imageTemp.createGraphics();
        g.setColor(Color.RED);

        Font font = new Font("Arial", Font.PLAIN, 200);
        g.setFont(font);
        g.drawString(text, 0, 200);

        g.dispose();
    }
}
