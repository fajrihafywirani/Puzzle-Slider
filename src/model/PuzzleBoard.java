package model;

import java.util.Random;

public class PuzzleBoard {

    public int[][] board;
    public int emptyRow, emptyCol;

    public PuzzleBoard() {
        // Inisialisasi papan target
        board = new int[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 0}
        };
        emptyRow = 2;
        emptyCol = 2;

        // PENTING: Pastikan ini dipanggil agar teracak saat Game Baru
        shuffle(100);
    }

    public PuzzleBoard(int[][] saved) {
        this.board = saved;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == 0) {
                    emptyRow = i;
                    emptyCol = j;
                }
            }
        }
    }

    private void shuffle(int moves) {
        Random rand = new Random();
        int count = 0;
        while (count < moves) {
            int dr = 0, dc = 0;
            int dir = rand.nextInt(4);
            if (dir == 0) dr = -1;
            else if (dir == 1) dr = 1;
            else if (dir == 2) dc = -1;
            else if (dir == 3) dc = 1;

            int tr = emptyRow + dr;
            int tc = emptyCol + dc;

            if (tr >= 0 && tr < 3 && tc >= 0 && tc < 3) {
                // Gunakan logika move internal tanpa cek isWin
                board[emptyRow][emptyCol] = board[tr][tc];
                board[tr][tc] = 0;
                emptyRow = tr;
                emptyCol = tc;
                count++;
            }
        }
    }

    // METHOD BARU: Cek apakah susunan sudah benar
    public boolean isWin() {
        int count = 1;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (i == 2 && j == 2) return board[i][j] == 0;
                if (board[i][j] != count) return false;
                count++;
            }
        }
        return true;
    }

    public boolean move(int r, int c) {
        if (Math.abs(emptyRow - r) + Math.abs(emptyCol - c) == 1) {
            board[emptyRow][emptyCol] = board[r][c];
            board[r][c] = 0;
            emptyRow = r;
            emptyCol = c;
            return true;
        }
        return false;
    }
}