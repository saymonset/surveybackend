package com.security;

/**
 * Created by simon on 5/16/2019.
 */

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    public static final String HEADER_AUTHORIZATION = "Authorization";

    public static final String ADMIN = "ROLE_ADMIN";
    public static final String USER = "ROLE_USER";
    public static final String ANONYMOUS = "ROLE_ANONYMOUS";
    public static final String PERMISSION_PREFIX = "ROLE_PERMISSION_";
    public static final String ROLE_PREFIX = "ROLE_";

    public static final String ROLE_PERMISSION_READ_MAIL = "ROLE_PERMISSION_READ_MAIL";
    public static final String ROLE_PERMISSION_WRITE_MAIL = "ROLE_PERMISSION_WRITE_MAIL";
    public static final String ROLE_JBPM = "ROLE_JBPM";

    public static final String ROLE_PERMISSION_READ_USER = "ROLE_PERMISSION_READ_USER";
    public static final String ROLE_PERMISSION_WRITE_USER = "ROLE_PERMISSION_WRITE_USER";
    public static final String ROLE_PERMISSION_READ_PAIS = "ROLE_PERMISSION_READ_PAIS";
    public static final String ROLE_PERMISSION_WRITE_PAIS = "ROLE_PERMISSION_WRITE_PAIS";
    public static final String ROLE_PERMISSION_READ_ROL = "ROLE_PERMISSION_READ_ROL";
    public static final String ROLE_PERMISSION_WRITE_ROL = "ROLE_PERMISSION_WRITE_ROL";


    private AuthoritiesConstants() {
    }
}
