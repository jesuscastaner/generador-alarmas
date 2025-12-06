package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientManager implements Runnable {

    private final Socket socket;

    public ClientManager(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        try (InputStream is = socket.getInputStream();
             OutputStream os = socket.getOutputStream()
        ) {
            // leer input del cliente
            byte[] buffer = new byte[1024];
            int bytesRead = is.read(buffer);

            if (bytesRead != -1) {
                // imprimir input del cliente en la consola
                String input = new String(buffer, 0, bytesRead).trim();
                System.out.println("Input del cliente: " + input);

                // enviar respuesta al cliente
                String output = "Alarma recibida";
                os.write(output.getBytes());
                os.flush();
            }

            // cerrar conexion con el cliente
            socket.close();
            System.out.println("Conexion con un cliente cerrada");

        } catch (Exception e) {
            System.err.println("Error al atender a un cliente: " + e.getMessage());

        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                // ignorar
            }
        }
    }
}
