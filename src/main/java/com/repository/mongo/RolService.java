package com.repository.mongo;

/**
 * Created by simon on 6/4/2019.
 */
import com.enums.RolNombre;
import com.model.mongo.Rol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class RolService {

    @Autowired
    RolRepository rolRepository;

    public Optional<Rol> getByRolNombre(RolNombre rolNombre){
        return rolRepository.findByRolNombre(rolNombre);
    }

    public Rol save (Rol rol){
        return rolRepository.save(rol);
    }
}
