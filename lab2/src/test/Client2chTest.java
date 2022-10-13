package test;

import com.github.tomakehurst.wiremock.WireMockServer;
import main.Client2ch;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Client2chTest {

    @Nested
    @DisplayName("Fetching data from stub server")
    class stubServer {
        private final static String boardsDataPath = "src/test/resources/__files/json/boardsData.json";
        private final static String bBoardDataPath = "src/test/resources/__files/json/bBoardData.json";
        private WireMockServer wm;

        @BeforeEach
        void startStubServer() {
            wm = new WireMockServer(options().port(8080));
            wm.start();
        }

        @AfterEach
        void endStartServer() {
            wm.stop();
        }

        @Test
        void fetchBoards_from_specified_server() throws Exception {
            final String boardsData = getData(boardsDataPath);

            wm.stubFor(get(urlEqualTo("/api/mobile/v2/boards"))
                    .willReturn(aResponse()
                            .withStatus(200)
                            .withBody(boardsData)));

            final Client2ch client2ch = new Client2ch("http://localhost:8080");

            final String fetchedBoardsData = client2ch.fetchBoards();

            assertIgnoreSpaces(boardsData, fetchedBoardsData);
        }

        @Test
        void fetchBoardWithThreads_from_specified_server() throws Exception {
            final String bBoardData = getData(bBoardDataPath);

            wm.stubFor(get(urlEqualTo("/b/catalog_num.json"))
                    .willReturn(aResponse()
                            .withStatus(200)
                            .withBody(bBoardData)));

            final Client2ch client2ch = new Client2ch("http://localhost:8080");

            final String fetchedBoardData = client2ch.fetchBoardWithThreads("b");

            assertIgnoreSpaces(bBoardData, fetchedBoardData);
        }
    }

    private String getData(String path) throws IOException {
        FileInputStream fis = new FileInputStream(path);

        return IOUtils.toString(fis, StandardCharsets.UTF_8);
    }

    private void assertIgnoreSpaces(String a, String b) {
        assertEquals(
                a.replaceAll("\\s+", ""),
                b.replaceAll("\\s+", "")
        );
    }
}
