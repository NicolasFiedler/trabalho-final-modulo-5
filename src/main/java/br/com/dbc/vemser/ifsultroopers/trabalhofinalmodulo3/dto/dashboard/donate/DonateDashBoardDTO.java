package br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.dto.dashboard.donate;

import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DonateDashBoardDTO {
    private Integer idDonate;
    private Integer idVakinha;
    private Category category;
    private Double donateValue;
    private String donatorName;
    private String donatorEmail;
}
