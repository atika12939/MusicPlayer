import java.io.*;
import java.util.*;

/**
 * Created by taka on 2016/01/13.
 */
public class Players {
    Map<String, Player> players;
    List<String> musicNames;
    List<String> nowPlayings;
    int index;
    private final String prefix = "musics\\";

    public Players() {
        players = new HashMap<>();
        nowPlayings = new ArrayList<>();
        musicNames = new ArrayList<>();
        index = 0;
    }

    public void loadPlayList(File playlistFile) {
        for (String music : nowPlayings) {
            players.get(music).stopMusic();
        }
        nowPlayings.clear();
        musicNames.clear();
        index = 0;
        if (playlistFile == null)
            return;
        int point = playlistFile.getName().lastIndexOf(".");
        if (!playlistFile.getName().substring(point + 1).equals("pll")) {
            return;
        }
        FileReader fileReader;
        BufferedReader bufferedReader;
        try {
            fileReader = new FileReader(playlistFile);
            bufferedReader = new BufferedReader(fileReader);
            String musicName = bufferedReader.readLine();
            while (musicName != null) {
                musicNames.add(musicName);
                musicName = bufferedReader.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String[] getPlayList() {
        String[] playlist;
        if (musicNames.isEmpty()) {
            playlist = new String[] {};
            return playlist;
        }
        int count = 0;
        if (musicNames.get(0).equals("s")) {
            playlist = new String[musicNames.size() - 1];
        } else {
            playlist = new String[musicNames.size()];
        }
        for (String music : musicNames) {
            if (!music.startsWith("s")) {
                playlist[count++] = music;
            } else {
                playlist[count++] = music.substring(2, music.length());
            }
        }
        return playlist;
    }

    public void makeMusicList() {
        for (String music : nowPlayings) {
            players.get(music).stopMusic();
        }
        index = 0;
        nowPlayings.clear();
        musicNames.clear();
        File dir = new File(prefix);
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.getName().endsWith(".mp3")) {
                musicNames.add(file.getName());
            }
        }
    }

    public void loadMusics() {
        if (musicNames.isEmpty()) {
            return;
        }
        String music = musicNames.get(index);
        if (music.contains(",")) {
            String[] musics = music.split(",");
            if (musics[0].equals("s")) {
                int rand = new Random().nextInt(musics.length - 1);
                music = musics[rand + 1];
                Player player = new Player();
                player.loadMusic(prefix + music);
                players.put(music, player);
                nowPlayings.add(music);

            } else {
                for (String target : musics) {
                    Player player = new Player();
                    player.loadMusic(prefix + target);
                    players.put(target, player);
                    nowPlayings.add(target);
                }
            }
        } else {
            Player player = new Player();
            player.loadMusic(prefix + music);
            players.put(music, player);
            nowPlayings.add(music);
        }
    }

    public void playMusics() {
        if (players.isEmpty()) {
            return;
        }
        for (String music : nowPlayings) {
            players.get(music).playMusic();
        }
    }

    public void pauseMusics() {
        for (String music : nowPlayings) {
            players.get(music).pauseMusic();
        }
    }

    public void moveForward() {
        for (String music : nowPlayings) {
            players.get(music).stopMusic();
        }
        index = index + 1 < musicNames.size() ? index + 1 : 0;
        players.clear();
        nowPlayings.clear();
        loadMusics();
        playMusics();
    }

    public void moveBackward() {
        for (String music : nowPlayings) {
            players.get(music).stopMusic();
        }
        players.clear();
        nowPlayings.clear();
        index = index - 1 < 0 ? musicNames.size() - 1 : index - 1;
        loadMusics();
        playMusics();
    }

    public boolean isPlaying() {
        if (players.isEmpty())
            return false;
        boolean isPlaying = false;
        for (String music : nowPlayings) {
            isPlaying = isPlaying || (players.get(music).getPlayerStatus() == 0);
        }
        return isPlaying;
    }

    public String getMusicNames() {
        String names = "";
        int i = 0;
        int last = nowPlayings.size();
        for (String music : nowPlayings) {
            int point = music.lastIndexOf(".");
            names += music.substring(0, point);
            if (++i != last) {
                names += " & ";
            }
        }
        return names;
    }
}
