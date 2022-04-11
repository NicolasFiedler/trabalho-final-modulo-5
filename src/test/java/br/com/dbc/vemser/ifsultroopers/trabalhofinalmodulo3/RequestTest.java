package br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3;

import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
@SpringBootTest
public class RequestTest {
    @Autowired
    public RequestService requestService;



    @Test
    public void testCheckClosedValueFalse(){
        Boolean flag = requestService.checkClosedValue(1000.0, 50.0);
        assertFalse(flag);
    }

    @Test
    public void testCheckClosedValueTrue(){
        Boolean flag = requestService.checkClosedValue(50.0, 1000.0);
        assertTrue(flag);
    }

}
