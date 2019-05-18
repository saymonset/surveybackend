package com.rest;

/**
 * Created by simon on 3/25/2019.
 */
import com.model.mongo.User;
import com.repository.inject.MongoBd;
import com.repository.inject.MysqlBd;
import com.repository.mongo.UserRepository;
import com.security.TokenProvider;
import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;


@RestController
@RequestMapping("/users")
public class UserRest {
    Logger logger =  LoggerFactory.getLogger(this.getClass().getName());


    @Inject
    private DozerBeanMapper dozerBeanMapper;
    @Inject @MysqlBd
    private com.repository.mysql.UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
  /*  @Inject @MongoBd
    private com.repository.mongo.UserRepository userMongoRepository;*/

    @Inject
    private UserDetailsService userDetailsService;

    @Inject
    private TokenProvider tokenProvider;

    public UserRest(com.repository.mysql.UserRepository applicationUserRepository,
                    BCryptPasswordEncoder bCryptPasswordEncoder ) {
        this.userRepository = applicationUserRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
       // this.userMongoRepository = userMongoRepository;
    }

    @PostMapping("/sign-up")
    public void signUp(@RequestBody com.model.mysql.User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setUsername(user.getUsername());
        userRepository.save(user);

        com.model.mongo.User userMongo =   getDozerBeanMapper().map(user, com.model.mongo.User.class);

       // userMongoRepository.save(userMongo);
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