package ru.gradoboev.sd.lab5;

import ru.gradoboev.sd.lab5.drawingApi.AwtDrawingApi;
import ru.gradoboev.sd.lab5.drawingApi.DrawingApi;
import ru.gradoboev.sd.lab5.drawingApi.JavaFxDrawingApi;
import ru.gradoboev.sd.lab5.graph.AdjacencyMatrixGraph;
import ru.gradoboev.sd.lab5.graph.Graph;
import ru.gradoboev.sd.lab5.graph.ListEdgesGraph;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        DrawingApi drawingApi = getDrawer(args[0]);
        Graph graph = getGraph(args[1], drawingApi);

        graph.drawGraph();
    }

    private static DrawingApi getDrawer(String type) throws Exception {
        switch (type) {
            case "javafx":
                return new JavaFxDrawingApi();
            case "awt":
                return new AwtDrawingApi();
        }

        throw new Exception("Wrong drawing api type");
    }

    private static Graph getGraph(String type, DrawingApi drawingApi) throws Exception {
        switch (type) {
            case "list":
                return new ListEdgesGraph(drawingApi)
                        .setSize(12)
                        .addEdge(0, 1)
                        .addEdge(0, 3)
                        .addEdge(0, 4)
                        .addEdge(1, 4);
            case "matrix":
                AdjacencyMatrixGraph graph = new AdjacencyMatrixGraph(drawingApi);

                int size = 12;
                List<List<Boolean>> matrix = new ArrayList<>();
                for (int i = 0; i < size; i++) {
                    List<Boolean> row = new ArrayList<>();
                    for (int j = 0; j < size; j++) {
                        row.add(false);
                    }
                    matrix.add(row);
                }
                matrix.get(0).add(1, true);
                matrix.get(0).add(4, true);
                matrix.get(1).add(4, true);

                graph.setMatrix(matrix);
                return graph;
        }

        throw new Exception("Wrong graph type");
    }
}
