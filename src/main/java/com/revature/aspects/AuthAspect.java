package com.revature.aspects;

import com.revature.annotations.Authorized;
import com.revature.dtos.UserDTO;
import com.revature.exceptions.NotLoggedInException;
import com.revature.services.TokenService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Aspect
@Component
public class AuthAspect {

    private final HttpServletRequest req;
    private TokenService ts;

    public AuthAspect(HttpServletRequest req, TokenService ts) {
        this.req = req;
        this.ts = ts;
    }

    @Around("@annotation(authorized)")
    public Object authenticate(ProceedingJoinPoint pjp, Authorized authorized) throws Throwable {

        String token = req.getHeader("Authorization");
        UserDTO userDetails = ts.extractTokenDetails(token);

        Object[] args = pjp.getArgs();

        if(args == null) {
            //CREATE AN AUTH EXCEPTION RICHARD >:(
            throw new NotLoggedInException();
        }

        Integer id = (Integer) args[0];

        return pjp.proceed();
    }
}
