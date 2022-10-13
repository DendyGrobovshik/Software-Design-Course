package test;

import main.Thread;

import org.junit.jupiter.api.Test;

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ThreadTest {
    @Test
    public void setDate_correct() {
        final Thread thread = new Thread();
        thread.setDate("01/10/22 Сб 11:12:13");

        final Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(2022, Calendar.OCTOBER, 1, 11, 12, 13);

        assertEquals(cal.getTimeInMillis(), thread.getDate().getTime());
    }
}
