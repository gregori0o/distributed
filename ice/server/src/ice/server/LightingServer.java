package ice.server;

import com.zeroc.Ice.*;

import java.lang.Exception;
import java.util.Arrays;

public class LightingServer {
    public void configure(String[] args) {
        int status = 0;
        Communicator communicator = null;

        try	{
            // 1. Inicjalizacja ICE - utworzenie communicatora
            communicator = Util.initialize(args);

            // 2. Konfiguracja adaptera
            ObjectAdapter adapter = communicator.createObjectAdapter("LightAdapter");

            // 3. Stworzenie serwantów
            SimpleLampI simpleLampServant1 = new SimpleLampI();
            SimpleLampI simpleLampServant2 = new SimpleLampI();
            SimpleLampI simpleLampServant3 = new SimpleLampI();
            BulbsLampI bulbsLampServant1 = new BulbsLampI(10);
            BulbsLampI bulbsLampServant2 = new BulbsLampI(4);
            SmoothLampI smoothLampServant1 = new SmoothLampI();

            // 4. Dodanie wpisów do tablicy ASM, skojarzenie nazwy obiektu (Identity) z serwantem
            Identity[] identities = new Identity[6];
            identities[0] = new Identity("kitchen", "simple");
            identities[1] = new Identity("hall", "simple");
            identities[2] = new Identity("living_room", "bulbs");
            identities[3] = new Identity("sitting_room", "smooth");
            identities[4] = new Identity("room", "bulbs");
            identities[5] = new Identity("attic", "simple");

            adapter.add(simpleLampServant1, identities[0]);
            adapter.add(simpleLampServant2, identities[1]);
            adapter.add(bulbsLampServant1, identities[2]);
            adapter.add(smoothLampServant1, identities[3]);
            adapter.add(bulbsLampServant2, identities[4]);
            adapter.add(simpleLampServant3, identities[5]);

            // Zapisz informacje
            ServerInformationI serverInformationServant = new ServerInformationI(identities);
            adapter.add(serverInformationServant, new Identity("identities", "server"));

            // 5. Aktywacja adaptera i wejście w pętlę przetwarzania żądań
            adapter.activate();

            System.out.println("Entering event processing loop...");

            communicator.waitForShutdown();

        }
        catch (Exception e) {
            System.err.println(e);
            status = 1;
        }
        if (communicator != null) {
            try {
                communicator.destroy();
            }
            catch (Exception e) {
                System.err.println(e);
                status = 1;
            }
        }
        System.exit(status);
    }

    public static void main(String[] args) {
        LightingServer app = new LightingServer();
        app.configure(args);
    }
}
