package ma.ac.usmba.challenge02.dto;

import lombok.Data;
import ma.ac.usmba.challenge02.enums.UserRole;

@Data
public class UserDto {
    private long id ;
    private String name ;
    private String email ;
    private UserRole userRole ;
}
