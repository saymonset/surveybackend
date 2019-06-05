package com.security;

/**
 * Created by simon on 3/25/2019.
 */
public class SecurityConstants {
    public static final String SECRET = "SecretKeyToGenJWTs";
    public static final long EXPIRATION_TIME = 864_000_000; // 10 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/users/sign-up";
    public static final String AUTHENTICATE = "/autenticate/user";
    public static final String DATAFILL = "/datafill/all";
    public static final String URL ="http://localhost:8180";
/*
    public static final String SEND_ENCUESTAS = "/survey/send";
    public static final String SENT_SURVEY = "/survey/searchSurvey*";
    public static final String SENT_RESULT = "/survey/sent/result*";*/

}
