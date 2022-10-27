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
public class GetProductsServlet extends HttpServlet {
    private DataBaseConnection connection;

    public GetProductsServlet(DataBaseConnection connection) {
        this.connection = connection;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        connection
                .open()
                .executeQuery("SELECT * FROM PRODUCT", getHandler(response))
                .close();

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    private ResultSetHandler getHandler(HttpServletResponse response) {
        return rs -> {
            response.getWriter().println("<html><body>");
            while (rs.next()) {
                String name = rs.getString("name");
                int price = rs.getInt("price");
                response.getWriter().println(name + "\t" + price + "</br>");
            }
            response.getWriter().println("</body></html>");
        };
    }
}
