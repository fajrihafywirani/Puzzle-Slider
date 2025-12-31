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

        // Di initialize() GameController
        if (state.getBoard() != null) {
            puzzle = new PuzzleBoard(state.getBoard());
        } else {
            // Memberikan parameter size (3, 4, atau 5)
            puzzle = new PuzzleBoard(state.getSize());
            state.setBoard(puzzle.board);
        }
        drawBoard();
    }

    private void drawBoard() {
        grid.getChildren().clear();
        GameState state = GameState.getInstance();
        int size = state.getSize();

        // Tentukan gambar berdasarkan ukuran
        String imageName = "sample3.jpeg"; // Default
        if (size == 4) imageName = "sample4.jpeg";
        else if (size == 5) imageName = "sample5.jpeg";

        Image fullImage = null;
        if (state.isImageMode()) {
            fullImage = new Image(getClass().getResourceAsStream("/images/" + imageName));
        }

        // Atur GridPane agar ukurannya pas
        grid.setHgap(2); grid.setVgap(2);

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int value = puzzle.board[i][j];
                if (value != 0) {
                    Button btn = new Button();
                    // Ukuran tombol otomatis mengecil jika papan semakin besar
                    double btnSize = 400.0 / size;
                    btn.setPrefSize(btnSize, btnSize);

                    if (state.isImageMode() && fullImage != null) {
                        // Logika potong gambar dinamis berdasarkan 'size'
                        int originalRow = (value - 1) / size;
                        int originalCol = (value - 1) % size;
                        double tileW = fullImage.getWidth() / size;
                        double tileH = fullImage.getHeight() / size;

                        WritableImage cropped = new WritableImage(
                                fullImage.getPixelReader(),
                                (int)(originalCol * tileW), (int)(originalRow * tileH),
                                (int)tileW, (int)tileH
                        );

                        ImageView view = new ImageView(cropped);
                        view.setFitWidth(btnSize);
                        view.setFitHeight(btnSize);
                        btn.setGraphic(view);
                        btn.getStyleClass().add("grid-button-image");
                    } else {
                        btn.setText(String.valueOf(value));
                        btn.getStyleClass().add("grid-button");
                    }

                    // Aksi tombol
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
