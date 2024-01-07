package Florida.AE2;

import java.io.*;
import java.net.*;
import java.util.*;
import java.time.*;
import java.time.format.DateTimeFormatter;


/**
 * La clase Peticion implementa Runnable y representa una petición de un cliente.
 * Cada petición se ejecuta en un hilo separado.
 */
public class Peticion implements Runnable {
	BufferedReader bfr;
	PrintWriter pw;
	Socket socket;
	List<Usuario> usuariosAutorizados = new ArrayList<>();
	List<Peticion> clientesConectados;
	String username;

	/**
	 * Constructor de la clase Peticion
	 * 
	 * Inicia el socker y la lista de clientes
	 * Carga los usuarios autorizados desde un archivo
	 * 
	 * @param socket El socket asociado al cliente
	 * @param clientesConectados La lista de clientes actualmente en linea
	 */
	public Peticion (Socket socket, List<Peticion> clientesConectados) {
		this.socket = socket;
		this.clientesConectados = clientesConectados;
		
		try {
			// Cargar usuarios autorizados desde un archivo
			BufferedReader br = new BufferedReader(new FileReader("usuarios.txt"));
			String linea;
			while ((linea = br.readLine()) != null) {
			    String[] partes = linea.split(":");
			    usuariosAutorizados.add(new Usuario(partes[0], partes[1]));
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método run que se ejecuta cuando se inicia el hilo
	 * Lee el nombre de usuario y la contraseña del cliente, 
	 * y verifica si el usuario está autorizado.
	 * 
	 * Luego, entra en un bucle de lectura de mensajes del cliente y
	 * realiza acciones basadas en los mensajes que escribe
	 */
	public void run () {
		try {
			InputStream is = socket.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			bfr = new BufferedReader(isr);
			OutputStream os = socket.getOutputStream();
			pw = new PrintWriter(os, true);

			username = bfr.readLine();
			String password = bfr.readLine();
			
			// Comprobar si el nombre de usuario contiene espacios
			if (username.contains(" ")) {
				pw.println("error");
				return;
			}

			// Comprobar si el nombre de usuario ya está en uso
			if (UsuarioConectado(username)) {
				pw.println("error");
				return;
			}

			// Comprobar si un usuario está autorizado:
			boolean autorizado = false;
			for (Usuario usuario : usuariosAutorizados) {
			    if (usuario.getNombre().equals(username) && usuario.getContraseña().equals(password)) {
			        autorizado = true;
			        break;
			    }
			}
			if (autorizado) {
			    pw.println("ok");
			    clientesConectados.add(this);
			    System.out.println(">>> Usuario -" + username + "- conectado. <<<");
			} else {
			    pw.println("error");
			}


			String mensaje;
			while (!socket.isClosed() && (mensaje = bfr.readLine()) != null) {
				switch (mensaje) {
					case "?":
						// Devuelve los nombres de todos los clientes conectados.
						pw.println(ObtenerNombresClientesConectados());
						break;
					case "exit":
						// El cliente abandona el chat y cierra la conexión con el servidor.
						clientesConectados.remove(this);
						socket.close();
						System.out.println(">>> Usuario -" + username + "- desconectado. <<<");
						break;
					default:
						if (mensaje.startsWith("@")) {
							// Enviar el mensaje a un usuario específico.
							String[] partes = mensaje.split(" ", 2);
							String usuario = partes[0].substring(1);  // Elimina el '@' del inicio
							String contenido = partes[1];
							EnviarMensajeAUsuario(usuario, contenido);
						} else {
							// Enviar el mensaje a todos los usuarios conectados.
							EnviarMensajeATodos(mensaje);
						}
						break;
				}
			}

		} catch(IOException e) {
			e.printStackTrace();
			System.err.println("SERVIDOR >>> Error");
		}
	}

	/**
	 * Método para obtener los nombres de todos los clientes conectados
	 * @return Una cadena con todos los nombres de los clientes conectados
	 */
	private String ObtenerNombresClientesConectados() {
		 StringBuilder nombres = new StringBuilder();
	        for (Peticion cliente : clientesConectados) {
	            nombres.append(cliente.username).append(", ");
	        }
	        return nombres.toString();
	    }

	
	/**
	 * Método para obtener el tiempo actual
	 * @return Una cadena representada en formato HH:mm del momento actual en el que se envia el mensaje
	 */
	private String ObtenerTiempo() {
		LocalDateTime ahora = LocalDateTime.now();
		DateTimeFormatter formateador = DateTimeFormatter.ofPattern("HH:mm");
		return ahora.format(formateador);
	}
	
	/**
	 * Método par enviar un mensaje a un usuario en concreto
	 * @param usuario Usuario al que enviar el mensaje
	 * @param mensaje Mensaje a enviar
	 */
	private void EnviarMensajeAUsuario(String usuario, String mensaje) {
		for (Peticion cliente : clientesConectados) {
			if (cliente.username.equals(usuario)) {
				cliente.pw.println("[" + ObtenerTiempo() + "] " + username + ": " + mensaje);
				break;
			}
		}
	}

	/**
	 * Metodo para enviar mensaje a todos los usuarios conectados
	 * @param mensaje El mensaje que se enviará
	 */
	private void EnviarMensajeATodos(String mensaje) {
		for (Peticion cliente : clientesConectados) {
			cliente.pw.println("[" + ObtenerTiempo() + "] " + username + ": " + mensaje);
		}
	}
	
	
	/**
	 * Método para verificar si un usuario está conectado
	 * @param username El nombre del usuario a verificar
	 * @return Verdadero si el usuario está conectado, falso si no lo está
	 */
	private boolean UsuarioConectado(String username) {
	    for (Peticion cliente : clientesConectados) {
	        if (cliente.username.equals(username)) {
	            return true;
	        }
	    }
	    return false;
	}

}


