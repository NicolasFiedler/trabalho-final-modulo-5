package br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3;

import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.dto.donate.DonateCreateDTO;
import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.dto.request.RequestDTO;
import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.entity.DonateEntity;
import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.entity.RequestEntity;
import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.repository.DonateRepository;
import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.repository.RequestRepository;
import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.service.DonateService;
import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.service.RequestService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class DonateTest{

    @InjectMocks
    public DonateService donateService;
    @Mock
    public DonateRepository donateRepository;

    @Mock
    private RequestService requestService;

    @Mock
    private RequestRepository requestRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void BeforeEach() {
        ReflectionTestUtils.setField(donateService,"objectMapper",objectMapper);
    }

    @Test
    public void shouldSuccesfulCreate(){
        DonateCreateDTO donateCreateDTO = DonateCreateDTO.builder()
                .donatorName("Ana")
                .donateValue(50.0)
                .donatorEmail("anagocthel@gmail.com")
                .description(null)
                .build();
        DonateEntity donateEntity = DonateEntity.builder()
                .donatorName("Ana")
                .donateValue(50.0)
                .donatorEmail("anagocthel@gmail.com")
                .description(null)
                .idDonate(1)
                .build();
        RequestEntity requestEntity1 = RequestEntity.builder()
                .idRequest(1)
                .statusRequest(true)
                .build();

        donateEntity.setRequestEntity(requestEntity1);
        donateEntity.setIdRequest(1);
        Set<DonateEntity> donateEntitySet = new HashSet<>();
        donateEntitySet.add(donateEntity);
        requestEntity1.setDonates(donateEntitySet);

        try {
            when(requestRepository.getById(any(Integer.class))).thenReturn(requestEntity1);
            when(donateRepository.save(any(DonateEntity.class))).thenReturn(donateEntity);
            doNothing().when(requestService).incrementReachedValue(any(),any());
            doNothing().when(requestService).checkClosed(any());
            donateService.create(donateCreateDTO, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        verify(donateRepository,times(1)).save(any(DonateEntity.class));
    }

    @Test
    public void deleteDonate(){
        DonateEntity donateEntity = DonateEntity.builder()
                .donatorName("Ana")
                .donateValue(50.0)
                .donatorEmail("anagocthel@gmail.com")
                .description(null)
                .idDonate(1)
                .build();
        RequestEntity requestEntity1 = RequestEntity.builder()
                .idRequest(1)
                .statusRequest(true)
                .build();

        donateEntity.setRequestEntity(requestEntity1);
        donateEntity.setIdRequest(1);
        Set<DonateEntity> donateEntitySet = new HashSet<>();
        donateEntitySet.add(donateEntity);
        requestEntity1.setDonates(donateEntitySet);

        try {
            when(donateRepository.findById(any(Integer.class))).thenReturn(Optional.of(donateEntity));
            doNothing().when(donateRepository).deleteById(any(Integer.class));
            donateService.delete(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        verify(donateRepository,times(1)).deleteById(any(Integer.class));
    }



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
