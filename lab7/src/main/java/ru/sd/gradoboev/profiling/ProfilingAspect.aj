package ru.sd.gradoboev.profiling;

import org.aspectj.lang.JoinPoint;

import java.util.Objects;

public aspect ProfilingAspect {
    static final String METHOD_CALL = "method-execution";

    pointcut traceAnnotatedClasses(): within(@ru.sd.gradoboev.profiling.Profiled *)
            || execution(* ru.sd.gradoboev.profiledWithPath.ClassB.*(..));

    Object around(): traceAnnotatedClasses() {
        if (!isFCall(thisJoinPoint)) {
            return proceed();
        }

        long start = System.nanoTime();
        Object result = proceed();
        long executionTime = System.nanoTime() - start;

        String signature = thisJoinPoint.getSignature().toString()
                .split(" ")[1];
        Statistics.addRecord(signature, executionTime);

        return result;
    }

    private boolean isFCall(JoinPoint point) {
        return Objects.equals(point.getKind(), METHOD_CALL);
    }
}
