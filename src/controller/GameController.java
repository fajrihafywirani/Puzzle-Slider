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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;

public class GameController {

    @FXML
    private GridPane grid;

    @FXML
    private Label modeLabel;

    private PuzzleBoard puzzle;

    @FXML
    public void initialize() {
        try {
            MusicPlayer.playMusic("game.mpeg");
        } catch (Exception e) {
            System.err.println("Gagal memutar musik: " + e.getMessage());
        }

        // Gunakan Singleton secara konsisten
        GameState state = GameState.getInstance();

        if (modeLabel != null) {
            modeLabel.setText(state.isImageMode() ? "Mode: Puzzle Gambar" : "Mode: Puzzle Angka");
        }

        // Load jika ada save
        if (SaveManager.hasSave() && state.getBoard() == null) {
            SaveManager.load();
        }

        // Re-assign state setelah load (karena load mungkin mengganti instance)
        state = GameState.getInstance();

        if (state.getBoard() != null) {
            puzzle = new PuzzleBoard(state.getBoard());
        } else {
            puzzle = new PuzzleBoard();
            state.setBoard(puzzle.board);
        }
        drawBoard();
    }

    private void drawBoard() {
        grid.getChildren().clear();

        // Load gambar jika dalam mode gambar
        Image fullImage = null;
        if (GameState.getInstance().isImageMode()) {
            fullImage = new Image(getClass().getResourceAsStream("/images/sample.jpeg"));
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int value = puzzle.board[i][j];
                if (value != 0) {
                    Button btn = new Button();
                    btn.setPrefSize(100, 100);

                    if (GameState.getInstance().isImageMode() && fullImage != null) {
                        int originalRow = (value - 1) / 3;
                        int originalCol = (value - 1) % 3;

                        double tileW = fullImage.getWidth() / 3;
                        double tileH = fullImage.getHeight() / 3;

                        WritableImage croppedImage = new WritableImage(
                                fullImage.getPixelReader(),
                                (int)(originalCol * tileW),
                                (int)(originalRow * tileH),
                                (int)tileW,
                                (int)tileH
                        );

                        ImageView view = new ImageView(croppedImage);

                        // SAMAKAN dengan ukuran tombol agar FULL
                        view.setFitWidth(100);
                        view.setFitHeight(100);

                        // Agar gambar tidak gepeng jika aspect ratio tidak pas
                        view.setPreserveRatio(false);

                        btn.setGraphic(view);

                        // Tambahkan class CSS khusus gambar agar border/padding hilang
                        btn.getStyleClass().add("grid-button-image");
                    }

                    btn.getStyleClass().add("grid-button");

                    int r = i;
                    int c = j;

                    btn.setOnAction(e -> {
                        if (puzzle.move(r, c)) {
                            drawBoard();
                            GameState.getInstance().setBoard(puzzle.board);
                            if (puzzle.isWin()) showWinAlert();
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
            GameState.getInstance().setBoard(null);

            // Pindah ke Win Scene
            SceneManager.switchScene("/view/win_scene.fxml");
        });
        delay.play();
    }
    
    @FXML // Pastikan anotasi ini ada
    public void backToMenu() {
        MusicPlayer.playClickSound();
        if (puzzle != null) {
            GameState.getInstance().setBoard(puzzle.board);
            SaveManager.save();
        }
        // Tambahkan "/" di depan path agar sesuai dengan SceneManager
        SceneManager.switchScene("/view/main_menu.fxml");
    }
}
