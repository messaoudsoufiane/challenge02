package ma.ac.usmba.challenge02.repositories;

import ma.ac.usmba.challenge02.entities.User;
import ma.ac.usmba.challenge02.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findFirstByEmail(String email);

    Optional<User >findByUserRole(UserRole userRole);
}
