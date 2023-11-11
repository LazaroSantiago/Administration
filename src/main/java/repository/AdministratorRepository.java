package repository;

import entity.Administrator;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository("AdministratorRepository")
public interface AdministratorRepository extends MongoRepository<Administrator, Long> {
}