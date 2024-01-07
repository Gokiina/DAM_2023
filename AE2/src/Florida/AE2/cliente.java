package Florida.AE2;

import java.io.*;
import java.net.*;

/**
 *  La clase cliente conecta al cliente con el servidor,
 *  autentifica el usuario; manejando su entrada y salida de mensajes
 */
public class Cliente {
	/**
	 * Método principal que se ejecuta al iniciar el programa
	 * 
	 * Crea una conexión con el servidor, solicita al usuario su nombre de usuario y contraseña,
	 * y luego autentifica al usuario.
	 * Si la autenticación es exitosa, el usuario puede empezar a enviar mensajes.
	 * 
	 * @param args Elementos de la linea de comandos, no utilizados actualmente
	 * @throws IOException En caso de error de entrada o salida
	 */
	public static void main(String[] args) throws IOException {
		System.out.println("CLIENTE >>> Arranca cliente");
		System.out.println("CLIENTE >>> Conexion al servidor");
		
		// Dirección del servidor
		InetSocketAddress direccion = new InetSocketAddress("localhost", 9876);
		
		try (Socket socket = new Socket()) {
			// Conecta con el servidor
			socket.connect(direccion);
			
			// Crea los flujos de entrada y salida
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
			
			boolean autentificado = false;
			do {
				// Solicita al usuario su nombre de usuario y contraseña
				System.out.println("Por favor, introduce tu nombre de usuario:");
				String username = stdIn.readLine();
				out.println(username);
				
				System.out.println("Por favor, introduce tu contraseña:");
				String password = stdIn.readLine();
				out.println(password);
				
				// Recibe la respuesta del servidor
				String serverResponse = in.readLine();
				if (serverResponse.equals("ok")) {
					// Si la autenticación es correcta, el usuario puede empezar a enviar mensajes
					autentificado = true;
					System.out.println("Estás autenticado. Ahora puedes empezar a enviar mensajes.");
					
					// Crea un hilo para escuchar los mensajes del servidor
					Thread escucha = new Thread(new Runnable() {
						public void run() {
							try {
								String mensaje;
								while ((mensaje = in.readLine()) != null) {
									System.out.println(mensaje);
								}
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					});
					escucha.start();
					
					// Crea un hilo para enviar mensajes al servidor
					Thread envia = new Thread(new Runnable() {
						public void run() {
							try {
								String mensaje;
								while ((mensaje = stdIn.readLine()) != null) {
									out.println(mensaje);
									if (mensaje.equals("exit")) {
										break;
									}
								}
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					});
					envia.start();

					// Espera a que ambos hilos terminen
					try {
						escucha.join();
						envia.join();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} else {
				    System.out.println("Validación incorrecta");
				    return;
				}
			} while (!autentificado);
		}
	}
}