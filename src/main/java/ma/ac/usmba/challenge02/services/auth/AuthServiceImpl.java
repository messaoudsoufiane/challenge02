package ma.ac.usmba.challenge02.services.auth;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import ma.ac.usmba.challenge02.dto.SignupRequest;
import ma.ac.usmba.challenge02.dto.UserDto;
import ma.ac.usmba.challenge02.entities.User;
import ma.ac.usmba.challenge02.enums.UserRole;
import ma.ac.usmba.challenge02.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    @PostConstruct
    public void createAdminAccount() {
       Optional<User> optionalAdmin = userRepository.findByUserRole(UserRole.ADMIN);
       if(optionalAdmin.isEmpty()) {
           User admin = new User();
           admin.setName("admin");
           admin.setEmail("admin@admin.com");
           admin.setUserRole(UserRole.ADMIN);
           admin.setPassword(new BCryptPasswordEncoder().encode("admin"));
           userRepository.save(admin);
           System.out.println("Admin created successfully");
       }else{
           System.out.println("Admin already exists");
       }

    }

    @Override
    public boolean hasUserWithEmail(String email) {
        return userRepository.findFirstByEmail(email).isPresent();
    }

    @Override
    public UserDto signup(SignupRequest signupRequest) {
        User user = new User();
        user.setEmail(signupRequest.getEmail());
        user.setName(signupRequest.getName());
        user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
        user.setUserRole(UserRole.USER);
        return userRepository.save(user).getUserDto();
    }
}
