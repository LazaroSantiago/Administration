package service;

import dto.ScooterDTO;
import entity.Administrator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import repository.AdministratorRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdministratorService implements BaseService<Administrator> {

    private final AdministratorRepository administratorRepository;

    private final RestTemplate restTemplate;

    @Autowired
    public AdministratorService(AdministratorRepository administratorRepository, RestTemplateBuilder builder) {
        this.administratorRepository = administratorRepository;
        this.restTemplate = builder.build();
    }

    @Override
    public List<Administrator> findAll() {
        return administratorRepository.findAll();
    }

    @Override
    public Administrator findById(Long id) throws Exception {
        try {
            Optional<Administrator> result = administratorRepository.findById(id);
            if (result.isPresent())
                return result.get();
            else
                throw new Exception();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public boolean delete(Long id) throws Exception {
        try {
            if (administratorRepository.existsById(id)) {
                administratorRepository.deleteById(id);
                return true;
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Administrator save(Administrator entity) throws Exception {
        try {
            return this.administratorRepository.save(entity);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public ResponseEntity<?> banAccount(Long id) {
        String url = "localhost:8083/accounts/ban/{" + id + "}";
        HttpEntity<Void> requestEntity = getRequestEntity();
        return restTemplate.exchange(url, HttpMethod.PUT, requestEntity, String.class);
    }

    @Transactional
    public ResponseEntity<?> unbanAccount(Long id) {
        String url = "localhost:8083/accounts/unban/{" + id + "}";
        HttpEntity<Void> requestEntity = getRequestEntity();
        return restTemplate.exchange(url, HttpMethod.PUT, requestEntity, String.class);
    }

    private static HttpEntity<Void> getRequestEntity() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return requestEntity;
    }

    @Transactional
    public ResponseEntity<?> getFunctionalScooters(){
        String url = "localhost:8082/scooters/functional_scooters";
        return restTemplate.getForEntity(url, Integer[].class);
    }

    @Transactional
    public List<ScooterDTO> getScootersByTripAmount(Long trips, int year) throws Exception {
        //TODO: url
        String url = "localhost:8082/scooters/most_used_scooters/trips/{"+trips+"}/year/{"+year+"}}";
        ResponseEntity<ScooterDTO[]> responseEntity = restTemplate.getForEntity(url, ScooterDTO[].class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            ScooterDTO[] scooterArray = responseEntity.getBody();
            assert scooterArray != null;
            return Arrays.asList(scooterArray);
        } else {
            throw new Exception("Error al obtener datos del microservicio.");
        }
    }

    /*
    public Long getProfits(int month1, int month2, int year){
        //TODO: url
        //creo que se va a llamar a trip o recepit en vez de scooters
        String url = "localhost:8082/scooters/most_used_scooters/trips/{"+trips+"}/year/{"+year+"}}";
        ResponseEntity<Long> responseEntity = restTemplate.getForEntity(url, Long.class);
        return responseEntity.getBody();
    }*/

    //Como administrador quiero hacer un ajuste de precios, y que a partir de cierta fecha el sistema
    //habilite los nuevos precios
    @Transactional
    public ResponseEntity<?> setNormalTripPrice(LocalDateTime date, Long price) throws InterruptedException {
        long time = getTime(date);
        Thread.sleep(time);
        String url = "localhost:8082/scooters/normalRate/{"+price+"}";
        HttpEntity<Void> requestEntity = getRequestEntity();
        return restTemplate.exchange(url, HttpMethod.PUT, requestEntity, String.class);
    }

    @Transactional
    public ResponseEntity<?> setExtraTripPrice(LocalDateTime date, Long price) throws InterruptedException {
        long time = getTime(date);
        Thread.sleep(time);
        String url = "localhost:8082/scooters/extraRate/{"+price+"}";
        HttpEntity<Void> requestEntity = getRequestEntity();
        return restTemplate.exchange(url, HttpMethod.PUT, requestEntity, String.class);
    }

    private static long getTime(LocalDateTime date) {
        return ChronoUnit.MILLIS.between(LocalDateTime.now(), date);
    }
}
