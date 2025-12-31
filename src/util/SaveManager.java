package util;

import model.GameState;
import java.io.*;

public class SaveManager {

    private static final String FILE = "save.dat";

    public static void save() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE))) {
            oos.writeObject(GameState.getBoard());
            oos.writeBoolean(GameState.isImageMode()); // Tambahkan ini
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void load() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE))) {
            int[][] board = (int[][]) ois.readObject();
            boolean isImage = ois.readBoolean(); // Tambahkan ini

            GameState.setBoard(board);
            GameState.setImageMode(isImage); // Set kembali modenya
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean hasSave() {
        return new File(FILE).exists();
    }

    // Method baru untuk reset game
    public static void deleteSave() {
        File file = new File(FILE);
        if (file.exists()) {
            file.delete();
        }
        GameState.setBoard(null); // Pastikan state di memori juga bersih
    }
}