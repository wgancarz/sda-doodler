import ImageEditor.ImageEditor;

public class Main {
    public static void main(String args[]){
        ImageEditor imageEditor = new ImageEditor();
        imageEditor.loadImage("C:\\Users\\Damian\\Downloads\\maxresdefault.jpg");

        imageEditor.addTextToTopLeftCorner("czesd");

        imageEditor.saveImage();
    }

}
