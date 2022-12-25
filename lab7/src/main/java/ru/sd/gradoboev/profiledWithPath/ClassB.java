package ru.sd.gradoboev.profiledWithPath;

import ru.sd.gradoboev.profiling.Profiled;

public class ClassB {
    public void foo() throws InterruptedException {
        Thread.sleep(1);
        innerB.boo();
    }

    @Profiled
    public static class innerB {
        public static void boo() {
        }
    }
}
