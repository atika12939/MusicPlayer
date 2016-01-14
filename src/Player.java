import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerException;

import java.io.File;

/**
 * Created by taka on 2016/01/13.
 */
public class Player {
    private BasicPlayer player;

    public Player() {
        player = new BasicPlayer();
    }

    public void loadMusic(String musicFilePath) {
        File musicFile = new File(musicFilePath);
        try {
            this.player.open(musicFile);
        } catch (BasicPlayerException e) {
            e.printStackTrace();
        }
    }

    public void playMusic() {
        try {
            if (player.getStatus() == 1) {
                player.resume();
            } else {
                player.play();
            }
        } catch (BasicPlayerException e) {
            e.printStackTrace();
        }
    }

    public void pauseMusic() {
        try {
            if (player.getStatus() == 0) {
                player.pause();
            }
        } catch (BasicPlayerException e) {
            e.printStackTrace();
        }
    }

    public void stopMusic() {
        try {
            player.stop();
        } catch (BasicPlayerException e) {
            e.printStackTrace();
        }
    }

    public int getPlayerStatus() {
        return player.getStatus();
    }
}
