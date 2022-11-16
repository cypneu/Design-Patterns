package decorator.shapes;

import decorator.IShape;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

public class TriangleShape implements IShape {
    @Override
    public Shape draw() {
        Polygon triangle = new Polygon(400., 200., 275., 400., 525., 400.);
        triangle.setFill(Color.WHITE);
        return triangle;
    }
}
