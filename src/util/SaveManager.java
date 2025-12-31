package util;

import model.GameState;
import java.io.*;

public class SaveManager {

    private static final String FILE = "save.dat";

    public static void save() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE))) {
            // Menyimpan seluruh object GameState (termasuk board dan imageMode)
            oos.writeObject(GameState.getInstance());
        } catch (IOException e) {
            System.err.println("Gagal menyimpan data: " + e.getMessage());
        }
    }

    public static void load() {
        File file = new File(FILE);
        if (!file.exists()) return;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            GameState loaded = (GameState) ois.readObject();
            // Mengganti instance di memori dengan data dari file
            GameState.setInstance(loaded);
        } catch (Exception e) {
            System.err.println("Gagal memuat save data: " + e.getMessage());
            // Opsional: hapus file jika rusak agar tidak error terus menerus
            file.delete();
        }
    }

    public static boolean hasSave() {
        return new File(FILE).exists();
    }

    public static void deleteSave() {
        File file = new File(FILE);
        if (file.exists()) {
            file.delete();
        }
        // Perbaikan: Gunakan getInstance() karena setBoard bukan static lagi
        if (GameState.getInstance() != null) {
            GameState.getInstance().setBoard(null);
        }
    }
}