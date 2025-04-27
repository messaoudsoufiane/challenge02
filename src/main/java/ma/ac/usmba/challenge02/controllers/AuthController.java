package ma.ac.usmba.challenge02.controllers;

import lombok.RequiredArgsConstructor;
import ma.ac.usmba.challenge02.dto.AuthenticationRequest;
import ma.ac.usmba.challenge02.dto.AuthenticationResponse;
import ma.ac.usmba.challenge02.dto.SignupRequest;
import ma.ac.usmba.challenge02.dto.UserDto;
import ma.ac.usmba.challenge02.entities.User;
import ma.ac.usmba.challenge02.repositories.UserRepository;
import ma.ac.usmba.challenge02.services.auth.AuthService;
import ma.ac.usmba.challenge02.services.jwt.UserService;
import ma.ac.usmba.challenge02.utils.JWTUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    private final JWTUtils jwtUtils;
    private final UserService userService;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody AuthenticationRequest authenticationRequest) {
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));

        }catch(BadCredentialsException e){
            throw new BadCredentialsException("Incorrect username or password");
        }
        final UserDetails userDetails = userService.userDetailsService().loadUserByUsername(authenticationRequest.getEmail());
        Optional<User> optionalUser= userRepository.findFirstByEmail(authenticationRequest.getEmail());
        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("User with email " + authenticationRequest.getEmail() + " not found");
        }
         final String jwt =jwtUtils.generateToken(userDetails);
        AuthenticationResponse response=new AuthenticationResponse();
        if(optionalUser.isPresent()){
            System.out.println("UserExist");
           response.setJwt(jwt);
            response.setUserRole(optionalUser.get().getUserRole());
            response.setUserId(optionalUser.get().getId());

              }
        return response;
    }

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
