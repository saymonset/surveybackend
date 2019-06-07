package com.repository.mongo;

/**
 * Created by simon on 6/4/2019.
 */
import com.enums.RolNombre;
import com.model.mongo.Company;
import com.model.mongo.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolRepository extends CrudRepository<Rol, Long> {
    Optional<Rol> findByRolNombreAndCompany(RolNombre rolNombre, Company company);
}