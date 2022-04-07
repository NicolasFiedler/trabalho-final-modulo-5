package br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@Entity(name = "ROLES")
public class RoleEntity implements Serializable, GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "roles_seq")
    @SequenceGenerator(name = "roles_seq", sequenceName = "roles_seq", allocationSize = 1)
    @Column(name = "id_role")
    private Integer idRole;

    @Column(name = "name")
    private String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "roles")
    private Set<UsersEntity> users;

    @Override
    public String getAuthority() {
        return name;
    }
}
