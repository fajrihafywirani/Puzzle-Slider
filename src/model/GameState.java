package model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class GameState implements Serializable {
    private static final long serialVersionUID = 1L;

    // Simpan Best Time: Key adalah ukuran (3, 4, 5), Value adalah detik terkecil
    private Map<Integer, Integer> bestTimes = new HashMap<>();

    private int size = 3; // Default 3x3
    private boolean imageMode = false;
    private int[][] board;
    private int imageIndex = 0;

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
    public int getImageIndex() { return imageIndex; }
    public void setImageIndex(int index) { this.imageIndex = index; }

    // Untuk memuat data hasil load dari SaveManager
    public static void setInstance(GameState loadedState) {
        instance = loadedState;
    }

    // Getter dan Setter non-static
    public boolean isImageMode() { return imageMode; }
    public void setImageMode(boolean mode) { this.imageMode = mode; }
    public int[][] getBoard() { return board; }
    public void setBoard(int[][] b) { this.board = b; }

    private int secondsElapsed = 0;

    public int getSecondsElapsed() { return secondsElapsed; }
    public void setSecondsElapsed(int seconds) { this.secondsElapsed = seconds; }

    public int getBestTime(int size) {
        return bestTimes.getOrDefault(size, Integer.MAX_VALUE);
    }

    public void setBestTime(int size, int time) {
        bestTimes.put(size, time);
    }
}