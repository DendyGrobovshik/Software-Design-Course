package ru.gradoboev.sd.lab5.graph;

import ru.gradoboev.sd.lab5.drawingApi.DrawingApi;
import ru.gradoboev.sd.lab5.utils.Pair;

import java.util.ArrayList;
import java.util.List;

public class ListEdgesGraph extends Graph {
    private final List<Pair<Integer, Integer>> edges = new ArrayList<>();

    private int size;

    public ListEdgesGraph(DrawingApi drawingApi) {
        super(drawingApi);
    }

    public ListEdgesGraph setSize(int size) {
        this.size = size;

        return this;
    }

    public ListEdgesGraph addEdge(int from, int to) {
        edges.add(new Pair<>(from, to));

        return this;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void drawGraph() {
        drawVertices();

        drawEdges();

        drawingApi.render();
    }

    private void drawEdges() {
        for (var edge : edges) {
            drawEdge(edge);
        }
    }
}
