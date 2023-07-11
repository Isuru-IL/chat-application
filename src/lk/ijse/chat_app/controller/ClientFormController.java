package lk.ijse.chat_app.controller;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;

public class ClientFormController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnSend;

    @FXML
    private HBox hBoxImoji;

    @FXML
    private ImageView imgCamera;

    @FXML
    private ImageView imgImoji;

    @FXML
    private Label lblUserName;

    @FXML
    private ScrollPane scrollpClient;

    @FXML
    private ScrollPane scrollpImoji;

    @FXML
    private TextField txtField;

    @FXML
    private VBox vBoxClient;

    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    Socket socket;
    String message;

    @FXML
    void txtFieldOnMouseClicked(MouseEvent event) {
        scrollpImoji.setVisible(false);
        hBoxImoji.setVisible(false);
    }

    @FXML
    void btnSendOnAction(ActionEvent event) {
        try {
            dataOutputStream.writeUTF(lblUserName.getText() + " :" + txtField.getText());
            dataOutputStream.flush();

            Text text = new Text("Me :" + txtField.getText());
            text.setStyle("-fx-font-size: 14");

            TextFlow textFlow = new TextFlow(text);
            textFlow.setStyle("-fx-background-color:  #00cc00; -fx-font-weight: bold; -fx-text-fill: white; -fx-background-radius: 0 10 10 10");
            textFlow.setPadding(new Insets(10, 8, 10, 10));

            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER_RIGHT);
            hBox.getChildren().add(textFlow);

            vBoxClient.getChildren().add(hBox);
            txtField.clear();

            hBoxImoji.setVisible(false);
            scrollpImoji.setVisible(false);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void imgCameraOnAction(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        File selectedImg = fileChooser.showOpenDialog(null);
        if(selectedImg != null){
            sendImage(selectedImg);
        }
    }

    @FXML
    void imgImojiOnAction(MouseEvent event) {
        scrollpImoji.setVisible(true);
        hBoxImoji.setVisible(true);
        hBoxImoji.setStyle("-fx-background-color: #ffffff;");
    }

    private void getServerMessage() {
        new Thread(()->{
            while (true){
                try {
                    message = dataInputStream.readUTF();
                    Text text = new Text(message);
                    text.setStyle("-fx-font-size: 14");

                    TextFlow textFlow = new TextFlow(text);
                    textFlow.setStyle("-fx-background-color: #999999; -fx-font-weight: bold; -fx-color: white; -fx-background-radius: 0 10 10 10");
                    textFlow.setPadding(new Insets(10, 8, 10, 10));

                    HBox hBox = new HBox(10);
                    hBox.setAlignment(Pos.CENTER_LEFT);

                    if(message.charAt(0) == '#'){
                        String imgMassege = message.substring(1);

                        Image image = new Image(imgMassege);
                        ImageView imageView = new ImageView(image);
                        hBox.getChildren().add(imageView);

                    }else if (message.charAt(0) == '@'){
                        String imojiMsg = message.substring(1);

                        text = new Text(imojiMsg);
                        text.setStyle("-fx-font-size: 14");

                        textFlow = new TextFlow(text);
                        textFlow.setStyle("-fx-background-color: #999999; -fx-font-weight: bold; -fx-color: white; -fx-background-radius:0 10 10 10");
                        textFlow.setPadding(new Insets(10, 8, 10, 10));
                        hBox.getChildren().add(textFlow);

                    }else {
                        hBox.getChildren().add(textFlow);
                    }

                    Platform.runLater(()->{
                        vBoxClient.getChildren().add(hBox);
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    void getConnection(){
        new Thread(()->{
            try {
                socket = new Socket("localhost", 3001);
                dataInputStream = new DataInputStream(socket.getInputStream());
                dataOutputStream = new DataOutputStream(socket.getOutputStream());

                getServerMessage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @FXML
    void initialize() {
        lblUserName.setText(LoginFormController.userName);
        getConnection();

        loadEmoji("\uD83D\uDE00");
        loadEmoji("\uD83C\uDF1E"); // Sun with Face
        loadEmoji("\u2764\ufe0f");

        vBoxClient.setSpacing(7);
        vBoxClient.setStyle("-fx-background-color: #ffffff;");

    }

    private void loadEmoji(String grinningFace) {
        Label label = new Label(grinningFace);
        label.setPadding(new Insets(5, 5, 5, 5));
        hBoxImoji.getChildren().add(label);

        label.setOnMouseClicked(event -> {
            try {
                dataOutputStream.writeUTF("@" + grinningFace);
                dataOutputStream.flush();

                HBox hBox = new HBox(10);

                Text text = new Text(grinningFace);
                text.setStyle("-fx-font-size: 14");

                TextFlow textFlow = new TextFlow(text);
                textFlow.setStyle("-fx-background-color: #00cc00; -fx-font-weight: bold; -fx-color: white; -fx-background-radius: 0 10 10 10");
                textFlow.setPadding(new Insets(10, 8, 10, 10));

                hBox.setAlignment(Pos.BASELINE_RIGHT);
                hBox.getChildren().addAll(textFlow);
                vBoxClient.getChildren().add(hBox);

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void sendImage(File selectedImg) {
        try {
            dataOutputStream.writeUTF("#" + selectedImg.toURI());
            dataOutputStream.flush();

            Image image = new Image(selectedImg.toURI().toString());

            ImageView imageView = new ImageView(image);

            VBox vBox = new VBox();
            vBox.setAlignment(Pos.BASELINE_RIGHT);
            vBox.setPrefWidth(10);
            vBox.setPadding(new Insets(10, 10, 10, 10));
            vBox.getChildren().addAll(imageView);

            vBoxClient.getChildren().add(vBox);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
