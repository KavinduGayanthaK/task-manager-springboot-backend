package lk.ijse.taskmanager.util.idGenerator;


import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class IdGenerator {

    public  long generateTaskId() {
        long timestamp = System.currentTimeMillis(); // 13-digit
        int random = new Random().nextInt(999);      // 3-digit
        return Long.parseLong(timestamp + String.format("%03d", random));

    }
}
