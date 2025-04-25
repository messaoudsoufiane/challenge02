package ma.ac.usmba.challenge02.services.auth;

import ma.ac.usmba.challenge02.dto.SignupRequest;
import ma.ac.usmba.challenge02.dto.UserDto;

public interface AuthService {

    UserDto signup(SignupRequest signupRequest);
    boolean hasUserWithEmail(String email);

}
