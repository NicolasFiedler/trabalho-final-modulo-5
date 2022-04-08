package br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3;


import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.dto.request.RequestCreateDTO;
import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.entity.Category;
import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.exception.BusinessRuleException;
import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.service.RequestService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertNotNull;

@SpringBootTest
class TrabalhoFinalModulo3ApplicationTests {

	@Test
	void contextLoads() {
	}
	//NÃO TÁ PRONTO
	RequestService requestService = new RequestService();

//	@Test
//	void createRequestTest() throws BusinessRuleException {
//		RequestCreateDTO requestCreateDTO = new RequestCreateDTO("teste", "descrição teste",
//				10000.00, 1);
//
//		requestService.create(666, requestCreateDTO, Category.OUTROS);
//
//		assertEquals~~~
//	}

//	@Test
//	void closedRequestTest() throws BusinessRuleException {
//		requestService.findByStatusRequestIsFalse();
//	}

	//teste donate com o true/false das abertas fechadas?

//	@Test
//	void exceptionTest() throws BusinessRuleException {
//		try {
//			assertNotNull(requestService.getById(0));
//		} catch (BusinessRuleException b) {
//			b.printStackTrace();
//		}
//	}

//	@Test
//	void deleteRequestTest() throws BusinessRuleException {
//		requestService.delete(666);
//
//		assertEquals~~~
//	}

}
