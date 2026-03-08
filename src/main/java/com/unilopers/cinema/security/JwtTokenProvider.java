package com.unilopers.cinema.security;

import com.unilopers.cinema.model.Usuario;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final String secret = "CHAVE987654321012345678901234567890";
    private final long validadeEmMilissegundos = 1000 * 60 * 60;

    private final Key key = Keys.hmacShaKeyFor(secret.getBytes());

    public String gerarToken(Usuario usuario) {

        Date agora = new Date();
        Date validade = new Date(agora.getTime() + validadeEmMilissegundos);

        return Jwts.builder()
                .setSubject(usuario.getEmail())
                .claim("roles", usuario.getRoles())
                .setIssuedAt(agora)
                .setExpiration(validade)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String obterUsernameDoToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public boolean validarToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
