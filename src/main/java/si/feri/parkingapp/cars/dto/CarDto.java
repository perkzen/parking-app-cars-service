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
public class CarDto {
    private String id;
    private String owner;
    private String registrationPlate;

    public static CarEntity toEntity(CarDto carDto) {
        return CarEntity
                .builder()
                .id(carDto.getId())
                .owner(carDto.getOwner())
                .registrationPlate(carDto.getRegistrationPlate())
                .build();
    }
}
