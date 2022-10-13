package test;

import main.Thread;
import main.Stats2ch;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Date;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.*;

public class Stats2chTest {

    private Thread createThreadStubWithDateOffsetInMinutes(long offset) {
        final long offsetInMs = offset * Duration.ofMinutes(1).toMillis();
        final long now = new Date().getTime();
        final Date date = new Date(now - offsetInMs);

        final Thread stub = mock(Thread.class);
        when(stub.getDate()).thenReturn(date);

        return stub;
    }

    @Test
    void threadDistributionByHour_correct() {
        final Thread[] threads = new Thread[]{
                createThreadStubWithDateOffsetInMinutes(75),
                createThreadStubWithDateOffsetInMinutes(125),
                createThreadStubWithDateOffsetInMinutes(126),
        };

        final int[] distribution = Stats2ch.threadDistributionByHour(threads, 4);
        final int[] answer = new int[]{0, 1, 2, 0};

        assertArrayEquals(answer, distribution);
    }
}
