package webrise.test.subscriptions_service.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class ServiceLoggingAspect {

    @Pointcut("@within(org.springframework.stereotype.Service)")
    public void serviceMethods() {}

    @Around("serviceMethods()")
    public Object logService(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().toShortString();
        long startTime = System.currentTimeMillis();

        try {
            Object result = joinPoint.proceed();
            long duration = System.currentTimeMillis() - startTime;
            log.info("Service {} executed in {} ms", methodName, duration);
            return result;
        } catch (Throwable ex) {
            log.error("Service exception in {}: {}", methodName, ex.getMessage(), ex);
            throw ex;
        }
    }
}

