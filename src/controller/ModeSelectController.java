package controller;

import javafx.fxml.FXML; // Pastikan ini di-import
import model.GameState;
import util.MusicPlayer;
import util.SceneManager;

public class ModeSelectController {

    @FXML
    public void chooseImageMode() {
        GameState.getInstance().setImageMode(true);
        GameState.getInstance().setBoard(null); // Reset board agar baru
        SceneManager.switchScene("/view/size_select.fxml");
    }

    @FXML
    public void chooseNumberMode() {
        GameState.getInstance().setImageMode(false);
        GameState.getInstance().setBoard(null);
        SceneManager.switchScene("/view/size_select.fxml");
    }

    @FXML
    public void backToMenu() {
        MusicPlayer.playClickSound();
        SceneManager.switchScene("/view/main_menu.fxml");
    }
}