package si.feri.parkingapp.cars.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import si.feri.parkingapp.cars.entity.CarEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateCarDto {
    private String owner;
    private String registrationPlate;

    public CarEntity toEntity() {
        return CarEntity
                .builder()
                .owner(owner)
                .registrationPlate(registrationPlate)
                .build();
    }
}
