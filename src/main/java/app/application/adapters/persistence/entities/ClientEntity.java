package app.application.adapters.persistence.entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "clients")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class ClientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clientId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    private String fullName;
    private String documentNumber;
    private String email;
    private String phone;
    private String address;
}