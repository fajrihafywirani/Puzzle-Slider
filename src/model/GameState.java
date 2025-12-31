package model;

import java.io.Serializable;

public class GameState implements Serializable {
    private static final long serialVersionUID = 1L;
    private int size = 3; // Default 3x3
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

    public int getSize() { return size; }
    public void setSize(int s) { this.size = s; }

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