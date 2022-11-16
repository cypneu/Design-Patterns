package decorator.shapes;

import decorator.IShape;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class CircleShape implements IShape {
    @Override
    public Shape draw() {
        return new Circle(400, 300, 100, Color.WHITE);
    }
}
