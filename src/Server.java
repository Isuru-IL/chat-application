import lk.ijse.chat_app.controller.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    static ServerSocket serverSocket;
    ArrayList<ClientHandler> client = new ArrayList<>();

    public void startServer(){
        try{

            while (!serverSocket.isClosed()){
                Socket socket = serverSocket.accept();
                System.out.println("A new client has connected");

                ClientHandler clientHandler = new ClientHandler(socket, client);
                client.add(clientHandler);
                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
            serverSocket.isClosed();
        }
    }

    public static void main(String[] args) throws IOException {
        serverSocket = new ServerSocket(3001);

        Server server = new Server();
        server.startServer();
    }
}
