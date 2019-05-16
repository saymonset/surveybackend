package com.security;

import com.dto.UserDTO;
import io.jsonwebtoken.*;
import org.joda.time.DateTime;
import org.joda.time.Seconds;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.codec.Hex;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by simon on 5/15/2019.
 */
public class TokenProvider  {

    private static final String USER_CLAIM = "user";

    private final String secretKey;
    private final int tokenValidity;
    private final int notifyGraceSeconds;
    private Map<String, UserTokenStatus> usersToInvalidateToken = new HashMap<>();

    public TokenProvider(String secretKey, int tokenValidity, int notifyGraceSeconds) {
        this.secretKey = secretKey;
        this.tokenValidity = tokenValidity;
        this.notifyGraceSeconds = notifyGraceSeconds;
    }

    /**
     * Crea un nuevo token JWT con un UserDTO
     *
     * @param user
     * @return the JWT token
     */
    public String createToken(UserDTO user) {
        Date expires = DateTime.now().plusSeconds(tokenValidity).toDate();

        String authToken = Jwts.builder()
                .setExpiration(expires)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .claim(USER_CLAIM, user)
                .compact();

        /**
         *
         *
         *
         * @POST
         @Produces("application/json")
         @Consumes("application/x-www-form-urlencoded")
         public Response authenticateUser(@FormParam("username") String username,
         @FormParam("password") String password) {
         try {
         // Authenticate the user using the credentials provided
         // authenticate(username, password);
         // Issue a token for the user
         String compactJws = Jwts.builder().setSubject(username).signWith(SignatureAlgorithm.HS512, "pepe").compact();

         // Return the token on the response
         return Response.ok(compactJws).build();

         } catch (Exception e) {
         return Response.status(Response.Status.UNAUTHORIZED).build();
         }
         }
          *
          * */

        removeUserToInvaliateToken(user.getUsername());

        return authToken;
    }

    public String createToken(UserDetails userDetails) {
        Date expires = DateTime.now().plusSeconds(tokenValidity).toDate();

        String authToken = Jwts.builder()
                .setExpiration(expires)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .claim(USER_CLAIM, userDetails)
                .compact();

        removeUserToInvaliateToken(userDetails.getUsername());

        return authToken;


    }

    public String computeSignature(UserDetails userDetails, long expires) {
        StringBuilder signatureBuilder = new StringBuilder();
        signatureBuilder.append(userDetails.getUsername()).append(":");
        signatureBuilder.append(expires).append(":");
        signatureBuilder.append(secretKey);

        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("No MD5 algorithm available!", e);
        }
        return new String(Hex.encode(digest.digest(signatureBuilder.toString().getBytes())));
    }

    /**
     * Retorna el nombre de usuario de un token
     *
     * @param authToken
     * @return
     */
    public String getUserNameFromToken(String authToken) {
        return (String) Jwts.parser().setSigningKey(secretKey)
                .parseClaimsJws(authToken)
                .getBody()
                .get(USER_CLAIM, Map.class).get("login");
    }

    /**
     * Verica que el token es válido. Si alguna condición no cumple se retorna una excepción apropiada
     * @param authToken
     * @param user
     * @return El nombre login del usuario asociado al token
     *
     * @throws MalformedJwtException: Si el token es malformado
     * @throws SignatureException: Si la firma es inválida
     * @throws IllegalArgumentException: El algún argumento es inválido
     * @throws TokenExpiredException: Si el token se encuentra expirado por usuario invalidado
     * @throws TokenToBeExpiredException: Si el token se encuentra próximo a expirar
     */
    public String validateToken(String authToken) throws  MalformedJwtException,
            SignatureException, IllegalArgumentException, TokenExpiredException, TokenToBeExpiredException {
        String userName = getUserNameFromToken(authToken);
        isUserValid(userName);
        return userName;
    }

    /**
     * Verifica si el usuario ha sido invalidado. En caso de ser invalidado responde una excepción apropiada según el caso
     *
     * @param username
     * @param authToken
     * @throws TokenExpiredException: El usuario se encuentra invalidado y debe expirarse el token
     * @throws TokenToBeExpiredException: El usuario se encuentra invalidado y pronto se expirará su token
     */
    public synchronized void isUserValid(String username) throws TokenExpiredException, TokenToBeExpiredException {
        UserTokenStatus userTokenStatus = usersToInvalidateToken.get(username);

        if (userTokenStatus != null) {
            if (userTokenStatus.isNotified()){
                if (userTokenStatus.getNotifyTime().plusSeconds(notifyGraceSeconds).isBeforeNow()){
                    throw new TokenExpiredException();
                } else {
                    throw new TokenToBeExpiredException();
                }
            } else {
                userTokenStatus.setNotifyTime(new DateTime());
                userTokenStatus.setNotified(true);
                throw new TokenToBeExpiredException();
            }
        }
    }

    /**
     * Obtiene el tiempo de expiracion para un usuario invalidado
     * @param username
     * @return
     */
    public Integer getSecondsToExpireInvalidateUser(String username) {
        UserTokenStatus userTokenStatus = usersToInvalidateToken.get(username);
        Integer timeToExpire = null;
        if (userTokenStatus != null) {
            if (userTokenStatus.getNotifyTime().plusSeconds(notifyGraceSeconds).isBeforeNow()) {
                timeToExpire = 0;
            } else {

                timeToExpire = Seconds.secondsBetween(
                        DateTime.now(),
                        userTokenStatus.getNotifyTime().plusSeconds(notifyGraceSeconds))
                        .getSeconds();
            }
        }
        return timeToExpire;
    }

    /**
     * @return the secretKey
     */
    public String getSecretKey() {
        return secretKey;
    }

    /**
     * @return the tokenValidity
     */
    public int getTokenValidity() {
        return tokenValidity;
    }

    /**
     * Agrega un usuario al cual se debe invalidar el token
     * @param userId
     */
    public void addUserToInvaliateToken(String userId) {
        usersToInvalidateToken.put(userId, new UserTokenStatus(userId));
    }

    /**
     * Elimina un usuario de los que se deben invalidar el token
     * @param userId
     */
    public void removeUserToInvaliateToken(String userId) {
        usersToInvalidateToken.remove(userId);
    }

}
