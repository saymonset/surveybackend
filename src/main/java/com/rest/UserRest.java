package com.rest;

/**
 * Created by simon on 3/25/2019.
 */

import com.model.UserMongo;
import com.repository.UserMongoRepository;
import com.repository.UserRepository;
import com.model.User;
import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;


@RestController
@RequestMapping("/users")
public class UserRest {
    Logger logger =  LoggerFactory.getLogger(this.getClass().getName());



    @Inject
    private DozerBeanMapper dozerBeanMapper;
    private UserRepository applicationUserRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserMongoRepository userMongoRepository;

    public UserRest(UserRepository applicationUserRepository,
                    BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.applicationUserRepository = applicationUserRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping("/sign-up")
    public void signUp(@RequestBody User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setUsername(user.getUsername());
        applicationUserRepository.save(user);

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