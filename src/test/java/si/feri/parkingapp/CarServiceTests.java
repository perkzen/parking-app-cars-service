package si.feri.parkingapp;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import si.feri.parkingapp.cars.CarRepository;
import si.feri.parkingapp.cars.CarService;
import si.feri.parkingapp.cars.dto.CarDto;
import si.feri.parkingapp.cars.dto.CreateCarDto;

import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class CarServiceTests {

    @Autowired
    private CarService carService;

    @Autowired
    private CarRepository carRepository;


    CreateCarDto createCarDto = CreateCarDto.builder().owner("TestOwner").registrationPlate("TestLicensePlate").build();


    @AfterEach
    void tearDown() {
        carRepository.deleteAll();
    }

    @Test
    void testSaveCar() {
        var newCar = carService.save(createCarDto.toEntity());

        assertNotNull(newCar);
        assertEquals(newCar.getOwner(), createCarDto.getOwner());
        assertEquals(newCar.getRegistrationPlate(), createCarDto.getRegistrationPlate());
    }

    @Test
    void testGetCar() {
        var newCar = carService.save(createCarDto.toEntity());
        var car = carService.findById(newCar.getId()).isPresent() ? carService.findById(newCar.getId()).get() : null;

        assertNotNull(car);
        assertEquals(car.getOwner(), createCarDto.getOwner());
        assertEquals(car.getRegistrationPlate(), createCarDto.getRegistrationPlate());
    }

    @Test
    void testDeleteCar() {
        var newCar = carService.save(createCarDto.toEntity());
        carService.deleteById(newCar.getId());

        var car = carService.findById(newCar.getId()).isPresent() ? carService.findById(newCar.getId()).get() : null;

        assertNull(car);
    }

    @Test
    void testUpdateCar() {
        var car = carService.save(createCarDto.toEntity());
        car.setOwner("UpdatedOwner");


        var updatedCar = carService.save(CarDto.toEntity(car));

        assertNotNull(updatedCar);
        assertEquals(updatedCar.getOwner(), car.getOwner());
        assertEquals(updatedCar.getRegistrationPlate(), car.getRegistrationPlate());
    }

    @Test
    void testGetAllCars() {
        carService.save(createCarDto.toEntity());
        carService.save(createCarDto.toEntity());

        var cars = carService.findAll();
        var count = StreamSupport.stream(cars.spliterator(), false).count();

        assertNotNull(cars);
        assertEquals(count, 2);
    }
}
