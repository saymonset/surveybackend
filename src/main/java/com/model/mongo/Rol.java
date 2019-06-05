package com.model.mongo;

import com.enums.RolNombre;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

/**
 * Created by simon on 6/4/2019.
 */
@Document
public class Rol {
    @Id
    private String id;
    @Enumerated(EnumType.STRING)
    @NotNull
    private RolNombre rolNombre;
    public Rol() {
    }

    public Rol(@NotNull RolNombre rolNombre) {
        this.setRolNombre(rolNombre);
    }



    public RolNombre getRolNombre() {
        return rolNombre;
    }

    public void setRolNombre(RolNombre rolNombre) {
        this.rolNombre = rolNombre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
