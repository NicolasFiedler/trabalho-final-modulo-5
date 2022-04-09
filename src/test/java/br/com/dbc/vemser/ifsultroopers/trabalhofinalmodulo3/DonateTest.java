package br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3;

import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.repository.DonateRepository;
import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.service.DonateService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertTrue;


@RunWith(MockitoJUnitRunner.class)
public class DonateTest{

    @InjectMocks
    public final DonateService donateService;
    @Mock
    public final DonateRepository donateRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void BeforeEach() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(donateService,"objectMapper",objectMapper);
    }


    @Test
    public void testaValorPraAtualizarNoRequest() {
        Boolean flag = donateService.updateValor(1000.0,50.0)<0 && donateService.updateValor(5.0,1000.0)>0;
        assertTrue(flag);
    }
//
//    @Test
//    public void testaValorPraDeletarDoRequest() {
//        Boolean flag = donateService.deleteValor(1000.0)==(-1000);
//        assertTrue(flag);
//    }

}
