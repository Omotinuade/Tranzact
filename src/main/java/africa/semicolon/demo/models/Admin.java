package africa.semicolon.demo.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter@Entity
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne
    private BioData biodata;
}
