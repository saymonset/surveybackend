package com.rest;

/**
 * Created by simon on 3/25/2019.
 */
import com.codahale.metrics.annotation.Timed;
import com.dto.TokenDTO;
import com.dto.UserDTO;
import com.model.UserMongo;
import com.repository.UserMongoRepository;
import com.repository.UserRepository;
import com.model.User;
import com.security.TokenProvider;
import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import java.util.Set;


@RestController
@RequestMapping("/users")
public class UserRest {
    Logger logger =  LoggerFactory.getLogger(this.getClass().getName());


    @Inject
    private DozerBeanMapper dozerBeanMapper;
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserMongoRepository userMongoRepository;

    @Inject
    private UserDetailsService userDetailsService;

    @Inject
    private TokenProvider tokenProvider;

    public UserRest(UserRepository applicationUserRepository,
                    BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = applicationUserRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping("/sign-up")
    public void signUp(@RequestBody User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setUsername(user.getUsername());
        userRepository.save(user);

        UserMongo userMongo =   getDozerBeanMapper().map(user, UserMongo.class);

        userMongoRepository.save(userMongo);
        logger.info("INGRESO USUARIO " + userMongo.getUsername());

        /**
         * public List<ListUserDTO> getActiveUsers() {
         return DozerHelper.map(dozerBeanMapper, userRepository.findAll(), ListUserDTO.class);
         }
         * */


    }


    private UsernamePasswordAuthenticationToken externalAuthentication(String username){
        org.springframework.security.core.userdetails.User userDetails =
                (org.springframework.security.core.userdetails.User) userDetailsService.loadUserByUsername(username);

        UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(
                userDetails.getUsername(), userDetails.getPassword(),
                userDetails.getAuthorities());

        result.setDetails(userDetails);
        return result;
    }


    @Timed
    @PostMapping(value = "/authenticate",  produces = MediaType.APPLICATION_JSON_VALUE, consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public TokenDTO authenticate(@RequestBody User  user2) {
        User user = userRepository.findByUsername(user2.getUsername());


        logger.info(" 1 username =" + user2.getUsername() +  ", password = "+ user.getPassword());
        if(user == null){
            throw new BadCredentialsException("Los datos ingresados no son correctos");
        }
        UsernamePasswordAuthenticationToken authentication;
        UsernamePasswordAuthenticationToken token;
        if(user.getPassword() != null){
            logger.info(" 2 username =" + user2.getUsername());
            if(bCryptPasswordEncoder.matches(user2.getPassword(), user.getPassword())){
                //Autentico usuario interno/LDAP
                authentication = this.externalAuthentication(user2.getUsername());
                SecurityContextHolder.getContext().setAuthentication(authentication);
                logger.info("Todo excele nte!!!");
            }else{
                throw new BadCredentialsException("Los datos ingresados no son correctos");
            }
        }

        UserDTO userDTO =   getDozerBeanMapper().map(user, UserDTO.class);
        logger.info(" 3 userDTO =" + userDTO.getUsername());
        String tokenOut =  tokenProvider.createToken(userDTO);
                logger.info("exit = " + tokenOut);
        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setSuccess(true);
        tokenDTO.setToken(tokenOut);
;        return tokenDTO;
    }




    public DozerBeanMapper getDozerBeanMapper() {
        return dozerBeanMapper;
    }

    public void setDozerBeanMapper(DozerBeanMapper dozerBeanMapper) {
        this.dozerBeanMapper = dozerBeanMapper;
    }
}