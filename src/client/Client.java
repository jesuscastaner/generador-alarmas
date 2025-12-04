package client;

import server.Server;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {

        try (Socket socket = new Socket();
             Scanner scanner = new Scanner(System.in)
        ) {
            // conectarse al servidor
            socket.connect(new InetSocketAddress(Server.IP, Server.PORT));
            System.out.println("Cliente conectado al servidor");

            // pedir input al usuario
            System.out.print("Escribe el texto de la alarma: ");
            String input = "ALARMA: " + scanner.nextLine().trim();

            // enviar input al servidor
            OutputStream os = socket.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            // esperar respuesta del servidor
            InputStream is = socket.getInputStream();
            byte[] buffer = new byte[1024];
            int bytesRead = is.read(buffer);

            // mostrar respuesta del servidor
            if (bytesRead != -1) {
                String output = new String(buffer, 0, bytesRead).trim();
                System.out.println(output);
            } else {
                System.out.println("No se ha recibido respuesta del servidor");
            }

            // cerrar conexion con el servidor (automatico con try-with-resources)
            System.out.println("Cliente desconectado del servidor");

        } catch (Exception e) {
            System.err.println("Error en el cliente: " + e.getMessage());
        }
    }
}
