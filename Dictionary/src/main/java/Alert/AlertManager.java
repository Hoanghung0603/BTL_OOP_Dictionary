package Alert;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class AlertManager {
    public static void showAlert(Label label) {
        label.setVisible(true);
        FadeTransition fadeAlert = new FadeTransition(Duration.seconds(2.0), label);
        fadeAlert.setFromValue(1.0);
        fadeAlert.setToValue(0.0);
        fadeAlert.play();
    }

    public Alert alertConfirmation(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        return alert;
    }

    public Alert alertWarning(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        return alert;
    }
}
