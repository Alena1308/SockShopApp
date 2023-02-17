package sky.course3.sockshopapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SocksTransaction {
    private OperationType operationType;
    private Date date = new Date();
    private Socks sock;
}
