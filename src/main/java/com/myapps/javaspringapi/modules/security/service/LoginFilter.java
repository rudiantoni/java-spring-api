package com.myapps.javaspringapi.modules.security.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myapps.javaspringapi.modules.security.dto.UserDto;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.util.Collections.emptyList;

// Filtro aplicado apenas para o endpoint de login post
public class LoginFilter extends AbstractAuthenticationProcessingFilter {

    public LoginFilter(String url, AuthenticationManager authenticationManager) {
        super (new AntPathRequestMatcher(url));
        setAuthenticationManager(authenticationManager);
    }

    // Método executado para tentar se autenticar
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
        UserDto userDto = new ObjectMapper()
                .readValue(request.getInputStream(), UserDto.class);
        return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(
                userDto.getEmail(), userDto.getPassword(), emptyList()));

    }

    // Método executado ao ter se autenticado com sucesso
    @Override
    protected void successfulAuthentication(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, Authentication authentication)
            throws IOException, ServletException {
        super.successfulAuthentication(request, response, filterChain, authentication);
        AuthenticationService.addToken(response, authentication.getName());
    }

}
