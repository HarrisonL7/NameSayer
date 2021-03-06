package app.controllers;

import app.model.IUserModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * A StreakSceneController holds the responsibility of receiving input events
 * from the user while the streak window is open. It then translates them
 * into actions on the app.views.
 */

public class StreakSceneController {

    @FXML private Label _streakCounter;

    /**
     * Initialises the IUserModel object that the streaks information is
     * retrieved from.
     * @param userModel
     */
    public void setModel(IUserModel userModel) {
        _streakCounter.setText(String.valueOf(userModel.getDailyStreak()));
    }

    /**
     * When the okay button is pressed the streak window is closed.
     * @param event
     */
    public void okButtonAction(ActionEvent event) {
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.close();
    }
}
