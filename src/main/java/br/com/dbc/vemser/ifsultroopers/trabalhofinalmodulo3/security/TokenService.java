package br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.security;

import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.entity.UsersEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TokenService {

    private static final String PREFIX = "Bearer ";
    private static final String HEADER_AUTHORIZATION = "Authorization";
    private static final String CHAVE_REGRAS = "REGRAS";

    @Value("${jwt.expiration}")
    private String expiration;

    @Value("${jwt.secret}")
    private String secret;

    public String getToken(Authentication authentication) {
        UsersEntity usuario = (UsersEntity) authentication.getPrincipal();

        Date now = new Date();
        Date exp = new Date(now.getTime() + Long.parseLong(expiration));

        List<String> regras = usuario.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        String token = Jwts.builder()
                .setIssuer("PrimeiroProjetoSpring")
                .setSubject(usuario.getIdUser().toString())
                .claim(CHAVE_REGRAS, regras)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();

        return PREFIX + token;
    }

    public Authentication getAuthentication(HttpServletRequest request) {
        String tokenBearer = request.getHeader(HEADER_AUTHORIZATION); // Bearer hfUIfs

        if (tokenBearer != null) {
            String token = tokenBearer.replace(PREFIX, "");
            Claims body = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
            String user = body.getSubject();
            if (user != null) {
                List<String> regras = body.get(CHAVE_REGRAS, List.class);
                List<SimpleGrantedAuthority> roles = regras.stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
                return new UsernamePasswordAuthenticationToken(user, null, roles);
            }
        }
        return null;
    }
}
