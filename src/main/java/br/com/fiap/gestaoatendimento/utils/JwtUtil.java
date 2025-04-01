package br.com.fiap.gestaoatendimento.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import br.com.fiap.gestaoatendimento.model.UsuarioModel;

import java.util.Date;

@Component
public class JwtUtil {

    // Chave de segurança adicionada aqui apenas a fim academico,
    // em versão real deve ser armazenada e capturada de forma segura
    private static final SecretKey SECRET_KEY = Keys
            .hmacShaKeyFor("P4R4NG4R1CUT1R1M1RRU4R@P4R4NG4R1CUT1R1M1RRU4R@".getBytes());

    private String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // Token válido por 1 hora
                .signWith(SECRET_KEY)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Claims claims = extractClaims(token);
            return claims.getExpiration().after(new Date()); // Verifica a expiração
        } catch (Exception e) {
            return false;
        }
    }

    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    public String login(UsuarioModel user) {

        //Apenas a fim academico e ilustrativo, deve ser capturado de uma base de dados
        String usuario = "usuario";
        String senha = "senha";

        if (usuario.equals(user.getUsername()) && senha.equals(user.getPassword())) {
            return generateToken(user.getUsername());
        } else {
            return null;
        }
    }

}