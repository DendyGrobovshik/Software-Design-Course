package ru.gradoboev.sd.lab5.graph;

import ru.gradoboev.sd.lab5.drawingApi.DrawingApi;
import ru.gradoboev.sd.lab5.utils.Pair;

public abstract class Graph {
    protected final DrawingApi drawingApi;

    private Pair<Integer, Integer> _center;
    private int _radius;
    private int _vertexRadius;

    public Pair<Integer, Integer> center() {
        initGraphGeometry();
        return _center;
    }

    public int radius() {
        initGraphGeometry();
        return _radius;
    }

    public int vertexRadius() {
        initGraphGeometry();
        return _vertexRadius;
    }

    public Graph(DrawingApi drawingApi) {
        this.drawingApi = drawingApi;
    }

    public abstract void drawGraph();

    public abstract int size();

    private void initGraphGeometry() {
        final int vertexRadius;
        final int height = drawingApi.getDrawingAreaHeight();
        final int width = drawingApi.getDrawingAreaWidth();

        final int heightMid = height / 2;
        final int widthMid = width / 2;
        final Pair<Integer, Integer> center = new Pair<>(widthMid, heightMid);

        int maxRadius = Math.min(height, width) / 2;

        int circumference = (int) (2 * Math.PI * maxRadius);

        // distance between vertex is equal to vertex size
        vertexRadius = circumference / size() / 4;

        // correction
        this._radius = maxRadius - vertexRadius * 2;
        center.x -= vertexRadius / 2;
        center.y -= vertexRadius / 2;

        this._center = center;
        this._vertexRadius = vertexRadius;
    }

    protected void drawVertices() {
        for (int i = 0; i < size(); i++) {
            final double angle = 360.0 / size() * i;

            final var vertexCord = getXY(angle);

            drawingApi.drawCircle(vertexCord, vertexRadius(), String.valueOf(i));
        }
    }

    protected void drawEdge(Pair<Integer, Integer> edge) {
        final int angleFirst = 360 / size() * edge.x;
        final var start = getXY(angleFirst);

        final int angleSecond = 360 / size() * edge.y;
        final var end = getXY(angleSecond);

        drawingApi.drawLine(start, end);
    }

    private Pair<Integer, Integer> getXY(double angle) {
        final double yShift = Math.sin(Math.toRadians(angle));
        final double xShift = Math.cos(Math.toRadians(angle));

        final var center = center();
        final var radius = radius();

        return new Pair<>((int) (center.x + xShift * radius), (int) (center.y + yShift * radius));
    }
}
