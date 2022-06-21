package ice.server;

import Home.Service.ServerInformation;
import com.zeroc.Ice.Current;
import com.zeroc.Ice.Identity;

import java.util.Arrays;

public class ServerInformationI implements ServerInformation {
    private String[] allIdentities;

    public ServerInformationI (Identity[] allIdentities) {
        this.allIdentities = Arrays.stream(allIdentities).map(e -> e.name + " from category " + e.category).toArray(String[]::new);
    }
    @Override
    public String[] getAllIdentities(Current current) {
        System.out.println("GET IDENTITIES");
        return allIdentities;
    }
}
