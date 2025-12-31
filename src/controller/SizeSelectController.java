package controller;

import javafx.fxml.FXML;
import model.GameState;
import util.MusicPlayer;
import util.SceneManager;
import util.SaveManager;

public class SizeSelectController {

    @FXML
    public void select3x3() { startGame(3); }

    @FXML
    public void select4x4() { startGame(4); }

    @FXML
    public void select5x5() { startGame(5); }

    private void startGame(int size) {
        MusicPlayer.playClickSound();

        // 1. Set ukuran di GameState
        GameState.getInstance().setSize(size);

        // 2. Karena ini game baru dari menu pilih ukuran, hapus save lama & reset board
        SaveManager.deleteSave();
        GameState.getInstance().setBoard(null);

        // 3. Pindah ke arena permainan
        SceneManager.switchScene("/view/game.fxml");
    }

    @FXML
    public void backToMode() {
        MusicPlayer.playClickSound();
        SceneManager.switchScene("/view/mode_select.fxml");
    }
}