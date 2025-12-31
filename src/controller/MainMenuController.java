package controller;

import javafx.fxml.FXML;
import util.SceneManager;
import util.MusicPlayer;
import util.SaveManager;

public class MainMenuController {

    @FXML
    public void initialize() {
        // Pastikan MusicPlayer tidak error, jika error layar bisa putih
        try {
            MusicPlayer.playMenuMusic();
        } catch (Exception e) {
            System.err.println("Gagal memutar musik: " + e.getMessage());
        }
    }

    @FXML
    public void newGame() {
        MusicPlayer.playClickSound();
        SaveManager.deleteSave(); // Hapus file save lama agar GameController membuat PuzzleBoard() baru
        SceneManager.switchScene("/view/mode_select.fxml");
    }

    @FXML
    public void continueGame() {
        MusicPlayer.playClickSound();
        if (SaveManager.hasSave()) {
            // Langsung lompat ke game, GameController akan otomatis load karena save.dat ada
            SceneManager.switchScene("/view/game.fxml");
        }
    }

    @FXML
    public void openSettings() {
        MusicPlayer.playClickSound(); // SFX
        SceneManager.switchScene("/view/settings.fxml");
    }

    @FXML
    public void exit() {
        MusicPlayer.playClickSound();
        System.exit(0);
    }
}