package editor;

import editor.exception.InvalidParametersException;
import editor.history.HistoryEntry;
import editor.operation.Operation;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.Stack;


public class ImageEditor {

    private Logger logger = LogManager.getLogger(ImageEditor.class);
    private BufferedImage imageTemp;

    private Stack<HistoryEntry> history = new Stack<>();

    public ImageEditor() {
    }

    public void loadImage(String filename) {
        if (StringUtils.isEmpty(filename)) {
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

    public void undo() {
        if (history.isEmpty()) {
            logger.error("Nothing to undo you dumbass.");
        } else {
            HistoryEntry previousEntry = history.pop();
            imageTemp = previousEntry.getPreviousImage();
            logger.info("Undone operation: " + previousEntry.getOperation());
        }
    }

    public void performOperation(Operation operation) {
        HistoryEntry newHistoryEntry = new HistoryEntry();

        newHistoryEntry.setPreviousImage(getTempImageCopy());
        newHistoryEntry.setOperation(operation);

        history.push(newHistoryEntry);

        try {
            operation.perform(imageTemp);
        } catch (InvalidParametersException e) {
            e.printStackTrace();
        }
    }

    private BufferedImage getTempImageCopy() {
        ColorModel cm = imageTemp.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = imageTemp.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }
}
