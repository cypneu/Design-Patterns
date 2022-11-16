package decorator.decorators;

import decorator.IShape;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public class ColorDecorator extends BaseDecorator implements IShape {
    public Color color;

    public ColorDecorator(IShape wrappedShape, Color color) {
        super(wrappedShape);
        this.color = color;
    }

    @Override
    public Shape draw() {
        Shape shape = wrappedShape.draw();
        shape.setStroke(this.color);
        shape.setStrokeWidth(3);
        return shape;
    }
}
