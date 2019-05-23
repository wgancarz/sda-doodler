package editor.history;

import editor.operation.Operation;

import java.awt.image.BufferedImage;

public class HistoryEntry {
    BufferedImage previousImage;
    Operation operation;

    public BufferedImage getPreviousImage() {
        return previousImage;
    }

    public void setPreviousImage(BufferedImage previousImage) {
        this.previousImage = previousImage;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }
}
