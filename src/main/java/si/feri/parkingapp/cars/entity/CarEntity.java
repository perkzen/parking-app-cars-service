package si.feri.parkingapp.cars.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import si.feri.parkingapp.cars.dto.CarDto;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "cars")
public class CarEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String owner;

    private String registrationPlate;

    private LocalDateTime createdAt = LocalDateTime.now();

    public CarDto toDto() {
        return CarDto
                .builder()
                .id(id)
                .owner(owner)
                .registrationPlate(registrationPlate)
                .build();
    }
}
