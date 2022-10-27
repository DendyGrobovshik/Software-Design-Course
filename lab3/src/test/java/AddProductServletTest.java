import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.akirakozov.sd.refactoring.db.DataBaseConnection;
import ru.akirakozov.sd.refactoring.servlet.AddProductServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.*;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class AddProductServletTest {
    private HttpServletRequest request;
    private HttpServletResponse response;
    private StringWriter responseContent;

    private static class AddProductServletOpen extends AddProductServlet {
        public AddProductServletOpen() {
            super(new DataBaseConnection("jdbc:sqlite:test.db"));
        }
        @Override
        public void doGet(HttpServletRequest request,
                          HttpServletResponse response) throws IOException {
            super.doGet(request, response);
        }
    }

    @BeforeEach
    void createRequestMock() {
        request = mock(HttpServletRequest.class);

        when(request.getParameter("name")).thenReturn("iphone");
        when(request.getParameter("price")).thenReturn("300");
    }

    @BeforeEach
    void createResponseMock() throws IOException {
        response = mock(HttpServletResponse.class);

        responseContent = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(responseContent));
    }

    @Test
    public void doGet_return_ok() throws IOException {
        new AddProductServletOpen().doGet(request, response);

        verify(response).setContentType("text/html");
        verify(response).setStatus(HttpServletResponse.SC_OK);
        assertEquals("OK\n", responseContent.toString());
    }

    @Test
    public void doGet_fails_if_price_is_not_provided() {
        when(request.getParameter("price")).thenReturn(null);

        assertThrows(NumberFormatException.class, () -> {
            new AddProductServletOpen().doGet(request, response);
        });
    }

    @Test
    public void doGet_insert_new_product_to_database() throws IOException, SQLException {
        String now = new Date().toString();
        when(request.getParameter("name")).thenReturn(now);

        new AddProductServletOpen().doGet(request, response);

        assertEquals(1, DataBaseUtils.getNumberOfProductsByName(now));
    }
}
