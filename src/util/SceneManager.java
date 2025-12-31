package util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneManager {

    private static Stage stage;

    public static void setStage(Stage s) {
        stage = s;
    }

    public static void switchScene(String fxml) {
        try {
            var resource = SceneManager.class.getResource(fxml);
            if (resource == null) {
                System.err.println("Error: File FXML tidak ditemukan: " + fxml);
                return;
            }

            // 1. Simpan status maximized
            boolean wasMaximized = stage.isMaximized();

            FXMLLoader loader = new FXMLLoader(resource);
            javafx.scene.Parent root = loader.load();

            // 2. Jika tidak maximized, kita bisa tentukan ukuran default
            // atau biarkan mengikuti scene sebelumnya
            Scene scene = stage.getScene();
            if (scene == null) {
                // Ini untuk pertama kali aplikasi jalan (Main Menu)
                scene = new Scene(root, 500, 600);
            } else {
                // Ganti isi scene tanpa membuat objek Scene baru agar ukuran stabil
                scene.setRoot(root);
            }

            // Load CSS
            var cssResource = SceneManager.class.getResource("/style/style.css");
            if (cssResource != null) {
                scene.getStylesheets().clear();
                scene.getStylesheets().add(cssResource.toExternalForm());
            }

            // OTOMATISASI SUARA HOVER DAN KLIK
            scene.getRoot().addEventFilter(javafx.scene.input.MouseEvent.MOUSE_ENTERED_TARGET, event -> {
                if (event.getTarget() instanceof javafx.scene.control.Button) {
                    MusicPlayer.playHoverSound();
                }
            });

            scene.getRoot().addEventFilter(javafx.scene.input.MouseEvent.MOUSE_CLICKED, event -> {
                if (event.getTarget() instanceof javafx.scene.control.Button) {
                    MusicPlayer.playClickSound();
                }
            });

            stage.setScene(scene);

            // 3. Logika Posisi dan Ukuran
            if (wasMaximized) {
                stage.setMaximized(true);
            } else {
                // Memaksa stage menghitung ulang ukuran dan menaruhnya di tengah
                stage.sizeToScene();
                stage.centerOnScreen();
            }

            stage.show();

        } catch (Exception e) {
            System.err.println("Gagal memuat: " + fxml);
            e.printStackTrace();
        }
    }
}
