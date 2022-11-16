package decorator.decorators;

import decorator.IShape;
import decorator.LineStyle;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeLineCap;

public class LineStyleDecorator extends BaseDecorator implements IShape {
    public LineStyle lineStyle;

    public LineStyleDecorator(IShape wrappedShape, LineStyle lineStyle) {
        super(wrappedShape);
        this.lineStyle = lineStyle;
    }

    @Override
    public Shape draw() {
        Shape shape = wrappedShape.draw();

        shape.getStrokeDashArray().clear();
        switch (this.lineStyle) {
            case DASHED -> {
                shape.setStrokeLineCap(StrokeLineCap.SQUARE);
                shape.getStrokeDashArray().add(2 * Math.PI * 100 / 100);
            }
            case DOTTED -> {
                shape.setStrokeLineCap(StrokeLineCap.ROUND);
                shape.getStrokeDashArray().addAll(2 * Math.PI * 100 / 2000, 2 * Math.PI * 100 / 100);
            }
        }

        return shape;
    }

}
