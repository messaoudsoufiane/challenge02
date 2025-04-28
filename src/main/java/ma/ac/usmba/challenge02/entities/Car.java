package ma.ac.usmba.challenge02.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import ma.ac.usmba.challenge02.dto.CarDto;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;

@Entity
@Data
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String brand;
    private String type;
    private String transmission;
    private String color;
    private Date year;
    private boolean sold;
    @Lob
    private String description;
    @Lob
    @Column(columnDefinition = "longblob")
    private byte[] image;
    private long price;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "user_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;

    public CarDto getCarDto() {
        CarDto carDto = new CarDto();
        carDto.setId(id);
        carDto.setName(name);
        carDto.setBrand(brand);
        carDto.setType(type);
        carDto.setTransmission(transmission);
        carDto.setColor(color);
        carDto.setYear(year);
        carDto.setSold(sold);
        carDto.setDescription(description);
        carDto.setReturnImage(image);
        carDto.setPrice(price);
        return carDto;

    }

}
