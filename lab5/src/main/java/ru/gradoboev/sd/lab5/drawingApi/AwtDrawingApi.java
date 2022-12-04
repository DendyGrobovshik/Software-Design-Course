package ru.gradoboev.sd.lab5.drawingApi;

import ru.gradoboev.sd.lab5.drawingApi.awt.AwtEdge;
import ru.gradoboev.sd.lab5.drawingApi.awt.AwtVertex;
import ru.gradoboev.sd.lab5.utils.Pair;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AwtDrawingApi extends Container implements DrawingApi {
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
        AwtVertex vertex = new AwtVertex(cord, radius, text);

        add(vertex);
    }

    @Override
    public void paint(Graphics g) {
        for (var component : super.getComponents()) {
            if (component.isVisible()) {
                component.paint(g);
            }
        }
    }

    @Override
    public void render() {
        Frame frame = new Frame();
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
        frame.setSize(getDrawingAreaWidth(), getDrawingAreaHeight());
        frame.add(this);
        frame.setVisible(true);
    }

    @Override
    public void drawLine(Pair<Integer, Integer> start, Pair<Integer, Integer> end) {
        AwtEdge edge = new AwtEdge(start, end);

        add(edge);
    }
}
