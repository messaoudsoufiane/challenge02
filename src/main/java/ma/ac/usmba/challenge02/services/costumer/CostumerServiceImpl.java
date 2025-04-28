package ma.ac.usmba.challenge02.services.costumer;

import lombok.RequiredArgsConstructor;
import ma.ac.usmba.challenge02.dto.CarDto;
import ma.ac.usmba.challenge02.entities.Car;
import ma.ac.usmba.challenge02.entities.User;
import ma.ac.usmba.challenge02.repositories.CarRepository;
import ma.ac.usmba.challenge02.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CostumerServiceImpl implements CostumerService{

    private final UserRepository userRepository;
    private final CarRepository carRepository;
    @Override
    public boolean createCar(CarDto carDto) throws IOException {
        System.out.println("appel de methode"+carDto.getUserId()+" "+carDto.getPrice()+" "+carDto.getYear()+" ");
        Optional<User> optionalUser=userRepository.findById(carDto.getUserId());
        System.out.println("Find User By Id");


        if(optionalUser.isPresent()){
            Car car=new Car();
            System.out.println("Create Car");
            car.setName(carDto.getName());
            car.setPrice(carDto.getPrice());
            car.setBrand(carDto.getBrand());
            car.setDescription(carDto.getDescription());
            car.setSold(false);
            car.setTransmission(carDto.getTransmission());
            car.setColor(carDto.getColor());
            car.setYear(carDto.getYear());
            car.setUser(optionalUser.get());
            System.out.println("saveed car");
           // car.setImage(carDto.getImage().getBytes());
            carRepository.save(car);
            return true;


        }
        return false;
    }

    @Override
    public List<CarDto> getAllCars() {
        return carRepository.findAll().stream().map(Car::getCarDto).collect(Collectors.toList());
    }
}
