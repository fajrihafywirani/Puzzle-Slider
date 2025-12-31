package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import model.GameState;
import model.PuzzleBoard;
import util.MusicPlayer;
import util.SaveManager;
import util.SceneManager;

public class GameController {

    @FXML
    private GridPane grid;

    @FXML
    private Label modeLabel;

    private PuzzleBoard puzzle;

    @FXML
    public void initialize() {
        if (modeLabel != null) {
            modeLabel.setText(GameState.isImageMode() ? "Mode: Puzzle Gambar" : "Mode: Puzzle Angka");
        }

        // 1. Jika ada file save, kita LOAD dulu isinya ke GameState
        if (SaveManager.hasSave() && GameState.getBoard() == null) {
            SaveManager.load();
        }

        // 2. Sekarang cek apakah GameState sudah punya data (hasil load tadi atau hasil move sebelumnya)
        if (GameState.getBoard() != null) {
            // Gunakan papan yang ada di memory/save
            puzzle = new PuzzleBoard(GameState.getBoard());
        } else {
            // Jika benar-benar kosong, buat baru (shuffle)
            puzzle = new PuzzleBoard();
            GameState.setBoard(puzzle.board);
        }

        drawBoard();
    }

    private void drawBoard() {
        grid.getChildren().clear();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {

                int value = puzzle.board[i][j];
                if (value != 0) {
                    Button btn = new Button(String.valueOf(value));
                    btn.setPrefSize(80, 80);

                    int r = i;
                    int c = j;

                    btn.setOnAction(e -> {
                        if (puzzle.move(r, c)) {
                            drawBoard();
                            GameState.setBoard(puzzle.board);

                            // TAMBAHKAN INI
                            if (puzzle.isWin()) {
                                System.out.println("Selamat! Anda Menang!");
                                // Anda bisa menambahkan Alert JavaFX di sini
                                showWinAlert();
                            }
                        }
                    });

                    grid.add(btn, j, i);
                }
            }
        }
    }

    @FXML
    private void showWinAlert() {
        // Jalankan sedikit delay agar user bisa melihat papan terakhir sebelum pindah
        javafx.animation.PauseTransition delay = new javafx.animation.PauseTransition(javafx.util.Duration.seconds(0.5));
        delay.setOnFinished(event -> {
            // Hapus data permainan yang sudah selesai
            SaveManager.deleteSave();
            GameState.setBoard(null);

            // Pindah ke Win Scene
            SceneManager.switchScene("/view/win_scene.fxml");
        });
        delay.play();
    }
    
    @FXML // Pastikan anotasi ini ada
    public void backToMenu() {
        MusicPlayer.playClickSound();
        if (puzzle != null) {
            GameState.setBoard(puzzle.board);
            SaveManager.save();
        }
        // Tambahkan "/" di depan path agar sesuai dengan SceneManager
        SceneManager.switchScene("/view/main_menu.fxml");
    }
}
