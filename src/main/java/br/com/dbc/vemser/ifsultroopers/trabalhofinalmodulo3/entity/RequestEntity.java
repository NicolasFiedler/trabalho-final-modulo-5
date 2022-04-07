package br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "REQUEST")
public class RequestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "REQUEST_SEQ")
    @SequenceGenerator(name= "REQUEST_SEQ", sequenceName = "REQUEST_SEQ", allocationSize = 1)
    @Column(name = "id_request")
    private Integer idRequest;

    @Column(name = "title")
    private String title;

    @Column(name = "request_description")
    private String description;

    @Column(name = "goal")
    private Double goal;

    @Column(name = "reached_value")
    private Double reachedValue;

    @Column(name = "id_user", insertable = false, updatable = false)
    private Integer idUser;

    @Column(name = "id_bank_account", insertable = false, updatable = false)
    private Integer idBankAccount;

    @Column(name = "status_request")
    private Boolean statusRequest;

    @Column(name = "id_category")
    @Enumerated(EnumType.ORDINAL)
    private Category category;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_bank_account", referencedColumnName = "id_bank_account")
    private BankAccountEntity bankAccountEntity;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", referencedColumnName = "id_user")
    private UsersEntity usersEntity;

    @JsonIgnore
    @OneToMany(mappedBy = "requestEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<DonateEntity> donates;
}