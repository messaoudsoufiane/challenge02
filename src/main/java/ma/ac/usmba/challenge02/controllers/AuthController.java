package ma.ac.usmba.challenge02.controllers;

import lombok.RequiredArgsConstructor;
import ma.ac.usmba.challenge02.dto.SignupRequest;
import ma.ac.usmba.challenge02.dto.UserDto;
import ma.ac.usmba.challenge02.services.auth.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest signupRequest) {
        if(authService.hasUserWithEmail(signupRequest.getEmail()))
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Email already exists");
        UserDto userDto=authService.signup(signupRequest);
        if(userDto==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }else{
            return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
        }
    }

}
