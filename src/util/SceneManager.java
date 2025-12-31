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
            // PERBAIKAN: Gunakan parameter 'fxml' agar bisa berpindah ke file lain
            var resource = SceneManager.class.getResource(fxml);

            if (resource == null) {
                System.err.println("Error: File FXML tidak ditemukan di path: " + fxml);
                return;
            }

            FXMLLoader loader = new FXMLLoader(resource);
            Scene scene = new Scene(loader.load());

            // Load CSS
            var cssResource = SceneManager.class.getResource("/style/style.css");
            if (cssResource != null) {
                scene.getStylesheets().add(cssResource.toExternalForm());
            }

            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();

        } catch (Exception e) {
            System.err.println("Gagal memuat file FXML: " + fxml);
            e.printStackTrace();
        }
    }
}
