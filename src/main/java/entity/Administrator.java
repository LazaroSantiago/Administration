package entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(value = "admin")
public class Administrator {

    @Id
    private Long id;

    private String name;

    public Administrator() {}

    public Administrator(Long id){
        this.id = id;
    }

}
