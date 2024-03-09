package si.feri.parkingapp;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import si.feri.parkingapp.cars.CarController;
import si.feri.parkingapp.cars.CarRepository;
import si.feri.parkingapp.cars.CarService;
import si.feri.parkingapp.cars.dto.CarDto;
import si.feri.parkingapp.cars.dto.CreateCarDto;
import si.feri.parkingapp.cars.entity.CarEntity;

import java.util.ArrayList;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
public class CarControllerTests {

    @Autowired
    private CarController carController;

    @MockBean
    private CarService carService;

    @MockBean
    private CarRepository carRepository;

    @AfterEach
    void tearDown() {
        carRepository.deleteAll();
    }

    CreateCarDto createCarDto = CreateCarDto.builder().owner("TestOwner").registrationPlate("TestLicensePlate").build();
    CarDto carDto = CarDto.builder().id("test").owner("TestOwner").registrationPlate("TestLicensePlate").build();

    @Test
    void testSaveCar() {
        when(carService.save(any(CarEntity.class))).thenReturn(carDto);

        var newCar = carController.newCar(createCarDto);

        assertNotNull(newCar);
        assertEquals(Objects.requireNonNull(newCar.getBody()).getOwner(), createCarDto.getOwner());
        assertEquals(newCar.getBody().getRegistrationPlate(), createCarDto.getRegistrationPlate());
    }

    @Test
    void testUpdateCar() {
        when(carService.save(any(CarEntity.class))).thenReturn(carDto);
        when(carService.existsById("test")).thenReturn(true);

        var updatedCar = carController.updateCar(createCarDto, "test");

        assertNotNull(updatedCar);
        assertEquals(Objects.requireNonNull(updatedCar.getBody()).getOwner(), createCarDto.getOwner());
        assertEquals(updatedCar.getBody().getRegistrationPlate(), createCarDto.getRegistrationPlate());
    }

    @Test
    void testDeleteCar() {
        when(carService.existsById("test")).thenReturn(true);

        carController.deleteCar("test");

        var car = carController.findOne("test");

        assertEquals(404, car.getStatusCode().value());
    }

    @Test
    void testGetCar() {
        when(carService.findById("test")).thenReturn(java.util.Optional.of(carDto));

        var car = carController.findOne("test");

        assertNotNull(car);
        assertEquals(200, car.getStatusCode().value());
    }

    @Test
    void testGetAllCars() {
        var cars = new ArrayList<CarDto>();
        cars.add(carDto);
        cars.add(carDto);

        when(carService.findAll()).thenReturn(cars);


        var res = carController.findAll();

        assertNotNull(res);
        assertEquals(2, cars.spliterator().getExactSizeIfKnown());
    }

    @Test
    void testGetCarNotFound() {
        when(carService.save(any(CarEntity.class))).thenReturn(null);


        var car = carController.findOne("notfound");

        assertEquals(404, car.getStatusCode().value());
    }

    @Test
    void testUpdateCarNotFound() {
        when(carService.save(any(CarEntity.class))).thenReturn(carDto);
        when(carService.existsById("test")).thenReturn(false);

        var updatedCar = carController.updateCar(createCarDto, "test");

        assertEquals(404, updatedCar.getStatusCode().value());
    }


    @Test
    void testDeleteCarNotFound() {
        when(carService.existsById("test")).thenReturn(false);

        carController.deleteCar("notfound");

        var car = carController.findOne("notfound");

        assertEquals(404, car.getStatusCode().value());
    }

    @Test
    void testGetAllCarsEmpty() {
        var cars = carController.findAll();

        assertNotNull(cars);
        assertEquals(0, cars.spliterator().getExactSizeIfKnown());
    }

}
