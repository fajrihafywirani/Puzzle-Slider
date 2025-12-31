package util;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MusicPlayer {
    private static MediaPlayer musicPlayer;
    private static double musicVolume = 0.5;
    private static double sfxVolume = 0.8;

    public static void playMenuMusic() {
        try {
            if (musicPlayer == null) {
                var res = MusicPlayer.class.getResource("/music/menu.mp3");
                if (res == null) return;

                Media media = new Media(res.toString());
                musicPlayer = new MediaPlayer(media);
                musicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
                musicPlayer.setVolume(musicVolume);
            }
            musicPlayer.play();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public static void playClickSound() {
        try {
            var res = MusicPlayer.class.getResource("/music/click.mp3");
            if (res != null) {
                MediaPlayer sfxPlayer = new MediaPlayer(new Media(res.toString()));
                sfxPlayer.setVolume(sfxVolume);
                sfxPlayer.play();
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    public static void setMusicVolume(double volume) {
        musicVolume = volume;
        if (musicPlayer != null) musicPlayer.setVolume(volume);
    }

    public static void setSfxVolume(double volume) {
        sfxVolume = volume;
    }

    public static double getMusicVolume() { return musicVolume; }
    public static double getSfxVolume() { return sfxVolume; }
}