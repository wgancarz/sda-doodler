package editor.operation;

import editor.exception.IncorrectParametersException;

import java.awt.image.BufferedImage;

public interface Operation {
    void perform(BufferedImage image) throws IncorrectParametersException;

}
