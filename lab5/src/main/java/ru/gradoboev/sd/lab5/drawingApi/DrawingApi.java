package ru.gradoboev.sd.lab5.drawingApi;

import ru.gradoboev.sd.lab5.utils.Pair;

public interface DrawingApi {
    int getDrawingAreaWidth();

    int getDrawingAreaHeight();

    void drawCircle(Pair<Integer, Integer> cord, int radius, String text);

    void drawLine(Pair<Integer, Integer> start, Pair<Integer, Integer> end);

    void render();
}
