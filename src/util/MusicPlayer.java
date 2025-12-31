package util;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MusicPlayer {
    private static MediaPlayer musicPlayer;
    private static double musicVolume = 0.5;
    private static double sfxVolume = 0.8;
    private static String currentPlaying = "";

    // Tambahkan variabel untuk memuat file suara sekali saja (hemat memori)
    private static AudioClip hoverSound;
    private static AudioClip clickSound;

    public static void playMusic(String fileName) {
        // Jika lagu yang diminta sama dengan yang sedang diputar, jangan diapa-apakan
        if (fileName.equals(currentPlaying) && musicPlayer != null &&
                musicPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            return;
        }

        try {
            if (musicPlayer != null) {
                musicPlayer.stop();
                musicPlayer.dispose();
            }

            var res = MusicPlayer.class.getResource("/music/" + fileName);
            if (res == null) return;

            currentPlaying = fileName; // Simpan nama lagu saat ini
            Media media = new Media(res.toString());
            musicPlayer = new MediaPlayer(media);
            musicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            musicPlayer.setVolume(musicVolume);

            // Gunakan setOnReady untuk memastikan lagu diputar saat sudah siap
            musicPlayer.setOnReady(() -> musicPlayer.play());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void playHoverSound() {
        try {
            if (hoverSound == null) {
                var res = MusicPlayer.class.getResource("/music/hover.mp3");
                if (res != null) hoverSound = new AudioClip(res.toString());
            }
            if (hoverSound != null) {
                hoverSound.setVolume(sfxVolume * 0.5); // Lebih pelan dari suara klik
                hoverSound.play();
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    public static void playClickSound() {
        try {
            if (clickSound == null) {
                var res = MusicPlayer.class.getResource("/music/click.mp3");
                if (res != null) clickSound = new AudioClip(res.toString());
            }
            if (clickSound != null) {
                clickSound.setVolume(sfxVolume);
                clickSound.play();
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