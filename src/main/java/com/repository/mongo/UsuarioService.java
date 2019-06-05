package com.repository.mongo;

/**
 * Created by simon on 6/4/2019.
 */
import com.model.mongo.Company;
import com.model.mongo.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UsuarioService {
    @Autowired
    UsuarioRepository usuarioRepository;

    public Optional<Usuario> getByNombreUsuario(String nu){
        return usuarioRepository.findByNombreUsuario(nu);
    }

    public boolean existePorNombre(String nu){
        return usuarioRepository.existsByNombreUsuario(nu);
    }

    public  boolean existePorEmail(String email){
        return usuarioRepository.existsByEmail(email);
    }
    public Optional<Usuario> findByEmailAndCompany(String email, Company company){
        return usuarioRepository.findByEmailAndCompany( email, company);
    }
    public void guardar(Usuario usuario){
        usuarioRepository.save(usuario);
    }
}
