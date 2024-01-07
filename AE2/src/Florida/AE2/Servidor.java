package Florida.AE2;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * La clase Servidor acepta conexiones de clientes y lanza nuevos hilos
 * para cada cliente que se conecta
 */
public class Servidor {
	// Lista de clientes conectados al servidor
	public static List<Peticion> clientesConectados = Collections.synchronizedList(new ArrayList<Peticion>());
	
	/**
	 * Método principal que se ejecuta al iniciar el programa
	 * Crea un nuevo ServerSocket que escucha en el puerto 9876
	 * 
	 * Acepta nuevas conexiones de clientes, crea una nueva instancia de Peticion para cada cliente,
	 * y lanza un nuevo hilo para manejar la conexión del cliente
	 * 
	 * @param args Elementos de la linea de comandos, no utilizados actualmente
	 * @throws IOException En caso de error de entrada o salida
	 */
	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException {
		System.err.println("SERVIDOR >>> Arranca el servidor, espera peticion");
		ServerSocket socketEscucha = null;
		try {
			// Crea un nuevo ServerSocket que escucha en el puerto 9876
			socketEscucha = new ServerSocket(9876);
		} catch(IOException e) {
			System.err.println("SERVIDOR >>> Error");
			return;
		}

		while (true) {
            try {
                // Acepta una nueva conexión de cliente
                Socket conexion = socketEscucha.accept();
                System.err.println("SERVIDOR >>> Conexion recibida ----> Lanza hilo clase Peticion");
                
                // Crea una nueva instancia de Peticion para manejar la conexión del cliente
                Peticion p = new Peticion(conexion, clientesConectados);
                
                // Lanza un nuevo hilo para manejar la conexión del cliente
                Thread hilo = new Thread(p);
                hilo.start();
            } catch(IOException e) {
                System.err.println("SERVIDOR >>> Error al aceptar la conexión del cliente");
            }
        }
    }
}
