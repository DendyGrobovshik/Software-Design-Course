package main;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class API2ch {
    private final Client2ch client;

    public API2ch(Client2ch client) {
        this.client = client;
    }
    private static final ObjectMapper deserializer = new ObjectMapper()
            .configure(
                    DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                    false
            );

    public Board[] getBoards() throws IOException {
        final String content = client.fetchBoards();

        try {
            return deserializer.readValue(content, Board[].class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public Thread[] getThreadsByBoardId(final String id) throws IOException {
        final String content = client.fetchBoardWithThreads(id);

        try {
            return deserializer.readValue(content, Board.class).getThreads();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
