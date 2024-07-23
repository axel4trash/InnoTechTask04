package edu.innotech;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@Aspect
@Component
public class LoggerAspect {

    @Pointcut("@annotation(LogTransformation)")
    public void loggableMethod() {
    }

    @Around("loggableMethod()")
    public Object logTransformation(ProceedingJoinPoint joinPoint) throws Throwable {

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getName();

        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        try {
            return joinPoint.proceed();
        } finally {
            stopWatch.stop();
            System.out.println("Transformation time for " + className + "." + methodName + " :: " + stopWatch.getTotalTimeMillis() + " ms");
            LogSaver.saveToLog("Запись данных в БД. Время: " + new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime()));
        }
    }
}
