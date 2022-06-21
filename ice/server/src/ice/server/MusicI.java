package ice.server;

import Home.Audio.Music;
import Home.Audio.InvalidPlaylistName;
import Home.Audio.InvalidSongName;
import com.zeroc.Ice.Current;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MusicI extends DeviceI implements Music {
    private final int maxVolume = 50;
    private int actualVolume = 20;
    private boolean isPlayed = false;
    private String actualPlaylist = "sanah";
    private int actualSong = 0;
    private HashMap<String, String[]> music;

    public MusicI() {
        music = new HashMap<>();
        String[] playlist1 = new String[] {
            "Czesława", "Szary świat", "Sen we śnie", "Tęsknię sobie", "ostatnia nadzieja", "Mamo tyś płakała", "Eldorado", "Baczyński", "audi", "oscar", "Święty Graal"
        };
        music.put("sanah", playlist1);
        String[] playlist2 = new String[] {
                "To już było", "Małgośka", "Łatwopalni", "Dziś Prawdziwych cyganów już nie ma", "Wszyscy chcą kochać", "Szparka sekretarka"
        };
        music.put("maryla", playlist2);
        String[] playlist3 = new String[] {
                "Zawsze", "2009", "Kosmiczne Energie", "Po prostu bądź", "Podobno", "Bal u Rafała"
        };
        music.put("ralph", playlist3);

    }

    @Override
    public void volumeUp(Current current) {
        System.out.println("Function volume up, old volume = " + actualVolume);
        actualVolume = Math.min(actualVolume + 1, maxVolume);
        System.out.println("Function volume up, new volume = " + actualVolume);
    }

    @Override
    public void volumeDown(Current current) {
        System.out.println("Function volume down, old volume = " + actualVolume);
        actualVolume = Math.max(actualVolume - 1, 0);
        System.out.println("Function volume down, new volume = " + actualVolume);
    }

    @Override
    public String[] getPlaylists(Current current) {
        System.out.println("Function get playlists names");
        return music.keySet().toArray(String[]::new);
    }

    @Override
    public Map<String, String[]> getMusic(Current current) {
        System.out.println("Function get all information about music");
        return music;
    }

    @Override
    public String[] getSongs(Optional<String> playlistName, Current current) throws InvalidPlaylistName {
        System.out.print("Function get songs ");
        if (! playlistName.isPresent()) {
            System.out.println("from actual playlist = " + actualPlaylist);
            return music.get(actualPlaylist);
        }
        if (! music.containsKey(playlistName.get()))
            throw new InvalidPlaylistName();
        System.out.println("from playlist = " + playlistName.get());
        return music.get(playlistName.get());
    }

    @Override
    public void setPlaylist(String playlistName, Current current) throws InvalidPlaylistName {
        System.out.println("Try new playlist = " + playlistName);
        if (! music.containsKey(playlistName))
            throw new InvalidPlaylistName();
        System.out.println("Setting...");
        actualPlaylist = playlistName;
        actualSong = 0;
    }

    @Override
    public void setSong(String songName, Current current) throws InvalidSongName {
        System.out.println("Try new song = " + songName);
        String[] playlist = music.get(actualPlaylist);
        for (int i = 0; i < playlist.length; i++)
            if (songName.equals(playlist[i])) {
                actualSong = i;
                System.out.println("Setting...");
                return;
            }
        throw new InvalidSongName();
    }

    @Override
    public void nextSong(Current current) {
        System.out.println("Change song to next, old = " + actualSong + ", " + music.get(actualPlaylist)[actualSong]);
        actualSong ++;
        if (actualSong >= music.get(actualPlaylist).length)
            actualSong = 0;
        System.out.println("Change song to next, new = " + actualSong + ", " + music.get(actualPlaylist)[actualSong]);
    }

    @Override
    public void prevSong(Current current) {
        System.out.println("Change song to prev, old = " + actualSong + ", " + music.get(actualPlaylist)[actualSong]);
        actualSong --;
        if (actualSong < 0)
            actualSong = music.get(actualPlaylist).length - 1;
        System.out.println("Change song to prev, new = " + actualSong + ", " + music.get(actualPlaylist)[actualSong]);
    }

    @Override
    public void startStop(Current current) {
        System.out.println("Start or stop playing music");
        isPlayed = ! isPlayed;
    }

    @Override
    public String getState(Current current) {
        System.out.println("GET STATE");
        if (isPlayed)
            return "Device is turned on and volume is " + actualVolume + " and " + music.get(actualPlaylist)[actualSong] + " is played.";
        return "Device is turned off";
    }
}
