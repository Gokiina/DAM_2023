package es.Florida.AE03;

import javax.swing.*;
import org.bson.Document;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import java.awt.event.ActionEvent;

/**
 *  La clase controlador gestiona las acciones a realizar entre Vista y modelo
 */
public class Controlador {

	private Vista vista;
	private Modelo modelo;
	public String user;
	public String pass;

	/**
	 * Constructor de la clase controlador
	 * @param vista La vista de la aplicación
	 * @param modelo El modelo de la aplicación
	 */
	public Controlador(Vista vista, Modelo modelo) {
		this.vista = vista;
		this.modelo = modelo;

		modelo.inicioBD();
		initEventHandlers();
	}

	/**
	 * Método que inicia los eventos para los botones
	 */
	public void initEventHandlers() {
		vista.btnRegistrar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String[] credenciales = vista.mostrarDialogoRegistro();
				if (credenciales != null) {
					boolean registrado = modelo.registrarUsuario(credenciales[0], credenciales[1], credenciales[2]);
					if (registrado) {
						System.out.println(">>> Usuario registrado exitosamente.");
					} else {
						System.out.println(
								">>> El registro falló. Por favor, verifica que las contraseñas coincidan y que el nombre de usuario no esté en uso.");
					}
				}
			}
		});
		vista.btnLogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String[] credenciales = vista.mostrarDialogoLogin();
				if (credenciales != null) {
					boolean verificado = modelo.verificarUsuario(credenciales[0], credenciales[1]);
					if (verificado) {
						System.out.println("\n>>> Bienvenido!");

						vista.btnJugar.setVisible(true);
						vista.btnGuardar.setVisible(true);
						vista.btnHallofFame.setVisible(true);
					} else {
						System.out.println(">>> El inicio falló. Por favor, verifica los datos.");
					}
				}
			}
		});
		vista.btnJugar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String seleccion = vista.mostrarSeleccionTab();
				if (seleccion.equals("2x4")) {
					System.out.println("Iniciando tablero 2x4");
					iniciarTablero(2, 4);
				} else if (seleccion.equals("4x4")) {
					System.out.println("Iniciando tablero 4x4");
					iniciarTablero(4, 4);
				}
			}
		});
		vista.btnGuardar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String seleccionTablero = vista.getSeleccionTablero();
				int tiempoTranscurrido = vista.getTiempoTranscurrido();
				long timestamp = vista.getStartTime();

				if (modelo.registrarResultado(seleccionTablero, timestamp, tiempoTranscurrido)) {
					JOptionPane.showMessageDialog(null, "¡Enhorabuena! Has obtenido el menor tiempo para este grado de dificultad.");
				}
				JOptionPane.showMessageDialog(null, "Guardado");

			}
		});

		vista.btnHallofFame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Obtener las clasificaciones de la base de datos
				List<Document> clasificacion8 = modelo.obtenerResultadosPorDificultad(8);
				List<Document> clasificacion16 = modelo.obtenerResultadosPorDificultad(16);

				// Llamar a la función 'mostrarHall' de la vista con las clasificaciones
				vista.mostrarHall(clasificacion8, clasificacion16);
			}
		});

	}

	/**
	 * Inicia un tablero de juego con las filas y columnas seleccionadas
	 * @param filas Número de filas
	 * @param columnas Número de columnas
	 */
	public void iniciarTablero(int filas, int columnas) {
		int totalCartas = filas * columnas;
		List<File> selectedImages = modelo.obtenerImagenes(totalCartas);
		vista.crearTablero(selectedImages, filas, columnas);
	}

}