package br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "DONATE")
public class DonateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DONATE_SEQ")
    @SequenceGenerator(name= "DONATE_SEQ", sequenceName = "donate_seq", allocationSize = 1)
    @Column(name = "id_donate")
    private Integer idDonate;

    @Column(name = "id_request", insertable = false, updatable = false)
    private Integer idRequest;

    @Column(name = "donator_name")
    private String donatorName;

    @Column(name = "donator_email")
    private String donatorEmail;

    @Column(name = "donate_value")
    private Double donateValue;

    @Column(name = "donate_description")
    private String description;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_request", referencedColumnName = "id_request")
    private RequestEntity requestEntity;

}