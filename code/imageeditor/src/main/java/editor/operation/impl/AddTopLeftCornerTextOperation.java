package editor.operation.impl;

import editor.exception.InvalidParametersException;
import editor.operation.Operation;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.StringJoiner;

public class AddTopLeftCornerTextOperation implements Operation {
    String text;
    Color color;
    Integer fontSize;
    private Logger logger = LogManager.getLogger(AddTopLeftCornerTextOperation.class);

    @Override
    public void perform(BufferedImage image) throws InvalidParametersException {
        validateParameters();

        logger.info("Performing " + toString());

        Graphics2D g = image.createGraphics();
        g.setColor(color);

        Font font = new Font("Arial", Font.PLAIN, fontSize);
        g.setFont(font);
        g.drawString(text, 0, fontSize);

        g.dispose();
    }

    private void validateParameters() throws InvalidParametersException {
        StringJoiner sj = new StringJoiner(" ");

        if (StringUtils.isEmpty(text)) {
            sj.add("Text is empty.");
        }

        if (color == null) {
            sj.add("Text color was not set.");
        }

        if (fontSize == null || fontSize <= 0) {
            sj.add("Font size must be greater than 0.");
        }

        String errorMessage = sj.toString();
        if (!errorMessage.isEmpty()) {
            throw new InvalidParametersException(sj.toString());
        }
    }

    public String toString() {
        return "Operation AddCenterText "
                + "["
                + "text: " + text + "; "
                + "font size: " + fontSize + "; "
                + "color: " + color
                + "]";
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
