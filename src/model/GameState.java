package model;

import java.io.Serializable;

public class GameState implements Serializable {
    // Versi ID untuk validasi serialisasi
    private static final long serialVersionUID = 1L;

    // Hapus keyword static agar bisa di-save ke file
    private boolean imageMode = false;
    private int[][] board;

    // Gunakan Singleton agar data mudah diakses dari Controller mana pun
    private static GameState instance;

    private GameState() {} // Constructor private

    public static GameState getInstance() {
        if (instance == null) {
            instance = new GameState();
        }
        return instance;
    }

    // Untuk memuat data hasil load dari SaveManager
    public static void setInstance(GameState loadedState) {
        instance = loadedState;
    }

    // Getter dan Setter non-static
    public boolean isImageMode() { return imageMode; }
    public void setImageMode(boolean mode) { this.imageMode = mode; }
    public int[][] getBoard() { return board; }
    public void setBoard(int[][] b) { this.board = b; }
}