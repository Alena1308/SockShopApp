package sky.course3.sockshopapp.model;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Socks {
    private Color color;
    private Size size;
    @Min(value = 0, message = "should be >0")
    @Max(value = 100, message = "should be <100")
    private int cottonPart;
    @Positive(message = "should be positive")
    @Setter
    private long quantity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Socks socks = (Socks) o;
        return cottonPart == socks.cottonPart && color == socks.color && size == socks.size;
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, size, cottonPart);
    }
}
