package main;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        final Client2ch client2ch = new Client2ch();
        final API2ch api2ch = new API2ch(client2ch);

        final String boardId = readBoardId(api2ch);
        final int period = readPeriod();

        final Thread[] threads = api2ch.getThreadsByBoardId(boardId);

        final int[] distribution = Stats2ch.threadDistributionByHour(threads, period);

        System.out.println("Threads count from present to past");
        System.out.println(Arrays.toString(distribution));
    }

    private static String readBoardId(API2ch api2ch) throws IOException {
        final Scanner in = new Scanner(System.in);

        System.out.println("Popular boards: \"b\", \"po\", \"news\"");
        System.out.println("Need to show a list of all boards?(Yes/No)");
        final String needShowBoards = in.nextLine();

        if (needShowBoards.trim().equalsIgnoreCase("yes")) {
            final Board[] boards = api2ch.getBoards();

            for (final Board board : boards) {
                System.out.println(
                        board.getName()
                                .concat("[")
                                .concat(board.getId())
                                .concat("]")
                );
            }
        }

        System.out.println("main.Board ID:");
        return in.nextLine();
    }

    private static int readPeriod() {
        final Scanner in = new Scanner(System.in);

        while (true) {
            System.out.println("Period in hours 1 <= N <= 24");
            String str = in.nextLine();
            final int period = Integer.parseInt(str);
            if (period >= 1 && period <= 24) {
                return period;
            }
        }
    }
}