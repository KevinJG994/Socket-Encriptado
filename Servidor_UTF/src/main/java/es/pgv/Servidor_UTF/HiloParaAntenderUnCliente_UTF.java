package es.pgv.Servidor_UTF;



import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Inet4Address;
import java.net.Socket;
import java.util.Base64;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class HiloParaAntenderUnCliente_UTF extends Thread {
    Socket sk;

    public HiloParaAntenderUnCliente_UTF(Socket sk) {
        this.sk = sk;
    }

    @Override
    public void run() {
        try {
            // Establecer flujos de entrada y salida para el cliente actual
            DataInputStream flujo_entrada = new DataInputStream(this.sk.getInputStream());
            DataOutputStream flujo_salida = new DataOutputStream(this.sk.getOutputStream());

            // Flujo de entrada del servidor
            Scanner scanner = new Scanner(System.in);

            while (true) {
                
            	// Esperar mensaje del cliente
            	String mensajeClienteEncriptado = flujo_entrada.readUTF();
                
                // Desencriptar mensaje recibido del cliente
                byte[] mensajeDesencriptadoBytes = Base64.getDecoder().decode(mensajeClienteEncriptado);
                String mensajeDesencriptado = new String(mensajeDesencriptadoBytes);
                
                System.out.println("Cliente dice: " + mensajeDesencriptado);
               
                // Pedir mensaje al servidor
                System.out.print("Servidor dice: ");
                String mensajeServidor = scanner.nextLine();
                
                
                 System.out.println("Esperando mensaje del cliente... \n");
                // Encriptar mensaje usando Base64 antes de enviarlo al cliente
                String mensajeServidorEncriptado = Base64.getEncoder().encodeToString(mensajeServidor.getBytes());

                
                // Enviar mensaje al cliente
                flujo_salida.writeUTF(mensajeServidorEncriptado);
                flujo_salida.flush();  // Asegurar que los datos se envíen inmediatamente
            }

        } catch (IOException ex) {
        	System.out.println("Cliente desconectado");
        } finally {
            try {
                sk.close();
            } catch (IOException ex) {
                Logger.getLogger(HiloParaAntenderUnCliente_UTF.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
