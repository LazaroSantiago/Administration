package service;

import dto.CantidadScootersDto;
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

    //B
    @Transactional
    public ResponseEntity<CantidadScootersDto> getFunctionalScooters(){
        String url = "localhost:8082/scooter/report/activated";
        return restTemplate.getForEntity(url, CantidadScootersDto.class);
    }

    //C
    @Transactional
    public List<ScooterDTO> getScootersByTripAmount(Long trips, int year) throws Exception {
        String url = "localhost:8082/scooter/report/{"+trips+"}/{"+year+"}";
        ResponseEntity<ScooterDTO[]> responseEntity = restTemplate.getForEntity(url, ScooterDTO[].class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            ScooterDTO[] scooterArray = responseEntity.getBody();
            assert scooterArray != null;
            return Arrays.asList(scooterArray);
        } else {
            throw new Exception("Error al obtener datos del microservicio.");
        }
    }

    //D
    @Transactional
    public ResponseEntity<?> getProfits(int month1, int month2, int year){
        String url = "http://localhost:8082/trip/{"+month1+"}/{"+month2+"}/{"+year+"}";
        return restTemplate.getForEntity(url, Integer[].class);
    }

    //F
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
