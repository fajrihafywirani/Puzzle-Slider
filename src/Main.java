import javafx.application.Application;
import javafx.stage.Stage;
import util.SceneManager;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        // 1. Berikan stage ke SceneManager terlebih dahulu
        SceneManager.setStage(stage);

        // 2. Tentukan judul aplikasi
        stage.setTitle("Puzzle Slider Game");

        // 3. Panggil scene pertama (Main Menu)
        SceneManager.switchScene("/view/main_menu.fxml");

        // 4. stage.show() sebenarnya sudah ada di dalam SceneManager.switchScene Anda
        // Tapi memanggilnya di sini lagi tidak masalah untuk memastikan window muncul.
        stage.show();
    }
}