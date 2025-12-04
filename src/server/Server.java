package server;

import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static final int PORT = 5000;
    public static final String IP = "127.0.0.1";

    public static void main(String[] args) {

        // lanzar el servidor
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Servidor escuchando en el puerto " + PORT);

            // bucle para permitir multihilo (multiples clientes a la vez)
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Cliente conectado al servidor");

                // lanzar el client manager en un nuevo hilo
                ClientManager clientManager = new ClientManager(clientSocket);
                Thread thread = new Thread(clientManager);
                thread.start();
            }

        } catch (Exception e) {
            System.err.println("Error en el servidor: " + e.getMessage());
        }
    }
}
