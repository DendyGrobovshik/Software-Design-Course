package ru.gradoboev.sd.lab5.drawingApi.awt;

import ru.gradoboev.sd.lab5.utils.Pair;

import java.awt.*;

public class AwtEdge extends Component {
    private final Pair<Integer, Integer> start;
    private final Pair<Integer, Integer> end;

    public AwtEdge(Pair<Integer, Integer> start, Pair<Integer, Integer> end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public void paint(Graphics g) {
        g.drawLine(start.x, start.y, end.x, end.y);

        g.fillOval(start.x - 5, start.y - 5, 10, 10);
        g.fillOval(end.x - 5, end.y - 5, 10, 10);
    }
}
