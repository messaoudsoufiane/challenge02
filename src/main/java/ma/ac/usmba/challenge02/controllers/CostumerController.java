package ma.ac.usmba.challenge02.controllers;

import lombok.RequiredArgsConstructor;
import ma.ac.usmba.challenge02.dto.CarDto;
import ma.ac.usmba.challenge02.services.costumer.CostumerService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/costumer")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CostumerController {
    private final CostumerService costumerService;
    @PostMapping("/car")
    public ResponseEntity<?> addCar(@ModelAttribute CarDto carDto, HttpEntity<Object> httpEntity) throws IOException {
       boolean success =costumerService.createCar(carDto);
       if(success) {
           return ResponseEntity.status(HttpStatus.CREATED).build();
       }
       return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    }
    @GetMapping("/cars")
    public ResponseEntity<List<CarDto>> getAllCar() {
        return ResponseEntity.ok(costumerService.getAllCars());

    }
}
