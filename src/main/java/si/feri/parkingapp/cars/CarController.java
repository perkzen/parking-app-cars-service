package si.feri.parkingapp.cars;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import si.feri.parkingapp.cars.dto.CarDto;
import si.feri.parkingapp.cars.dto.CreateCarDto;
import si.feri.parkingapp.cars.entity.CarEntity;

import java.util.logging.Logger;

@RestController
@CrossOrigin
@RequestMapping("/api/cars")
@Tag(name = "Cars")
public class CarController {

    private static final Logger log = Logger.getLogger(CarController.class.getName());
    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/")
    public Iterable<CarDto> findAll() {
        return this.carService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarDto> findOne(@PathVariable String id) {
        var car = this.carService.findById(id);

        log.info(STR."Car with id \{id} was requested");

        return car.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    public ResponseEntity<CarDto> newCar(@RequestBody CreateCarDto carDto) {
        var car = this.carService.save(carDto.toEntity());

        log.info(STR."New car was created with id \{car.getId()}");

        return ResponseEntity.status(HttpStatus.CREATED).body(car);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarDto> updateCar(@RequestBody CreateCarDto dto, @PathVariable String id) {
        CarEntity carEntity = dto.toEntity();

        if (!this.carService.existsById(id)) {
            log.info(STR."Car with id \{id} was not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        carEntity.setId(id);

        log.info(STR."Car with id \{id} was updated");

        return ResponseEntity.ok(this.carService.save(carEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCar(@PathVariable String id) {
        this.carService.deleteById(id);

        log.info(STR."Car with id \{id} was deleted");

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
