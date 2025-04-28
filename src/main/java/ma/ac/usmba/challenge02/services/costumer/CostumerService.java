package ma.ac.usmba.challenge02.services.costumer;

import ma.ac.usmba.challenge02.dto.CarDto;

import java.io.IOException;
import java.util.List;

public interface CostumerService {
    boolean createCar(CarDto carDto) throws IOException;
    List<CarDto> getAllCars();
}
