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

    public Administrator(Long id, String name){
        this.id = id;
        this.name = name;
    }

}
