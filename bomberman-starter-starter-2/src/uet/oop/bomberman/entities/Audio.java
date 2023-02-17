package uet.oop.bomberman.entities;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Lưu trữ và xử lý âm thanh.
 */
public class Audio {

    private long startTime = 0;
    public List<MediaPlayer> list = new ArrayList<>();

    public enum audio {
        backgroundMusic(0),
        bomExplode(1),
        bomSet(2),
        bobDropped(3),
        buttonClick(4),
        gameOver(5),
        gameStart(6),
        playerDead(7),
        powerUp(8),
        ;

        public final int value;
        audio(int value) {
            this.value = value;
        }
    }

    private String path1 = "bomberman-starter-starter-2/res/Audio/BG_MUSIC.wav";
    private String path2 = "bomberman-starter-starter-2/res/Audio/BOM_EXPLODE.wav";
    private String path3 = "bomberman-starter-starter-2/res/Audio/BOM_SET.wav";
    private String path4 = "bomberman-starter-starter-2/res/Audio/bombDropped.wav";
    private String path5 = "bomberman-starter-starter-2/res/Audio/BUTTON_CLICK.wav";
    private String path6 = "bomberman-starter-starter-2/res/Audio/GAME_OVER.wav";
    private String path7 = "bomberman-starter-starter-2/res/Audio/GAME_START.wav";
    private String path8 = "bomberman-starter-starter-2/res/Audio/PLAYER_DEAD.wav";
    private String path9 = "bomberman-starter-starter-2/res/Audio/POWER_UP.wav";

    public Audio() {
        loadAudio(path1);
        loadAudio(path2);
        loadAudio(path3);
        loadAudio(path4);
        loadAudio(path5);
        loadAudio(path6);
        loadAudio(path7);
        loadAudio(path8);
        loadAudio(path9);
    }
    public void loadAudio(String path) {
        File file = new File(path);
        Media media = new Media(file.toURI().toString());
        list.add(new MediaPlayer(media));
    }

    public void playAudioFull(int i) {
        list.get(i).setCycleCount(MediaPlayer.INDEFINITE);
        list.get(i).play();
    }

    public void playAudio(int i) {
        list.get(i).play();
    }

    public void audioStopTime(int i, long time) {
        list.get(i).play();
        if (startTime == 0) {
            startTime = System.currentTimeMillis();
        }
        long endTime = System.currentTimeMillis();

        if (endTime - startTime >= time) {
            list.get(i).stop();
          //  startTime = endTime;
        }
    }
    public void autoPlay(int i, int time) {
        list.get(i).play();
        if (time > 0) {
            time--;
        } else {
            list.get(i).stop();
        }
    }

    public void stopAudio(int i) {
        list.get(i).stop();
    }
}
