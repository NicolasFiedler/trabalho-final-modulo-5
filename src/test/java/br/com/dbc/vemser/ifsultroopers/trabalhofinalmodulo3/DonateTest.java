package br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3;

import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.service.DonateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;
@SpringBootTest
public class DonateTest{

    @Autowired
    public DonateService donateService;

    @Test
    public void testaValorPraAtualizarNoRequest() {
        Boolean flag = donateService.updateValor(1000.0,50.0)<0 && donateService.updateValor(5.0,1000.0)>0;
        assertTrue(flag);
    }

    @Test
    public void testaValorPraDeletarDoRequest() {
        Boolean flag = donateService.deleteValor(1000.0)==(-1000);
        assertTrue(flag);
    }

}
