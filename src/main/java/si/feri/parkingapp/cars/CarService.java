package si.feri.parkingapp.cars;

import org.springframework.stereotype.Service;
import si.feri.parkingapp.cars.dto.CarDto;
import si.feri.parkingapp.cars.entity.CarEntity;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CarService {
    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }


    public Optional<CarDto> findById(String id) {
        return carRepository.findById(id)
                .map(CarEntity::toDto);
    }

    public CarDto save(CarEntity car) {
        return carRepository.save(car).toDto();
    }

    public void deleteById(String id) {
        carRepository.deleteById(id);
    }

    public boolean existsById(String id) {
        return carRepository.existsById(id);
    }

    public Iterable<CarDto> findAll() {
        return StreamSupport
                .stream(carRepository.findAll().spliterator(), false)
                .map(CarEntity::toDto)
                .collect(Collectors.toList());
    }

}
