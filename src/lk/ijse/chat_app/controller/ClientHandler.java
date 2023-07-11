package lk.ijse.chat_app.controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable{
    Socket socket;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    ArrayList<ClientHandler> client;

    public ClientHandler(Socket socket, ArrayList<ClientHandler> client) {
        this.socket = socket;
        this.client = client;
    }

    @Override
    public void run() {
        try {
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            String clientMessage = "";

            while (!clientMessage.equals("finish")){
                clientMessage = dataInputStream.readUTF();
                System.out.println("Client msg ="+clientMessage);

                if(clientMessage.charAt(0) == '#'){
                    for (ClientHandler clientHandler : client) {
                        if(clientHandler != this){
                            clientHandler.sendImageMessage(clientMessage);
                        }
                    }
                }else if(clientMessage.charAt(0) == '@'){
                    for (ClientHandler clientHandler : client) {
                        if(clientHandler != this){
                            clientHandler.sendEmojiMessage(clientMessage);
                        }
                    }
                }else {
                    for (ClientHandler clientHandler : client) {
                        if(clientHandler != this){
                            clientHandler.sendMessage(clientMessage);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(String clientMessage) {
        try {
            dataOutputStream.writeUTF(clientMessage);
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendImageMessage(String clientMessage) {
        try {
            dataOutputStream.writeUTF(clientMessage);
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendEmojiMessage(String clientMessage) {
        try {
            dataOutputStream.writeUTF(clientMessage);
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
