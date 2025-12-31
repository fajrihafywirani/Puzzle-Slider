package controller;

import javafx.fxml.FXML; // Pastikan ini di-import
import model.GameState;
import util.MusicPlayer;
import util.SceneManager;

public class ModeSelectController {

    @FXML
    public void numberMode() {
        MusicPlayer.playClickSound();
        GameState.setImageMode(false);
        SceneManager.switchScene("/view/game.fxml"); // Tambahkan "/"
    }

    @FXML
    public void imageMode() {
        MusicPlayer.playClickSound();
        GameState.setImageMode(true);
        SceneManager.switchScene("/view/game.fxml"); // Tambahkan "/"
    }

    @FXML
    public void backToMenu() {
        MusicPlayer.playClickSound();
        SceneManager.switchScene("/view/main_menu.fxml"); // Tambahkan "/"
    }
}