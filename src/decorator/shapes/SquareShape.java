package decorator.shapes;

import decorator.IShape;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class SquareShape implements IShape {
    @Override
    public Shape draw() {
        Rectangle rectangle = new Rectangle(300, 200, 200, 200);
        rectangle.setFill(Color.WHITE);
        return rectangle;
    }
}
