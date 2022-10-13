package test;

import main.API2ch;
import main.Board;
import main.Client2ch;
import main.Thread;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.io.IOException;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class API2chTest {
    private Client2ch client2ch;
    private API2ch api2ch;

    @BeforeEach
    public void cacheInit() {
        client2ch = mock(Client2ch.class);
        api2ch = new API2ch(client2ch);
    }

    @Test
    void getBoards_call_Client2ch_fetchBoards_once() {
        try {
            when(client2ch.fetchBoards()).thenReturn("[]");

            api2ch.getBoards();
            verify(client2ch, times(1)).fetchBoards(); // Mock role
        } catch (IOException e) {
            fail("Unexpected error thrown");
        }
    }

    @Test
    void getBoards_return_correct_boards() {
        final String boardsData = """
                [
                    {
                        "id": "b",
                        "name": "Бред",
                        "category": "Разное"
                    },
                    {
                        "id": "news"
                    }
                ]
                """;

        try {
            when(client2ch.fetchBoards()).thenReturn(boardsData); // Stub role

            final Board[] boards = api2ch.getBoards();

            assertEquals(2, boards.length);
            assertEquals("b", boards[0].getId());
            assertEquals("Бред", boards[0].getName());
            assertEquals("news", boards[1].getId());
            assertEquals("", boards[1].getName());
        } catch (IOException e) {
            fail("Unexpected error thrown");
        }
    }

    @Test
    void getThreadsByBoardId_call_Client2ch_fetchBoardWithThreads_once() {
        try {
            when(client2ch.fetchBoardWithThreads("b")).thenReturn("{}");

            api2ch.getThreadsByBoardId("b");
            verify(client2ch, times(1)).fetchBoardWithThreads("b"); // Mock role
        } catch (IOException e) {
            fail("Unexpected error thrown");
        }
    }

    @Test
    void getThreadsByBoardId_return_correct_threads() {
        final String boardData = """
                {
                    "id": "b",
                    "name": "Бред",
                    "category": "Разное",
                    "threads": [
                        {"date": "01/10/22 Сб 11:12:13"},
                        {"date": "01/10/22 Сб 11:12:13"},
                        {"date": "01/10/22 Сб 11:12:00"}
                    ]
                }
                """;
        try {
            when(client2ch.fetchBoardWithThreads("b")).thenReturn(boardData); // Stub role
            when(client2ch.fetchBoardWithThreads("a")).thenReturn("{}");

            final Thread[] threads = api2ch.getThreadsByBoardId("b");

            assertEquals(3, threads.length);

            final Calendar cal = Calendar.getInstance();
            cal.clear();
            cal.set(2022, Calendar.OCTOBER, 1, 11, 12, 13);

            assertEquals(cal.getTimeInMillis(), threads[0].getDate().getTime());
        } catch (IOException e) {
            fail("Unexpected error thrown");
        }
    }
}
