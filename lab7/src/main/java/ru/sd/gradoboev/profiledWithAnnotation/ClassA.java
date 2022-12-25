package ru.sd.gradoboev.profiledWithAnnotation;

import ru.sd.gradoboev.profiling.Profiled;

@Profiled
public class ClassA {
    public void foo() throws InterruptedException {
        Thread.sleep(3);
        boo();
    }

    public static boolean boo() throws InterruptedException {
        Thread.sleep(50);

        return true;
    }
}