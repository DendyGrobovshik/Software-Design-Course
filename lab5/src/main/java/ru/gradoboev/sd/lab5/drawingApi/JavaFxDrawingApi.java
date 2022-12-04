package ru.gradoboev.sd.lab5.drawingApi;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import ru.gradoboev.sd.lab5.utils.Pair;

public class JavaFxDrawingApi extends Application implements DrawingApi {
    private static final Canvas canvas;
    private static final GraphicsContext gc;

    static {
        canvas = new Canvas(600, 400);
        gc = canvas.getGraphicsContext2D();
    }

    @Override
    public int getDrawingAreaWidth() {
        return 600;
    }

    @Override
    public int getDrawingAreaHeight() {
        return 400;
    }

    @Override
    public void drawCircle(Pair<Integer, Integer> cord, int radius, String text) {
        gc.strokeOval(cord.x - radius, cord.y - radius, radius * 2, radius * 2);

        gc.fillText(text, cord.x - radius, cord.y - radius);
    }

    @Override
    public void drawLine(Pair<Integer, Integer> start, Pair<Integer, Integer> end) {
        gc.strokeLine(start.x, start.y, end.x, end.y);

        gc.fillOval(start.x - 5, start.y - 5, 10, 10);
        gc.fillOval(end.x - 5, end.y - 5, 10, 10);
    }

    @Override
    public void render() {
        Application.launch();
    }

    @Override
    public void start(Stage stage) {
        Group root = new Group();
        root.getChildren().add(canvas);
        stage.setScene(new Scene(root));
        stage.show();
    }
}
