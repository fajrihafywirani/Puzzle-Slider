package controller;

import javafx.fxml.FXML;
import model.GameState;
import util.MusicPlayer;
import util.SceneManager;
import util.SaveManager;
import java.util.Random;

public class SizeSelectController {

    @FXML
    public void select3x3() { startGame(3); }

    @FXML
    public void select4x4() { startGame(4); }

    @FXML
    public void select5x5() { startGame(5); }

    private static final java.util.Random RAND = new java.util.Random();

    private void startGame(int size) {
        MusicPlayer.playClickSound();
        GameState state = GameState.getInstance();
        // 1. Set ukuran di GameState
        state.setSize(size);
        state.setSecondsElapsed(0); // RESET WAKTU KE NOL

        int totalGambar = 6;
        int randomIdx = RAND.nextInt(totalGambar) + 1;
        state.setImageIndex(randomIdx);

        // 2. Karena ini game baru dari menu pilih ukuran, hapus save lama & reset board
        SaveManager.deleteSave();
        state.setBoard(null);

        // 3. Pindah ke arena permainan
        SceneManager.switchScene("/view/game.fxml");
    }

    @FXML
    public void backToMode() {
        MusicPlayer.playClickSound();
        SceneManager.switchScene("/view/mode_select.fxml");
    }
}