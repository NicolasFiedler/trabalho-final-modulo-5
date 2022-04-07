package br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "BANK_ACCOUNT")
public class BankAccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bank_account_seq")
    @SequenceGenerator(name = "bank_account_seq", sequenceName = "bank_account_seq", allocationSize = 1)
    @Column(name = "id_bank_account")
    private Integer idBankAccount;

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "agency")
    private String agency;

    @JsonIgnore
    @OneToMany(mappedBy = "bankAccountEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RequestEntity> requests;

}
