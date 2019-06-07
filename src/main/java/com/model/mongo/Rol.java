package com.model.mongo;

import com.enums.RolNombre;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
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
    @DBRef
    private Company company;
    public Rol() {
    }

    public Rol(@NotNull RolNombre rolNombre, Company company) {
        this.setRolNombre(rolNombre);
        this.company = company;
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

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
