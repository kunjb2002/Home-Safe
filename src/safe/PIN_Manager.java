import java.io.*;
import java.util.*;

public class PIN_Manager {
    int stored_pin;
    public PIN_Manager(File storage) throws Exception {
        Scanner sc = new Scanner(storage);
        while (sc.hasNext()) {
            this.stored_pin = Integer.parseInt(sc.nextLine());
        }
    }

    public boolean comparePIN(int PIN_entered){
        return false;//Placeholder until code is written
    }
}
