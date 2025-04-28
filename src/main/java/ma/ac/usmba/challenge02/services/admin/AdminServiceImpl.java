package ma.ac.usmba.challenge02.services.admin;

import lombok.RequiredArgsConstructor;
import ma.ac.usmba.challenge02.dto.CarDto;
import ma.ac.usmba.challenge02.entities.Car;
import ma.ac.usmba.challenge02.repositories.CarRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService{

    private final CarRepository carRepository;

    @Override
    public List<CarDto> getAllCars() {
        return carRepository.findAll().stream().map(Car::getCarDto).collect(Collectors.toList());
    }
}
