package lk.ijse.chat_app.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class ClientFormController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnSend;

    @FXML
    private ImageView imgCamera;

    @FXML
    private ImageView imgImoji;

    @FXML
    private Label lblUserName;

    @FXML
    private TextArea txtArea;

    @FXML
    private TextField txtField;

    @FXML
    void btnSendOnAction(ActionEvent event) {

    }

    @FXML
    void imgCameraOnAction(MouseEvent event) {

    }

    @FXML
    void imgImojiOnAction(MouseEvent event) {

    }

    @FXML
    void initialize() {
        lblUserName.setText(LoginFormController.userName);
    }
}
