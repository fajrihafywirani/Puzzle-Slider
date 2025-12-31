package util;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MusicPlayer {

    private static MediaPlayer player;

    public static void playMenuMusic() {
        try {
            if (player == null) {
                var resource = MusicPlayer.class.getResource("/music/menu.mp3");
                if (resource == null) {
                    System.err.println("File musik tidak ditemukan di path: /music/menu.mp3");
                    return;
                }
                Media media = new Media(resource.toString());
                player = new MediaPlayer(media);
                player.setCycleCount(MediaPlayer.INDEFINITE);
            }
            player.play();
        } catch (Exception e) {
            System.err.println("Error memutar musik: " + e.getMessage());
        }
    }
}
