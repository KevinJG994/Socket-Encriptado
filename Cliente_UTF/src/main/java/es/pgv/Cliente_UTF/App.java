package es.pgv.Cliente_UTF;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Base64;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App {
    final static int PORT = 40080;
    final static String HOST = "localhost";

    public static void main(String[] args) {
        try {
            Socket sk = new Socket(HOST, PORT);
            System.out.println("Conexión establecida. Puedes empezar a enviar mensajes.");
            enviarMensajesAlServidor(sk);
        } catch (IOException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static void enviarMensajesAlServidor(Socket sk) {
        try {
            DataInputStream flujo_entrada = new DataInputStream(sk.getInputStream());
            DataOutputStream flujo_salida = new DataOutputStream(sk.getOutputStream());
            Scanner scanner = new Scanner(System.in);

            while (true) {
                // Esperar mensaje del usuario
                System.out.print("Cliente dice: ");
                String mensajeCliente = scanner.nextLine();
                
                System.out.println("Esperando respuesta del servidor... \n");
        
                
                String mensajeEncriptado = Base64.getEncoder().encodeToString(mensajeCliente.getBytes());
                
                // Enviar mensaje al servidor
                flujo_salida.writeUTF(mensajeEncriptado);
                flujo_salida.flush();  // Asegurar que los datos se envíen inmediatamente
       
                
                // Esperar respuesta del servidor
                String mensajeServidorEncriptado = flujo_entrada.readUTF();
                
                
                byte[] mensajeDesencriptadoBytes = Base64.getDecoder().decode(mensajeServidorEncriptado);
                String mensajeDesencriptado = new String(mensajeDesencriptadoBytes);
                
                System.out.println("Servidor dice: " + mensajeDesencriptado);
                
            }
        } catch (IOException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}