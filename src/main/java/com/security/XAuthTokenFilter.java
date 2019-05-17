package com.security;
import com.exceptions.ServiceErrorRestException;
import org.springframework.security.core.userdetails.UserDetailsService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * Created by simon on 5/16/2019.
 */

/**
 * Filters incoming requests and installs a Spring Security principal if a
 * header corresponding to a valid user is found.
 */
public class XAuthTokenFilter extends GenericFilterBean {

    private final static Logger LOG = LoggerFactory.getLogger(TokenProvider.class);
    private final static String XAUTH_TOKEN_HEADER_NAME = "Authorization";

    private UserDetailsService detailsService;
    private TokenProvider tokenProvider;

    public XAuthTokenFilter(UserDetailsService detailsService, TokenProvider tokenProvider) {
        this.detailsService = detailsService;
        this.tokenProvider = tokenProvider;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        try {
            HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
            HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

            String authHeader = httpServletRequest.getHeader(XAUTH_TOKEN_HEADER_NAME);


            if (StringUtils.isNotEmpty(authHeader) && authHeader.startsWith("Bearer ")) {
                String authToken = authHeader.substring(7);

                try {
                    String username = this.tokenProvider.validateToken(authToken);
                    loadAuthentication(username);

                } catch (TokenExpiredException | ExpiredJwtException ee) {
                    LOG.warn(String.format("El token %s se encuentra expirado", authToken), ee);

                } catch (MalformedJwtException | SignatureException e) {
                    LOG.warn(String.format("El token %s es inválido", authToken), e);

                } catch (TokenToBeExpiredException ttee){
                    LOG.warn(String.format("El token %s será expirado próximamente", authToken), ttee);
                    String username = this.tokenProvider.getUserNameFromToken(authToken);
                    httpServletResponse.addHeader("token-expiration", Integer.toString(this.tokenProvider.getSecondsToExpireInvalidateUser(username)));
                    loadAuthentication(this.tokenProvider.getUserNameFromToken(authToken));
                }
            }
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (Exception ex) {
            throw new ServiceErrorRestException(ex);
        }
    }

    /**
     * Obtiene el {@link UserDetails} del usuario en base al nombre de usuario.
     * Carga la informacion del {@link UserDetails} dentro del contexto de
     * spring security para asignar los permisos del usuario.
     *
     * @param username
     */
    private void loadAuthentication(String username) {
        UserDetails details = this.detailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(details,
                details.getPassword(), details.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(token);
    }
}
