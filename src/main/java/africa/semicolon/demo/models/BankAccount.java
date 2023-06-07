package africa.semicolon.demo.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity@Getter@Setter
public class BankAccount {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String accountNumber;
    private String accountName;
    private String bankName;
}
