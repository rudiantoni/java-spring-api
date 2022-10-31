package com.myapps.javaspringapi.modules.security.service;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

// Filtro aplicado para qualquer endpoint que não seja o login
public class AuthenticationFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = AuthenticationService.getAuthentication((HttpServletRequest) request);
        SecurityContextHolder
                .getContext()
                .setAuthentication(usernamePasswordAuthenticationToken);
        filterChain.doFilter(request, response);
    }


}
