package br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.repository;

import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.entity.RequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface RequestRepository extends JpaRepository<RequestEntity, Integer> {

    List<RequestEntity> findByStatusRequestIsTrue();
    List<RequestEntity> findByStatusRequestIsFalse();
    Optional<RequestEntity> findByIdUserAndIdRequest (Integer idUser, Integer idRequests);

}
