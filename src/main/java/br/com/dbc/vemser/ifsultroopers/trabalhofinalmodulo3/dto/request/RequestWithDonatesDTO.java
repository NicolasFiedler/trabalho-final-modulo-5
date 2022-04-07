package br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.dto.request;

import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.dto.donate.DonateDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestWithDonatesDTO extends RequestDTO{
    List<DonateDTO> donateDTOList;
}
