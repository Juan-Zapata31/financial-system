package app.domain.models;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NaturalClient extends Client {
    private Long naturalClientId;
    private LocalDate birthDate;
}