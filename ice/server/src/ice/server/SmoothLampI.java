package ice.server;

import Home.Lighting.NotValidValueOfPercentage;
import Home.Lighting.SmoothLamp;
import com.zeroc.Ice.Current;

public class SmoothLampI extends LampI implements SmoothLamp {

    @Override
    public void turnOnPercentage(short num, Current current) throws NotValidValueOfPercentage {
        System.out.println("Try set new brightness " + num);
        if (num < 0 || num > 100)
            throw new NotValidValueOfPercentage();
        percentage = num;
        System.out.println("New brightness is " + percentage);
    }

    public String getState(Current current) {
        System.out.println("GET STATE");
        if (percentage == 0)
            return "Lamp is turned off.";
        return "Lamp is turned on in " + percentage + " percent.";
    }
}
