package ma.ac.usmba.challenge02.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.print.MultiDoc;
import java.util.Date;

@Data
public class CarDto {
    private long id;
    private String name;
    private String brand;
    private String type;
    private String transmission;
    private String color;
    private Date year;
    private boolean sold;
    private String description;
    private MultipartFile image= null;
    private long userId;
    private byte[] returnImage;
    private long price;
}
