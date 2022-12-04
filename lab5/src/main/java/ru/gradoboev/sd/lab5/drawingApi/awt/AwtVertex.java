package ru.gradoboev.sd.lab5.drawingApi.awt;

import ru.gradoboev.sd.lab5.utils.Pair;

import java.awt.*;

public class AwtVertex extends Component {
    private final Pair<Integer, Integer> cord;
    private final int radius;

    private final String text;

    public AwtVertex(Pair<Integer, Integer> cord, int radius, String text) {
        this.cord = cord;
        this.radius = radius;
        this.text = text;
    }

    @Override
    public void paint(Graphics g) {
        g.drawOval(cord.x - radius, cord.y - radius, radius * 2, radius * 2);

        g.drawString(text, cord.x - radius, cord.y - radius);
    }
}
