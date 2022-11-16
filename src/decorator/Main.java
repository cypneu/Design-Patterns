package decorator;

import decorator.decorators.ColorDecorator;
import decorator.decorators.LineStyleDecorator;
import decorator.shapes.CircleShape;
import decorator.shapes.SquareShape;
import decorator.shapes.TriangleShape;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class Main extends Application {
    CircleShape circle = new CircleShape();
    SquareShape square = new SquareShape();
    TriangleShape triangle = new TriangleShape();

    Pane root = new Pane();
    HBox mainHBox = new HBox();
    Group shapeGroup = new Group();

    IShape currentShape;
    Color currentColor;
    LineStyle currentLineStyle;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        setShapeRadioButtons();
        setColorRadioButtons();
        setLineStyleRadioButtons();

        root.setStyle("-fx-background-color: white;");
        root.getChildren().add(shapeGroup);
        root.getChildren().add(mainHBox);
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Decorator Design Pattern");
        primaryStage.show();
    }

    private void setShapeRadioButtons() {
        VBox shapeRadioButtonsVBox = new VBox();
        String[] shapeRadioLabels = {"Circle", "Triangle", "Square"};
        ToggleGroup shapeToggleGroup = createRadioButtons(shapeRadioButtonsVBox, "Shape:", shapeRadioLabels);

        shapeToggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            RadioButton rb = (RadioButton) shapeToggleGroup.getSelectedToggle();
            switch (rb.getText()) {
                case "Circle" -> currentShape = circle;
                case "Triangle" -> currentShape = triangle;
                case "Square" -> currentShape = square;
            }

            redrawShape();
        });

        mainHBox.getChildren().add(shapeRadioButtonsVBox);
    }

    private void setColorRadioButtons() {
        VBox colorRadioButtonsVBox = new VBox();
        String[] colorRadioLabels = {"Red", "Green", "Blue"};
        ToggleGroup colorToggleGroup = createRadioButtons(colorRadioButtonsVBox, "Color:", colorRadioLabels);

        colorToggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            RadioButton rb = (RadioButton) colorToggleGroup.getSelectedToggle();
            switch (rb.getText()) {
                case "Red" -> currentColor = Color.RED;
                case "Green" -> currentColor = Color.GREEN;
                case "Blue" -> currentColor = Color.BLUE;
            }

            redrawShape();
        });

        mainHBox.getChildren().add(colorRadioButtonsVBox);
    }

    private void setLineStyleRadioButtons() {
        VBox lineStyleRadioButtonsVBox = new VBox();
        String[] lineStyleRadioLabels = {"Solid", "Dashed", "Dotted"};
        ToggleGroup lineStyleToggleGroup = createRadioButtons(lineStyleRadioButtonsVBox, "Line style:", lineStyleRadioLabels);

        lineStyleToggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            RadioButton rb = (RadioButton) lineStyleToggleGroup.getSelectedToggle();
            switch (rb.getText()) {
                case "Solid" -> currentLineStyle = LineStyle.SOLID;
                case "Dashed" -> currentLineStyle = LineStyle.DASHED;
                case "Dotted" -> currentLineStyle = LineStyle.DOTTED;
            }

            redrawShape();
        });

        mainHBox.getChildren().add(lineStyleRadioButtonsVBox);
    }

    private ToggleGroup createRadioButtons(VBox vBox, String labelText, String[] radioLabels) {
        vBox.setPadding(new Insets(5, 10, 10, 10));
        vBox.setSpacing(3);
        vBox.getChildren().add(new Label(labelText));

        ToggleGroup toggleGroup = new ToggleGroup();
        for (String radioLabel : radioLabels) {
            RadioButton radioButton = new RadioButton(radioLabel);
            radioButton.setToggleGroup(toggleGroup);
            vBox.getChildren().add(radioButton);
        }

        return toggleGroup;
    }

    private void redrawShape() {
        if (currentShape != null) {
            if (currentColor != null) currentShape = new ColorDecorator(currentShape, currentColor);

            if (currentLineStyle != null) currentShape = new LineStyleDecorator(currentShape, currentLineStyle);

            if (currentColor != null && currentLineStyle != null) {
                shapeGroup.getChildren().clear();
                shapeGroup.getChildren().add(currentShape.draw());
            }
        }
    }
}