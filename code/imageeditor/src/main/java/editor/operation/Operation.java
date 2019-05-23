package editor.operation;

import editor.exception.InvalidParametersException;

import java.awt.image.BufferedImage;

public interface Operation {
    void perform(BufferedImage image) throws InvalidParametersException;

}
