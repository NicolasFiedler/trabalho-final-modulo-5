package br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.dto.bankaccount;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BankAccountCreateDTO {

    @NotNull @NotEmpty
    @ApiModelProperty(value = "Número da conta")
    private String accountNumber;

    @NotNull @NotEmpty
    @ApiModelProperty(value = "Número da agência")
    private String agency;

}
