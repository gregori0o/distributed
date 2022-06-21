package ice.server;

import Home.Lighting.SimpleLamp;
import com.zeroc.Ice.Current;

public class SimpleLampI extends LampI implements SimpleLamp {
    @Override
    public String getState(Current current) {
        System.out.println("GET STATE");
        if (turningOnBulbs == 0)
            return "Lamp is turned off.";
        return "Lamp is turned on.";
    }
}
