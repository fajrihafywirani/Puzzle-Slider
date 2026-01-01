package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import util.MusicPlayer;
import util.SaveManager;
import util.SceneManager;
import model.GameState;


public class WinController {

    @FXML private Label timeLabel;

    @FXML
    public void initialize() {
        try {
            MusicPlayer.playMusic("win.mpeg");
        } catch (Exception e) {
            System.err.println("Gagal memutar musik: " + e.getMessage());
        }
        int lastTime = GameState.getInstance().getSecondsElapsed(); // Pastikan tidak di-reset terlalu cepat
        int mins = lastTime / 60;
        int secs = lastTime % 60;
        timeLabel.setText(String.format("Waktu Anda: %02d:%02d", mins, secs));
    }

    @FXML
    public void restartGame() {
        // Hapus save lama agar generate puzzle baru yang teracak
        SaveManager.deleteSave();
        MusicPlayer.playClickSound();
        GameState.getInstance().setBoard(null);
        GameState.getInstance().setSecondsElapsed(0); // Reset waktu
        SceneManager.switchScene("/view/game.fxml");
    }

    @FXML
    public void goToMenu() {
        SaveManager.deleteSave();
        MusicPlayer.playClickSound();
        GameState.getInstance().setBoard(null);
        SceneManager.switchScene("/view/main_menu.fxml");
    }
}