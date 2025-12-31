package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import util.MusicPlayer;
import util.SceneManager;

public class SettingsController {
    @FXML private Slider musicSlider;
    @FXML private Slider sfxSlider;

    @FXML
    public void initialize() {
        // Set nilai slider sesuai volume saat ini
        musicSlider.setValue(MusicPlayer.getMusicVolume() * 100);
        sfxSlider.setValue(MusicPlayer.getSfxVolume() * 100);

        // Listener untuk perubahan volume musik
        musicSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            MusicPlayer.setMusicVolume(newVal.doubleValue() / 100.0);
        });

        // Listener untuk perubahan volume SFX
        sfxSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            MusicPlayer.setSfxVolume(newVal.doubleValue() / 100.0);
        });
    }

    @FXML
    public void backToMenu() {
        MusicPlayer.playClickSound();
        SceneManager.switchScene("/view/main_menu.fxml");
    }
}