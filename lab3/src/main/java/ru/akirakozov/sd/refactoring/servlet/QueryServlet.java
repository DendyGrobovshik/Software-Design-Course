package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.db.DataBaseConnection;
import ru.akirakozov.sd.refactoring.db.ResultSetHandler;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author akirakozov
 */
public class QueryServlet extends HttpServlet {
    private DataBaseConnection connection;

    public QueryServlet(DataBaseConnection connection) {
        this.connection = connection;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter("command");

        if ("max".equals(command)) {
            connection
                    .open()
                    .executeQuery("SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1", getMaxHandler(response))
                    .close();
        } else if ("min".equals(command)) {
            connection
                    .open()
                    .executeQuery("SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1", getMinHandler(response))
                    .close();
        } else if ("sum".equals(command)) {
            connection
                    .open()
                    .executeQuery("SELECT SUM(price) FROM PRODUCT", getSumHandler(response))
                    .close();
        } else if ("count".equals(command)) {
            connection
                    .open()
                    .executeQuery("SELECT COUNT(*) FROM PRODUCT", getCountHandler(response))
                    .close();
        } else {
            response.getWriter().println("Unknown command: " + command);
        }

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    private ResultSetHandler getMaxHandler(HttpServletResponse response) {
        return rs -> {
            response.getWriter().println("<html><body>");
            response.getWriter().println("<h1>Product with max price: </h1>");

            while (rs.next()) {
                String name = rs.getString("name");
                int price = rs.getInt("price");
                response.getWriter().println(name + "\t" + price + "</br>");
            }
            response.getWriter().println("</body></html>");
        };
    }

    private ResultSetHandler getMinHandler(HttpServletResponse response) {
        return rs -> {
            response.getWriter().println("<html><body>");
            response.getWriter().println("<h1>Product with min price: </h1>");

            while (rs.next()) {
                String name = rs.getString("name");
                int price = rs.getInt("price");
                response.getWriter().println(name + "\t" + price + "</br>");
            }
            response.getWriter().println("</body></html>");
        };
    }

    private ResultSetHandler getSumHandler(HttpServletResponse response) {
        return rs -> {
            response.getWriter().println("<html><body>");
            response.getWriter().println("Summary price: ");

            if (rs.next()) {
                response.getWriter().println(rs.getInt(1));
            }
            response.getWriter().println("</body></html>");
        };
    }

    private ResultSetHandler getCountHandler(HttpServletResponse response) {
        return rs -> {
            response.getWriter().println("<html><body>");
            response.getWriter().println("Number of products: ");

            if (rs.next()) {
                response.getWriter().println(rs.getInt(1));
            }
            response.getWriter().println("</body></html>");
        };
    }
}
