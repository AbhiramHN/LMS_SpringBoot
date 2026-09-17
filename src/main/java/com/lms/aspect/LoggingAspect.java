package com.lms.aspect;

import com.lms.dto.request.LeaveApplicationRequest;
import com.lms.dto.request.LoginRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect
{
    @Around("execution(* com.lms.service..*(..))")
    public Object logServiceMethods(ProceedingJoinPoint joinPoint) throws Throwable
    {
        Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass());

        String methodName = joinPoint.getSignature().getName();
        String employeeId = getEmployeeId(joinPoint);

        logger.info("Method started: {}, employeeId = {}", methodName, employeeId);

        try
        {
            Object result = joinPoint.proceed();

            logger.info("Method completed: {}, employeeId = {}", methodName, employeeId
            );

            return result;
        }
        catch (Exception exception)
        {
            logger.error("Method failed: {}, employeeId = {}, reason = {}", methodName, employeeId, exception.getMessage()
            );

            throw exception;
        }
    }

    private String getEmployeeId(ProceedingJoinPoint joinPoint)
    {
        String methodName = joinPoint.getSignature().getName();

        for (Object argument : joinPoint.getArgs())
        {
            if (argument instanceof LoginRequest request)
            {
                return request.getEmployeeId();
            }

            if (argument instanceof LeaveApplicationRequest request)
            {
                return request.getEmployeeId();
            }

            if (argument instanceof String employeeId &&
                    (methodName.equals("getLeaveHistory") ||
                            methodName.equals("getLeaveBalances")))
            {
                return employeeId;
            }
        }

        return "N/A";
    }
}