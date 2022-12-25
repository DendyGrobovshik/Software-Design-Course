package ru.sd.gradoboev.profiling;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Statistics {
    static final Map<String, MethodStat> execs = new HashMap<>();

    public static void addRecord(String signature, long executionTime) {
        if (!execs.containsKey(signature)) {
            execs.put(signature, new MethodStat());
        }

        execs.get(signature).addCall(executionTime);
    }

    public static void dumpRecords() {
        for (Map.Entry<String, MethodStat> entry : execs.entrySet()) {
            MethodStat stat = entry.getValue();
            System.out.println(
                    "Method - " + entry.getKey()
                            + "\n  called: " + stat.calls
                            + "\n  with total time: " + stat.totalTime
                            + "\n  with average time: " + stat.averageTime());
        }
    }

    public static void printTree() {
        print("", 0);
    }

    private static void print(String prefix, int level) {
        Set<String> nextPrefixes = new HashSet<>();
        Set<String> endPoints = new HashSet<>();

        for (String s : execs.keySet()) {
            if (s.startsWith(prefix)) {
                int nextPrefix = s.indexOf(".", prefix.length());
                if (nextPrefix != -1) {
                    nextPrefixes.add(s.substring(0, nextPrefix + 1));
                } else {
                    MethodStat stat = execs.get(s);
                    endPoints.add(s.substring(prefix.length())
                            + " - called(" + stat.calls
                            + ") with total time(" + stat.totalTime
                            + ") and average time(" + stat.averageTime() + ")"
                    );
                }
            }
        }

        for (String endPoint : endPoints) {
            printPrefix(level, '-');
            System.out.println(endPoint);
        }

        for (String nextPrefix : nextPrefixes) {
            printPrefix(level, '|');
            System.out.println(nextPrefix.substring(prefix.length()));
            print(nextPrefix, level + 1);
        }
    }

    private static void printPrefix(long level, char end) {
        for (int i = 0; i < level; i++) {
            System.out.print(' ');
        }
        System.out.print(end);
    }

    private static class MethodStat {
        long calls = 0;
        long totalTime = 0;

        public void addCall(long executionTime) {
            calls++;
            totalTime += executionTime;
        }

        public long averageTime() {
            return totalTime / calls;
        }
    }
}
