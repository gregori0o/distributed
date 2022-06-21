package ice.server;

import Home.Lighting.Lamp;
import com.zeroc.Ice.Current;

public class LampI extends DeviceI implements Lamp {
    protected int maxBulbs = 1;
    protected int turningOnBulbs = 0;
    protected int percentage = 0;
    @Override
    public void turnOn(Current current) {
        System.out.println("Turning on lamp");
        turningOnBulbs = maxBulbs;
        percentage = 100;
        System.out.println("Done");
    }

    @Override
    public void turnOff(Current current) {
        System.out.println("Turning off lamp");
        turningOnBulbs = 0;
        percentage = 0;
        System.out.println("Done");
    }
}
