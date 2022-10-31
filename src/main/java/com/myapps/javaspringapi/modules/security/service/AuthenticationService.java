package com.myapps.javaspringapi.modules.security.service;

import com.myapps.javaspringapi.modules.util.DateTimeUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Date;

import static java.util.Collections.emptyList;

// Componente responsavel por manipular o JwtToken
public class AuthenticationService {

    // Tempo de expiração do token em milissegundoss
    // 3600000ms = 1h
    static final Long EXPIRATION_TIME = 3600000L;
    // Chave de assinatura do token
    static final String SIGNING_KEY = "SecretKey";
    // Prefixo do valor do cabeçalho de autenticação da requisição
    static final String PREFIX = "Bearer";

    // Cria e adiciona o Token ao cabeçalho das
    // requisições HTTP quando o usuário conseguir se autenticar
    static public void addToken(HttpServletResponse response, String email) {
        Date expiration = DateTimeUtil.getDateFromLocalDateTime(LocalDateTime.now().plusNanos(EXPIRATION_TIME));

        String jwtToken = Jwts.builder()
                .setSubject(email)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS512, SIGNING_KEY)
                .compact();
        response.addHeader("Authorization", PREFIX + " " + jwtToken);
        response.addHeader("Access-Control-Expose-Headers", "Authorization");
    }

    // Método usando quando é enviado o token, a princípio seria para quando alguém já está autenticado
    static public UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String fullToken = request.getHeader("Authorization");
        if (fullToken != null) {
            String email = Jwts.parser()
                    .setSigningKey(SIGNING_KEY)
                    .parseClaimsJws(fullToken.replace(PREFIX, ""))
                    .getBody()
                    .getSubject();

            if (email != null)
                return new UsernamePasswordAuthenticationToken(email, null, emptyList());

        }
        return null;
    }


}
