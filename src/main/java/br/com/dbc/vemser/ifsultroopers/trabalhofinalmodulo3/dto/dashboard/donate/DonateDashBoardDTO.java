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
    private String category;
    private Integer donates;
    private Double donatesValue;
}
