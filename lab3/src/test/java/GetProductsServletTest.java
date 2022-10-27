import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.akirakozov.sd.refactoring.servlet.GetProductsServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class GetProductsServletTest {
    private HttpServletRequest request = mock(HttpServletRequest.class);
    private HttpServletResponse response;
    private StringWriter responseContent;

    private static class GetProductsServletOpen extends GetProductsServlet {
        @Override
        public void doGet(HttpServletRequest request,
                          HttpServletResponse response) throws IOException {
            super.doGet(request, response);
        }
    }

    @BeforeEach
    void createResponseMock() throws IOException {
        response = mock(HttpServletResponse.class);

        responseContent = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(responseContent));
    }

    @Test
    public void doGet_return_ok() throws IOException, SQLException {
        new GetProductsServletOpen().doGet(request, response);

        verify(response).setContentType("text/html");
        verify(response).setStatus(HttpServletResponse.SC_OK);

        assertEquals(
                DataBaseUtils.getAllProductsFormattedForHTML(),
                responseContent.toString()
        );
    }
}
