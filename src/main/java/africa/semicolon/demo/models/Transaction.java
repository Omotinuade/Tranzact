package africa.semicolon.demo.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;
        private Long buyerId;
        private Long sellerId;
        @OneToOne
        private Payment payment;
        private String description;
        private LocalDateTime createdAt;
}
