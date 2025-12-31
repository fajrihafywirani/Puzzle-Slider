package model;

import java.io.Serializable;

public class GameState implements Serializable {

    private static boolean imageMode = false;
    private static int[][] board;

    public static boolean isImageMode() {
        return imageMode;
    }

    public static void setImageMode(boolean mode) {
        imageMode = mode;
    }

    public static int[][] getBoard() {
        return board;
    }

    public static void setBoard(int[][] b) {
        board = b;
    }
}
