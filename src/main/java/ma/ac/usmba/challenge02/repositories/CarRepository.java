package ma.ac.usmba.challenge02.repositories;

import ma.ac.usmba.challenge02.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

}
