package decorator.decorators;

import decorator.IShape;
import javafx.scene.shape.Shape;

public class BaseDecorator implements IShape {
    protected IShape wrappedShape;

    public BaseDecorator(IShape wrappedShape) {
        this.wrappedShape = wrappedShape;
    }

    @Override
    public Shape draw() {
        return this.wrappedShape.draw();
    }
}
