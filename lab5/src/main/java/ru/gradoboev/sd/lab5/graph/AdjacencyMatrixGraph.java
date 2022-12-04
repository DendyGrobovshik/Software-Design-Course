package ru.gradoboev.sd.lab5.graph;

import ru.gradoboev.sd.lab5.drawingApi.DrawingApi;
import ru.gradoboev.sd.lab5.utils.Pair;

import java.util.List;

public class AdjacencyMatrixGraph extends Graph {
    private List<List<Boolean>> matrix;

    public AdjacencyMatrixGraph(DrawingApi drawingApi) {
        super(drawingApi);
    }

    public void setMatrix(List<List<Boolean>> matrix) {
        this.matrix = matrix;
    }

    @Override
    public int size() {
        return matrix.size();
    }

    @Override
    public void drawGraph() {
        drawVertices();

        drawEdges();

        drawingApi.render();
    }

    private void drawEdges() {
        for (int i = 0; i < matrix.size(); i++) {
            var row = matrix.get(i);
            for (int j = 0; j < row.size(); j++) {
                if (i < j && row.get(j)) {
                    drawEdge(new Pair<>(i, j));
                }
            }
        }
    }
}
