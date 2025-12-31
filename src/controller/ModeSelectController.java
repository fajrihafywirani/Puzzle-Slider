package controller;

import javafx.fxml.FXML; // Pastikan ini di-import
import model.GameState;
import util.SceneManager;

public class ModeSelectController {

    @FXML
    public void numberMode() {
        GameState.setImageMode(false);
        SceneManager.switchScene("/view/game.fxml"); // Tambahkan "/"
    }

    @FXML
    public void imageMode() {
        GameState.setImageMode(true);
        SceneManager.switchScene("/view/game.fxml"); // Tambahkan "/"
    }

    @FXML
    public void backToMenu() {
        SceneManager.switchScene("/view/main_menu.fxml"); // Tambahkan "/"
    }
}