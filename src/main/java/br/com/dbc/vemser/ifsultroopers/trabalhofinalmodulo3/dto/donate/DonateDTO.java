package br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.dto.donate;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DonateDTO extends DonateCreateDTO {

    @NotNull
    @ApiModelProperty(value = "Id do Doador")
    private Integer idDonate;

    @ApiModelProperty(value = "Id da Vakinha")
    @NotNull
    private Integer idRequest;

}
