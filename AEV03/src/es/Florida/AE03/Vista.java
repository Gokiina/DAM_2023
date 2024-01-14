package es.Florida.AE03;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import org.bson.Document;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import java.awt.*;
import javax.swing.GroupLayout.Alignment;

/**
 *  Se encarga de la interfaz gráfica del juego memory
 */
public class Vista extends JFrame {

	public JPanel contentPane;
	public DefaultTableModel tableModel;

	private JTextField fieldUsuario = new JTextField(10);
	private JPasswordField fieldContraseña = new JPasswordField(10);
	private JPasswordField fieldConfirmarContraseña = new JPasswordField(10);

	public JButton btnRegistrar;
	public JButton btnLogin;
	public JButton btnJugar;
	public JButton btnGuardar;
	public JButton btnHallofFame;
	public JButton btn2x4;
	public JButton btn4x4;
	
	public JPanel panelTablero;
	private JPanel panelBotones;
	public List<JButton> listaBotones = new ArrayList<>();
	long startTime = System.currentTimeMillis();
	private int tiempoTranscurrido;
	private String seleccionTablero;
	
	JButton botonClasificacion = new JButton("Mostrar clasificación");
	JTextArea areaClasificacion = new JTextArea();

	/**
	 * Constructor de la clase vista. Inicia la interfaz
	 */
	public Vista() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 811, 539);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new CardLayout(0, 0));

		JPanel panel = new JPanel();
		contentPane.add(panel, "name_64971382592916");

		btnRegistrar = new JButton("Registrarse");
		btnRegistrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});

		btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});

		btnJugar = new JButton("Jugar");
		btnJugar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});

		btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});

		btnHallofFame = new JButton("Hall of fame");
		btnHallofFame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});

		btnJugar.setVisible(false);
		btnGuardar.setVisible(false);
		btnHallofFame.setVisible(false);

		panelBotones = new JPanel();

		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
						.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createSequentialGroup().addGap(13).addComponent(btnRegistrar))
								.addGroup(gl_panel.createSequentialGroup().addGap(13).addComponent(btnLogin,
										GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_panel.createSequentialGroup().addGap(10).addComponent(btnJugar,
										GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_panel.createSequentialGroup().addGap(10).addComponent(btnGuardar,
										GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_panel.createSequentialGroup().addGap(13).addComponent(btnHallofFame,
										GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE)))
						.addGap(18).addComponent(panelBotones, GroupLayout.DEFAULT_SIZE, 647, Short.MAX_VALUE)
						.addContainerGap()));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup().addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup().addGap(5).addComponent(btnRegistrar).addGap(5)
								.addComponent(btnLogin).addGap(72).addComponent(btnJugar).addGap(73)
								.addComponent(btnGuardar).addGap(85).addComponent(btnHallofFame))
						.addComponent(panelBotones, GroupLayout.PREFERRED_SIZE, 485, GroupLayout.PREFERRED_SIZE))
						.addContainerGap(16, Short.MAX_VALUE)));
		panel.setLayout(gl_panel);

		tableModel = new DefaultTableModel();

		setVisible(true);

	}

	/**
	 * Muestra una pantalla emergente para que el usuario añada los datos de registro
	 * @return Un array con el nombre de usuario, la contraseña y la confirmación de la contraseña
	 */
	public String[] mostrarDialogoRegistro() {
		JPanel panel = new JPanel();
		panel.add(new JLabel("Usuario:"));
		panel.add(fieldUsuario);
		panel.add(Box.createHorizontalStrut(15));
		panel.add(new JLabel("Contraseña:"));
		panel.add(fieldContraseña);
		panel.add(Box.createHorizontalStrut(15));
		panel.add(new JLabel("Confirmar Contraseña:"));
		panel.add(fieldConfirmarContraseña);

		int result = JOptionPane.showConfirmDialog(null, panel, "Por favor, introduce el usuario y la contraseña",
				JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {
			return new String[] { fieldUsuario.getText(), new String(fieldContraseña.getPassword()),
					new String(fieldConfirmarContraseña.getPassword()) };
		} else {
			return null;
		}
	}

	/**
	 * Muestra una pantalla emergente para que el usuario inicie sesión
	 * @return Un array con el nombre de usuario y la contraseña
	 */
	public String[] mostrarDialogoLogin() {
		JPanel panel = new JPanel();
		panel.add(new JLabel("Usuario:"));
		panel.add(fieldUsuario);
		panel.add(Box.createHorizontalStrut(15));
		panel.add(new JLabel("Contraseña:"));
		panel.add(fieldContraseña);

		int result = JOptionPane.showConfirmDialog(null, panel, "Por favor, introduce el usuario y la contraseña",
				JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {
			return new String[] { fieldUsuario.getText(), new String(fieldContraseña.getPassword()),
					new String(fieldConfirmarContraseña.getPassword()) };
		} else {
			return null;
		}
	}

	/**
	 * Muestra una pantalla emergente para poder seleciconar el tamaño del tablero de juego
	 * @return
	 */
	public String mostrarSeleccionTab() {
		JPanel panel = new JPanel();
		btn2x4 = new JButton("2x4");
		btn4x4 = new JButton("4x4");

		String[] options = new String[] { "2x4", "4x4" };
		int response = JOptionPane.showOptionDialog(null, panel, "Selecciona el tablero:", JOptionPane.DEFAULT_OPTION,
				JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

		seleccionTablero = options[response];
		return seleccionTablero;
	}

	/**
	 * Método que crea el tablero de juego
	 * @param selectedImages Lista de archivos imagen para el juego
	 * @param filas Número de filas del tablero
	 * @param columnas Número de columnas del tablero
	 */
	public void crearTablero(List<File> selectedImages, int filas, int columnas) {
		int respuesta = JOptionPane.showConfirmDialog(null, "¿ESTAS LISTO?", "Confirmación", JOptionPane.YES_NO_OPTION);
		if (respuesta == JOptionPane.YES_OPTION) {
			startTime = System.currentTimeMillis(); // Almacena el tiempo de inicio

			// Crea los botones de imagen
			listaBotones.clear();
			JButton[] botonesRevelados = new JButton[2];
			String[] rutasImagenesReveladas = new String[2];
			for (File image : selectedImages) {
				ImageIcon iconBocaArriba = new ImageIcon(image.getPath());
				JButton boton = new JButton(); // Botón sin imagen
				boton.setPreferredSize(new Dimension(110, 110));
				boton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						if (boton.getIcon() == null) { // Solo permite pulsar el botón si no tiene imagen
							boton.setIcon(iconBocaArriba); // Muestra la imagen del botón cuando se hace clic en él
							if (botonesRevelados[0] == null) {
								botonesRevelados[0] = boton;
								rutasImagenesReveladas[0] = image.getPath();
							} else {
								botonesRevelados[1] = boton;
								rutasImagenesReveladas[1] = image.getPath();
								Timer timer = new Timer(1000, new ActionListener() {
									@Override
									public void actionPerformed(ActionEvent e) {
										if (!rutasImagenesReveladas[0].equals(rutasImagenesReveladas[1])) {
											// Si las imágenes no coinciden, oculta las cartas de nuevo
											botonesRevelados[0].setIcon(null);
											botonesRevelados[1].setIcon(null);
										} else {
											// Si las imágenes coinciden, desactiva los botones
											botonesRevelados[0].setEnabled(false);
											botonesRevelados[1].setEnabled(false);
											// Verifica si todas las imágenes se han emparejado correctamente
											boolean todasEmparejadas = true;
											for (JButton boton : listaBotones) {
												if (boton.getIcon() == null) {
													todasEmparejadas = false;
													break;
												}
											}
											if (todasEmparejadas) {
												long endTime = System.currentTimeMillis();
												tiempoTranscurrido = (int) Math.round((endTime - startTime) / 1000.0);
												JOptionPane.showMessageDialog(null,
														"COMPLETADO! Tiempo: " + tiempoTranscurrido + " segundos");

											}
										}
										botonesRevelados[0] = null;
										botonesRevelados[1] = null;
										rutasImagenesReveladas[0] = null;
										rutasImagenesReveladas[1] = null;
									}
								});
								timer.setRepeats(false);
								timer.start();
							}
						}
					}
				});
				listaBotones.add(boton);
			}

			// Crea un nuevo panel para el tablero
			JPanel tablero = new JPanel();
			tablero.setLayout(new GridLayout(filas, columnas));

			// Agrega los botones al tablero
			for (JButton boton : listaBotones) {
				tablero.add(boton);
			}

			// Agrega el tablero al panel existente
			panelBotones.removeAll();
			panelBotones.add(tablero);
			panelBotones.revalidate();
			panelBotones.repaint(); 
		}

	}

	/**
	 * Muestra una pantalla emergente con el Hall de las partidas
	 * @param clasificacion8 Lista con las partidas en dificultad 8
	 * @param clasificacion16 Lista con las partidas en dificultad 16
	 */
	public void mostrarHall(List<Document> clasificacion8, List<Document> clasificacion16) {
		JFrame frame = new JFrame("Hall of Fame");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// Crear un modelo de tabla para cada clasificación
		DefaultTableModel model8 = new DefaultTableModel();
		model8.addColumn("Usuario");
		model8.addColumn("Timestamp");
		model8.addColumn("Duración");

		DefaultTableModel model16 = new DefaultTableModel();
		model16.addColumn("Usuario");
		model16.addColumn("Timestamp");
		model16.addColumn("Duración");

		// Llenar los modelos de tabla con los datos de las clasificaciones
		for (Document doc : clasificacion8) {
			model8.addRow(new Object[] { doc.getString("usuario"), doc.getString("timestamp"), doc.getInteger("duracion") });
		}
		for (Document doc : clasificacion16) {
			model16.addRow(new Object[] { doc.getString("usuario"), doc.getString("timestamp"), doc.getInteger("duracion") });
		}

		// Crear las tablas y añadir los modelos de tabla a ellas
		JTable table8 = new JTable(model8);
		JTable table16 = new JTable(model16);

		// Crear un panel con una disposición de cuadrícula para contener las tablas
		JPanel panel = new JPanel(new GridLayout(1, 2));
		panel.add(new JScrollPane(table8));
		panel.add(new JScrollPane(table16));

		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
	}

	/**
	 * Devuelve el tiempo de la partida
	 * @return Tiempo de la partida
	 */
	public int getTiempoTranscurrido() {
		return tiempoTranscurrido;
	}

	/**
	 * Devuelve el tiempo de inicio del juego
	 * @return Tiempo de inicio del jego
	 */
	public long getStartTime() {
		return startTime;
	}

	/**
	 * Devuelve el tamaño del tablero
	 * @return Seleccion del tablero
	 */
	public String getSeleccionTablero() {
		return seleccionTablero;
	}
}
