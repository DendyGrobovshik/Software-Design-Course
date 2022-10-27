package ru.akirakozov.sd.refactoring.html;

import ru.akirakozov.sd.refactoring.db.ResultSetHandler;

import javax.servlet.http.HttpServletResponse;

public class HTMLFormatter {
    private final HttpServletResponse response;
    private ResultSetHandler previous = x -> {};

    public HTMLFormatter(HttpServletResponse response) {
        this.response = response;
    }

    public ResultSetHandler get() {
        return previous;
    }

    public HTMLFormatter bodyStart() {
        ResultSetHandler prev = previous;
        previous = rs -> {
            prev.handle(rs);
            response.getWriter().println("<html><body>");
        };

        return this;
    }

    public HTMLFormatter addHeader(String header) {
        ResultSetHandler prev = previous;
        previous = rs -> {
            prev.handle(rs);
            response.getWriter().println(header);
        };

        return this;
    }

    public HTMLFormatter addNamePrice(){
        ResultSetHandler prev = previous;
        previous = rs -> {
            prev.handle(rs);
            while (rs.next()) {
                String name = rs.getString("name");
                int price = rs.getInt("price");
                response.getWriter().println(name + "\t" + price + "</br>");
            }
        };

        return this;
    }

    public HTMLFormatter addValue() {
        ResultSetHandler prev = previous;
        previous =  rs -> {
            prev.handle(rs);
            response.getWriter().println(rs.getInt(1));
        };

        return this;
    }

    public HTMLFormatter bodyEnd() {
        ResultSetHandler prev = previous;
        previous = rs -> {
            prev.handle(rs);
            response.getWriter().println("</body></html>");
        };

        return this;
    }
}
