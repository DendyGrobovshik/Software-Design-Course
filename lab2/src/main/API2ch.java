package main;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class API2ch {
    private static final ObjectMapper deserializer = new ObjectMapper()
            .configure(
                    DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                    false
            );

    public static Board[] getBoards() throws IOException {
        final String content = Client2ch.fetchBoards();

        try {
            return deserializer.readValue(content, Board[].class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static Thread[] getThreadsByBoardId(final String id) throws IOException {
        final String content = Client2ch.fetchThread(id);

        try {
            return deserializer.readValue(content, Board.class).getThreads();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
