package ice.server;

import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Util;
import com.zeroc.Ice.ObjectAdapter;
import com.zeroc.Ice.Identity;

public class DevicesServer {
    public void configure(String[] args) {
        int status = 0;
        Communicator communicator = null;

        try	{
            // 1. Inicjalizacja ICE - utworzenie communicatora
            communicator = Util.initialize(args);

            // 2. Konfiguracja adaptera
            ObjectAdapter adapter = communicator.createObjectAdapter("DevicesAdapter");

            // 3. Stworzenie serwantów
            MusicI musicServant1 = new MusicI();
            MusicI musicServant2 = new MusicI();
            FurnaceI furnaceServant = new FurnaceI();

            // 4. Dodanie wpisów do tablicy ASM, skojarzenie nazwy obiektu (Identity) z serwantem
            Identity[] identities = new Identity[3];
            identities[0] = new Identity("ground", "audio");
            identities[1] = new Identity("floor", "audio");
            identities[2] = new Identity("furnace", "heating");
            adapter.add(musicServant1, identities[0]);
            adapter.add(musicServant2, identities[1]);
            adapter.add(furnaceServant, identities[2]);

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
        DevicesServer app = new DevicesServer();
        app.configure(args);
    }
}
