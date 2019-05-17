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







    public DozerBeanMapper getDozerBeanMapper() {
        return dozerBeanMapper;
    }

    public void setDozerBeanMapper(DozerBeanMapper dozerBeanMapper) {
        this.dozerBeanMapper = dozerBeanMapper;
    }
}