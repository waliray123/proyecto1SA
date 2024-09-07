package com.eatsleep.user.security.jwt;

import com.eatsleep.user.client.infrastructure.outputadapters.db.ClientDbEntity;
import com.eatsleep.user.client.infrastructure.outputadapters.db.ClientDbEntityRepository;
import com.eatsleep.user.employee.infrastructure.outputadapters.db.EmployeeDbEntity;
import com.eatsleep.user.employee.infrastructure.outputadapters.db.EmployeeDbEntityRepository;
import com.eatsleep.user.security.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;

@Service
public class JwtServiceImpl implements JwtService {

    public static final String SECRET_PHASE = "bmpJGkpYz0Af4ub65tzlnPRX2De1o02uuStUt2y1nhgAXzhngZJtWOgVAlOWYD41";

    public EmployeeDbEntityRepository employeeRepository;
    public ClientDbEntityRepository clientRepository;

    @Autowired
    public JwtServiceImpl(EmployeeDbEntityRepository employeeRepository, ClientDbEntityRepository clientRepository) {
        this.employeeRepository = employeeRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    public String generateToken(String username, Role role)     {
        return Jwts.builder()
                .claims(Collections.singletonMap("role", role))
                .subject(username)
                .expiration(new Date(System.currentTimeMillis() + 1800000))
                .issuedAt(new Date(System.currentTimeMillis()))
                .signWith(getSecretKey())
                .compact();
    }

    @Override
    public String getUsername(String token) {
        Claims claims = extractClaims(token);
        return claims.getSubject();
    }

    @Override
    public String getPayload(String token) {
        Claims claims = extractClaims(token);
        return claims.toString();
    }

    @Override
    public boolean isValid(String token) {
        Claims claims = extractClaims(token);
        Date expirationDate = claims.getExpiration();

        return new Date().before(expirationDate);
    }

    private Claims extractClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(getSecretKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    private SecretKey getSecretKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_PHASE);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public void updateTokenExpiration(String email) {
        //Buscamos por empleado sino existe buscamos por 
        Optional<ClientDbEntity> clientOpt = clientRepository.findByEmail(email);
        
        if(clientOpt.isPresent()){
            ClientDbEntity clientDbEntity = clientOpt.get();
            clientDbEntity.setExpirationDate(LocalDateTime.now().plusMinutes(15));
            clientRepository.save(clientDbEntity);
        }else{
            Optional<EmployeeDbEntity> employeeOpt = employeeRepository.findByEmail(email);
            EmployeeDbEntity employeeDbEntity = employeeOpt.get();
            employeeDbEntity.setExpirationDate(LocalDateTime.now().plusMinutes(15));
            employeeRepository.save(employeeDbEntity);
        }
    }

    public boolean isTokenExpired(String email) {
        Optional<ClientDbEntity> clientOpt = clientRepository.findByEmail(email);
        
        if(clientOpt.isPresent()){
            ClientDbEntity clientDbEntity = clientOpt.get();
            return clientDbEntity == null || LocalDateTime.now().isAfter(clientDbEntity.getExpirationDate());
        }else{
            Optional<EmployeeDbEntity> employeeOpt = employeeRepository.findByEmail(email);
            EmployeeDbEntity employeeDbEntity = employeeOpt.get();
            return employeeDbEntity == null || LocalDateTime.now().isAfter(employeeDbEntity.getExpirationDate());
        }
    }

}