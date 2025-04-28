package ma.ac.usmba.challenge02.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.Base64;

@Component
public class JwtUtil {

    // Clé secrète encodée en Base64
    private final String SECRET_KEY = "VmR6UmhOR3c5dXNMYXlyZ2JlOHZNU2szMlA0N0FwRG1qSlhZWmFiYzEyMzQ1Njc4OTA=";

    // Méthode pour extraire le nom d'utilisateur du jeton
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Méthode pour extraire la date d'expiration du jeton
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Méthode générique pour extraire une réclamation spécifique (ex: username, expiration)
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token); // Extraire toutes les réclamations
        return claimsResolver.apply(claims); // Appliquer le resolver à la réclamation extraite
    }

    // Extraire toutes les réclamations du jeton
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey()) // Utiliser la clé correcte pour la signature
                .build()
                .parseClaimsJws(token) // Décoder et vérifier le jeton
                .getBody(); // Retourner les réclamations
    }

    // Vérifier si le jeton est expiré
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date()); // Si la date d'expiration est avant aujourd'hui
    }

    // Générer un jeton JWT basé sur les détails de l'utilisateur
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();

        // Ajouter les rôles de l'utilisateur dans les réclamations
        claims.put("roles", userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority) // Récupérer les rôles sous forme de String
                .collect(Collectors.toList())); // Ajouter les rôles comme une liste

        // Créer et retourner le jeton
        return createToken(claims, userDetails.getUsername());
    }

    // Créer le jeton JWT avec les réclamations et le nom d'utilisateur
    private String createToken(Map<String, Object> claims, String username) {
        return Jwts.builder()
                .setClaims(claims) // Ajouter les réclamations
                .setSubject(username) // Ajouter le nom d'utilisateur
                .setIssuedAt(new Date(System.currentTimeMillis())) // Ajouter la date d'émission
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // Expiration dans 10 heures
                .signWith(SignatureAlgorithm.HS256, getSignKey()) // Signer avec l'algorithme HS256 et la clé secrète
                .compact(); // Retourner le jeton sous forme compacte
    }

    // Valider le jeton JWT en vérifiant le nom d'utilisateur et si le jeton est expiré
    public Boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token); // Extraire le nom d'utilisateur
        return (extractedUsername.equals(username) && !isTokenExpired(token)); // Comparer le nom d'utilisateur et vérifier si le jeton est expiré
    }

    // Obtenir la clé secrète pour signer et vérifier les signatures des jetons JWT
    private Key getSignKey() {
        byte[] keyBytes = Base64.getDecoder().decode(SECRET_KEY); // Décoder la clé Base64 en bytes
        return Keys.hmacShaKeyFor(keyBytes); // Créer la clé HMAC
    }

    // Extraire les rôles du jeton (c'est un champ custom "roles" que tu as ajouté)
    public List<String> extractRoles(String token) {
        Claims claims = extractAllClaims(token); // Extraire toutes les réclamations
        return claims.get("roles", List.class); // Retourner la liste des rôles
    }
}
