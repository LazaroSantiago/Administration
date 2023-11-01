package service;

import entity.Administrator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import repository.AdministratorRepository;

import java.util.List;
import java.util.Optional;

@Service("AdministratorService")
public class AdministratorService implements BaseService<Administrator> {
    @Autowired
    private AdministratorRepository administratorRepository;

    private RestTemplate restTemplate;

    @Autowired
    public AdministratorService(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    @Override
    @Transactional
    public List<Administrator> findAll() {
        return administratorRepository.findAll();
    }

    @Override
    @Transactional
    public Administrator findById(Long id) throws Exception {
        try {
            Optional<Administrator> result = administratorRepository.findById(id);
            return result.get();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
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
    @Transactional
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
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return restTemplate.exchange(url, HttpMethod.PUT, requestEntity, String.class);

        //url, HttpMethod.PUT, headers, String.class

        //llamar al endpoint de Account
        //desde ahi, setear la cuenta como baneada
    }

    @Transactional
    public ResponseEntity<?> unbanAccount(Long id) {
        String url = "localhost:8083/accounts/unban/{" + id + "}";
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return restTemplate.exchange(url, HttpMethod.PUT, requestEntity, String.class);
    }
}
