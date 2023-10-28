package es.pgv.Servidor_UTF;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        final int PORT = 40080;
        try {
            @SuppressWarnings("resource")
			ServerSocket sk = new ServerSocket(PORT);
            System.out.println("Servidor inicializado; Esperando clientes por el puerto 40080...");
            
            while(true){
                Socket socket = sk.accept();
                System.out.println("Alguien se conectó");
                
                HiloParaAntenderUnCliente_UTF hilo = new HiloParaAntenderUnCliente_UTF(socket);
                hilo.start();
            }
        } catch (IOException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
}
