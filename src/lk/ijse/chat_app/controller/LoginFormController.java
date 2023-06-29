package lk.ijse.chat_app.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginFormController {

    static String userName;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtUserName;

    @FXML
    void btnLogInOnAction(ActionEvent event) throws IOException {
        userName = txtUserName.getText();
        txtUserName.clear();

        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(LoginFormController.class.getResource("/lk/ijse/chat_app/view/client_form.fxml"))));
        stage.close();
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    void initialize() {

    }

}
