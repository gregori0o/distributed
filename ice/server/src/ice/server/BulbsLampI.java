package ice.server;

import Home.Lighting.BulbsLamp;
import Home.Lighting.NotValidNumberOfBulbs;
import com.zeroc.Ice.Current;

public class BulbsLampI extends LampI implements BulbsLamp {
    public BulbsLampI(int size) {
        maxBulbs = size;
    }

    public BulbsLampI() {
        maxBulbs = 5;
    }

    public String getState(Current current) {
        System.out.println("GET STATE");
        if (turningOnBulbs == 0)
            return "Lamp is turned off.";
        return "" + turningOnBulbs + " bulbs is turned on.";
    }

    @Override
    public void turnOnFew(short num, Current current) throws NotValidNumberOfBulbs {
        System.out.println("Try turning on " + num + " bulbs");
        if (num < 0 || num > maxBulbs)
            throw new NotValidNumberOfBulbs();
        turningOnBulbs = num;
        System.out.println("Turned on " + turningOnBulbs + " bulbs");
    }
}
