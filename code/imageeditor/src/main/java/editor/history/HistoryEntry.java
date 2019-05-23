package editor.history;

import editor.operation.Operation;

import java.awt.image.BufferedImage;

public class HistoryEntry {
    BufferedImage previousImage;
    Operation currentOperation;

    public BufferedImage getPreviousImage() {
        return previousImage;
    }

    public void setPreviousImage(BufferedImage previousImage) {
        this.previousImage = previousImage;
    }

    public Operation getCurrentOperation() {
        return currentOperation;
    }

    public void setCurrentOperation(Operation currentOperation) {
        this.currentOperation = currentOperation;
    }
}
