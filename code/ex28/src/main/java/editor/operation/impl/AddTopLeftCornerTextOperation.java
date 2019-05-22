package editor.operation.impl;

import editor.exception.IncorrectParametersException;
import editor.operation.Operation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.awt.image.BufferedImage;

public class AddTopLeftCornerTextOperation implements Operation {
    private Logger logger = LogManager.getLogger(AddTopLeftCornerTextOperation.class);

    String text;
    Color color;
    Integer fontSize;

    @Override
    public void perform(BufferedImage image) throws IncorrectParametersException {
        if (text == null || text.isEmpty() || color == null || fontSize == null) {
            throw new IncorrectParametersException("Incorrect parameters.");
        }

        logger.info("Printing \'" + text + "\' on image");

        Graphics2D g = image.createGraphics();
        g.setColor(color);

        Font font = new Font("Arial", Font.PLAIN, fontSize);
        g.setFont(font);
        g.drawString(text, 0, fontSize);

        g.dispose();
    }

    public String toString() {
        return "Operation AddCenterText [" +
                "text: " + text + "; " +
                "font size: " + fontSize + "; " +
                "color: " + color +
                "]";
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Integer getFontSize() {
        return fontSize;
    }

    public void setFontSize(Integer fontSize) {
        this.fontSize = fontSize;
    }
}
