package si.feri.parkingapp.cars;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import si.feri.parkingapp.cars.entity.CarEntity;

@Repository
public interface CarRepository extends CrudRepository<CarEntity, String> {
}
