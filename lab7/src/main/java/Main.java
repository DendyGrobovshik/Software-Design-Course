import ru.sd.gradoboev.profiledWithAnnotation.ClassA;
import ru.sd.gradoboev.profiledWithPath.ClassB;
import ru.sd.gradoboev.profiling.Statistics;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        ClassA.boo();
        ClassA a = new ClassA();
        for (int i = 0; i < 10; i++) {
            a.foo();
        }

        ClassB b = new ClassB();
        for (int i = 0; i < 100; i++) {
            b.foo();
        }

        Statistics.dumpRecords();
        System.out.println("=====");
        Statistics.printTree();
    }
}
