package sky.course3.sockshopapp.model;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Socks {
    private Color color;
    private Size size;
    @Min(value = 0, message = "should be >0")
    @Max(value = 100, message = "should be <100")
    private long cottonPart;
    @Positive(message = "should be positive")
    private long quantity;

}
