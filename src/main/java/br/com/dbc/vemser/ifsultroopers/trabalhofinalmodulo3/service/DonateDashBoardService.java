package br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.service;

import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.dto.dashboard.donate.DonateDashBoardDTO;
import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.entity.Category;
import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.repository.DonateDashBoardRepository;
import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.repository.DonateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DonateDashBoardService {

    private final DonateDashBoardRepository donateDashBoardRepository;

    public List<DonateDashBoardDTO> donatesDashBoard() throws Exception {
        return donateDashBoardRepository.donatesDashBoard();
    }

}
