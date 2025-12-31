package controller;

import javafx.fxml.FXML;
import util.MusicPlayer;
import util.SaveManager;
import util.SceneManager;
import model.GameState;

public class WinController {

    @FXML
    public void restartGame() {
        // Hapus save lama agar generate puzzle baru yang teracak
        SaveManager.deleteSave();
        MusicPlayer.playClickSound();
        GameState.setBoard(null);
        SceneManager.switchScene("/view/game.fxml");
    }

    @FXML
    public void goToMenu() {
        SaveManager.deleteSave();
        MusicPlayer.playClickSound();
        GameState.setBoard(null);
        SceneManager.switchScene("/view/main_menu.fxml");
    }
}