package com.example.MyWallWebApi.Security.JWT;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.SneakyThrows;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class JwtUtil {

    // Chave com algoritmo HS512
    // http://www.allkeysgenerator.com              //base 64
    private static final String JWT_SECRET = "bjJyNXU4eC9BJURHLUthUGRTZ1ZrWXAzczZ2OXkkQiZFKEgrTWJRZVRoV21acTR0N3cheiVDRi1KQE5jUmY=";

    @SneakyThrows
    public static Claims getClaims(String token) {
        byte[] signingKey = JwtUtil.JWT_SECRET.getBytes("UTF-8");

        token = token.replace("Bearer ", "");

        return Jwts.parser()
                .setSigningKey(signingKey)
                .parseClaimsJws(token).getBody();
    }

    public static String getLogin(String token) {
        Claims claims = getClaims(token);
        if (!isNull(claims)) {
            return claims.getSubject();
        }
        return null;
    }

    public static List<GrantedAuthority> getRoles(String token) {
        Claims claims = getClaims(token);
        if (claims == null) {
            return null;
        }
        return ((List<?>) claims
                .get("rol")).stream()
                .map(authority -> new SimpleGrantedAuthority((String) authority))
                .collect(Collectors.toList());
    }

    public static boolean isTokenValid(String token) {
        Claims claims = getClaims(token);
        if (nonNull(claims)) {
            String login = claims.getSubject();
            Date expiration = claims.getExpiration();
            Date now = new Date(System.currentTimeMillis());
            return login != null && expiration != null && now.before(expiration);
        }
        return false;
    }

    @SneakyThrows
    public static String createToken(UserDetails user) {
        List<String> roles = user.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        int days = 10;
        long time = days * 24 /*horas*/ * 60 /*min*/ * 60 /*seg*/ * 1000  /*milis*/;
        Date expiration = new Date(System.currentTimeMillis() + time);
//      System.out.println(expiration);

        byte[] signingKey = JwtUtil.JWT_SECRET.getBytes(StandardCharsets.UTF_8);
        return Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(signingKey), SignatureAlgorithm.HS512)
                .setSubject(user.getUsername())
                .setExpiration(expiration)
                .claim("rol", roles)
                .compact();
    }

    public static String getAuthLogin() {
        UserDetails user = getUserDetails();
        if(user != null){
            return user.getUsername();
        }
        return null;
    }

    public static UserDetails getUserDetails(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth != null && auth.getPrincipal() != null){
            return (UserDetails) auth.getPrincipal();
        }
        return null;
    }

}
