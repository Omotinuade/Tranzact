package africa.semicolon.demo.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Transaction {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;
        private Long buyerId;
        private Long sellerId;
        @OneToOne(cascade = CascadeType.ALL)
        private Payment payment;
        private String description;
        @JsonSerialize(using = LocalDateTimeSerializer.class)
        @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        private LocalDateTime timeCreatedAt;
        @JsonSerialize(using = LocalDateTimeSerializer.class)
        @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        private LocalDateTime lastModifiedAt;

        @PrePersist
        public void setTimeCreatedAt(){
                this.timeCreatedAt = LocalDateTime.now();
        }
}
