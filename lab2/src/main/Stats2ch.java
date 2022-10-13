package main;

import java.time.Duration;
import java.util.Date;

public class Stats2ch {
    public static int[] threadDistributionByHour(final Thread[] threads, final int hours) {
        final int[] distribution = new int[hours];

        Date hourEnd = new Date();
        Date hourStart = shiftBackOneHour(hourEnd);

        for (int i = 0; i < hours; i++) {
            distribution[i] = 0;
            for (final Thread thread : threads) {
                final Date date = thread.getDate();
                if (date.after(hourStart) && date.before(hourEnd)) {
                    distribution[i]++;
                }
            }

            hourEnd = hourStart;
            hourStart = shiftBackOneHour(hourEnd);
        }

        return distribution;
    }

    private static Date shiftBackOneHour(final Date date) {
        final long hourMS = Duration.ofHours(1).toMillis();

        return new Date(date.getTime() - hourMS);
    }
}
