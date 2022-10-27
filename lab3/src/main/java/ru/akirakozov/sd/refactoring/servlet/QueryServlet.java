package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.db.DataBaseConnection;
import ru.akirakozov.sd.refactoring.db.ResultSetHandler;
import ru.akirakozov.sd.refactoring.html.HTMLFormatter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author akirakozov
 */
public class QueryServlet extends HttpServlet {
    private final DataBaseConnection connection;

    public QueryServlet(DataBaseConnection connection) {
        this.connection = connection;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter("command");

        if ("max".equals(command)) {
            connection
                    .open()
                    .executeQuery("SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1", getNamePriceHandler(response, "<h1>Product with max price: </h1>"))
                    .close();
        } else if ("min".equals(command)) {
            connection
                    .open()
                    .executeQuery("SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1", getNamePriceHandler(response, "<h1>Product with min price: </h1>"))
                    .close();
        } else if ("sum".equals(command)) {
            connection
                    .open()
                    .executeQuery("SELECT SUM(price) FROM PRODUCT", getValueHandler(response, "Summary price: "))
                    .close();
        } else if ("count".equals(command)) {
            connection
                    .open()
                    .executeQuery("SELECT COUNT(*) FROM PRODUCT", getValueHandler(response, "Number of products: "))
                    .close();
        } else {
            response.getWriter().println("Unknown command: " + command);
        }

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    private ResultSetHandler getNamePriceHandler(HttpServletResponse response, String header) {
        return new HTMLFormatter(response)
                .bodyStart()
                .addHeader(header)
                .addNamePrice()
                .bodyEnd()
                .get();
    }

    private ResultSetHandler getValueHandler(HttpServletResponse response, String header) {
        return new HTMLFormatter(response)
                .bodyStart()
                .addHeader(header)
                .addValue()
                .bodyEnd()
                .get();
    }
}
