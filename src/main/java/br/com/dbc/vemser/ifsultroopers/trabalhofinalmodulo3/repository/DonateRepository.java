package br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.repository;
import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.entity.Category;
import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.entity.DonateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DonateRepository extends JpaRepository<DonateEntity, Integer> {

    List<DonateEntity> findByIdRequest(Integer idRequest);

    List<DonateEntity> findByDonatorNameContainingIgnoreCase(String donatorName);

}
