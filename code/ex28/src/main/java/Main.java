import editor.ImageEditor;
import editor.operation.impl.AddTopLeftCornerTextOperation;

import java.awt.*;

public class Main {
    public static void main(String args[]){
        ImageEditor imageEditor = new ImageEditor();
        imageEditor.loadImage("C:\\Users\\Damian\\Downloads\\maxresdefault.jpg");

        AddTopLeftCornerTextOperation addCzesc = new AddTopLeftCornerTextOperation();
        addCzesc.setColor(Color.BLUE);
        addCzesc.setFontSize(200);
        addCzesc.setText("czesc");
        imageEditor.performOperation(addCzesc);

        imageEditor.saveImage();

        AddTopLeftCornerTextOperation addSiema = new AddTopLeftCornerTextOperation();
        addSiema.setColor(Color.RED);
        addSiema.setFontSize(150);
        addSiema.setText("siema");
        imageEditor.performOperation(addSiema);

        imageEditor.saveImage();

        imageEditor.undo();

        imageEditor.saveImage();

        AddTopLeftCornerTextOperation incorrectOperation = new AddTopLeftCornerTextOperation();
        incorrectOperation.setColor(Color.BLUE);
        incorrectOperation.setFontSize(200);
//        addCzesc.setText("nie ma tekstu");
        imageEditor.performOperation(incorrectOperation);
    }

}
