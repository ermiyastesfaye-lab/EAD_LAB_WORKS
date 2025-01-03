package com.itsc;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class LoggingAspect {

    @Before("execution(* BookRegistrationServlet.*(..))")
    public void logBefore() {
        System.out.println("Method execution started.");
    }

    @After("execution(* BookRegistrationServlet.*(..))")
    public void logAfter() {
        System.out.println("Method execution completed.");
    }
}
