package model;

import java.util.Random;

public class PuzzleBoard {
    public int[][] board;
    public int emptyRow, emptyCol;
    private int size; // Variabel size agar dinamis

    // Constructor untuk GAME BARU
    public PuzzleBoard(int size) {
        this.size = size;
        this.board = new int[size][size];

        int count = 1;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = count++;
            }
        }

        // Slot kosong di pojok kanan bawah
        board[size - 1][size - 1] = 0;
        emptyRow = size - 1;
        emptyCol = size - 1;

        // Shuffle berdasarkan ukuran (papan besar butuh shuffle lebih banyak)
        shuffle(size * size * 20);
    }

    // Constructor untuk LOAD GAME
    public PuzzleBoard(int[][] saved) {
        this.board = saved;
        this.size = saved.length; // Otomatis deteksi ukuran dari array yang di-load

        // Cari posisi angka 0
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == 0) {
                    this.emptyRow = i;
                    this.emptyCol = j;
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
            if (dir == 0) dr = -1;      // Atas
            else if (dir == 1) dr = 1;  // Bawah
            else if (dir == 2) dc = -1; // Kiri
            else if (dir == 3) dc = 1;  // Kanan

            int tr = emptyRow + dr;
            int tc = emptyCol + dc;

            // Perbaikan: Batas koordinat menggunakan 'size'
            if (tr >= 0 && tr < size && tc >= 0 && tc < size) {
                board[emptyRow][emptyCol] = board[tr][tc];
                board[tr][tc] = 0;
                emptyRow = tr;
                emptyCol = tc;
                count++;
            }
        }
    }

    public boolean isWin() {
        int count = 1;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                // Cek kotak terakhir harus 0
                if (i == size - 1 && j == size - 1) {
                    return board[i][j] == 0;
                }
                // Cek urutan angka
                if (board[i][j] != count++) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean move(int r, int c) {
        // Logika jarak Manhattan (selisih 1 kotak)
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