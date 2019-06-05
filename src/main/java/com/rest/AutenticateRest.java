package com.rest;

import com.codahale.metrics.annotation.Timed;
import com.dto.TokenDTO;
import com.dto.UserDTO;
import com.model.mysql.User;
import com.security_delete.SecurityUtils;
import com.security_delete.TokenProvider;
import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

/**
 * Created by simon on 5/16/2019.
 */
/*@RestController
@RequestMapping("/autenticate")*/
public class AutenticateRest {
  /*  private Logger logger =  LoggerFactory.getLogger(this.getClass().getName());
    @Inject
    private DozerBeanMapper dozerBeanMapper;
    @Inject
    private com.repository.mysql.UserRepository userRepository;
    @Inject
    private BCryptPasswordEncoder bCryptPasswordEncoder;
   *//* @Autowired
    private com.repository.mongo.UserRepository userMongoRepository;*//*

    @Inject
    private UserDetailsService userDetailsService;

    @Inject
    private TokenProvider tokenProvider;

    private UsernamePasswordAuthenticationToken externalAuthentication(String username){
        org.springframework.security.core.userdetails.User userDetails =
                (org.springframework.security.core.userdetails.User) getUserDetailsService().loadUserByUsername(username);

        UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(
                userDetails.getUsername(), userDetails.getPassword(),
                userDetails.getAuthorities());

        result.setDetails(userDetails);
        return result;
    }


    @Timed
    @PostMapping(value = "/user",  produces = MediaType.APPLICATION_JSON_VALUE, consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public TokenDTO authenticate(@RequestBody com.model.mysql.User user2) {
        TokenDTO tokenDTO = new TokenDTO();
        User user = getUserRepository().findByUsername(user2.getUsername());
        if(user == null){
            throw new BadCredentialsException("Los datos ingresados no son correctos");
        }
        UsernamePasswordAuthenticationToken authentication;
        UsernamePasswordAuthenticationToken token;
        if(user.getPassword() != null){
            UserDTO userDTO =   getDozerBeanMapper().map(user, UserDTO.class);
            String tokenOut =  getTokenProvider().createToken(userDTO);


            if(getbCryptPasswordEncoder().matches(user2.getPassword(), user.getPassword())){
                //Autentico usuario interno/LDAP
             *//*   authentication = this.externalAuthentication(user2.getUsername());
                SecurityContextHolder.getContext().setAuthentication(authentication);*//*



                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                securityContext.setAuthentication(new UsernamePasswordAuthenticationToken(user2.getUsername(), user.getPassword()));
                SecurityContextHolder.setContext(securityContext);
                String login = SecurityUtils.getCurrentLogin();


                tokenDTO.setSuccess(true);
                tokenDTO.setToken(tokenOut);
            }
            *//*else{
                throw new BadCredentialsException("Los datos ingresados no son correctos");
            }*//*
        }


        return tokenDTO;
    }

    public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public DozerBeanMapper getDozerBeanMapper() {
        return dozerBeanMapper;
    }

    public void setDozerBeanMapper(DozerBeanMapper dozerBeanMapper) {
        this.dozerBeanMapper = dozerBeanMapper;
    }

    public com.repository.mysql.UserRepository getUserRepository() {
        return userRepository;
    }

    public void setUserRepository(com.repository.mysql.UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public BCryptPasswordEncoder getbCryptPasswordEncoder() {
        return bCryptPasswordEncoder;
    }

    public void setbCryptPasswordEncoder(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }



    public UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public TokenProvider getTokenProvider() {
        return tokenProvider;
    }

    public void setTokenProvider(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }*/
}
