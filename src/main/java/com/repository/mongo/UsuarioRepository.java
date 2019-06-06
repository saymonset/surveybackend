package com.repository.mongo;
import com.model.mongo.Company;
import com.model.mongo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
/**
 * Created by simon on 6/4/2019.
 */
@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Long> {
    Optional<Usuario> findByNombreUsuario(String nu);

    boolean existsByNombreUsuario(String nu);
    boolean existsByEmail(String email);
    Optional<Usuario> findByEmailAndCompany(String email, Company company);
}