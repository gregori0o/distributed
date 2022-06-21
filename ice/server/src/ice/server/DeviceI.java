package ice.server;

import Home.Device;
import com.zeroc.Ice.Current;

public class DeviceI implements Device {
    @Override
    public String getState(Current current) {
        System.out.println("GET STATE");
        return null;
    }
}
