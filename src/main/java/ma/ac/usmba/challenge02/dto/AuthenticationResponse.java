package ma.ac.usmba.challenge02.dto;

import lombok.Data;
import ma.ac.usmba.challenge02.enums.UserRole;
@Data
public class AuthenticationResponse {

    private String jwt;
    private Long userId;
    private UserRole userRole;
}
