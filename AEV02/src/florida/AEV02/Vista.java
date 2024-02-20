package florida.AEV02;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 * La clase Vista representa la interfaz gráfica de usuario de la aplicación.
 */
public class Vista extends JFrame {

	public JPanel contentPane;
	public JTextField rUsuario;
	public JTextField rPass;
	public JTextField textField;
	public JButton btnIniciarSesion;
	public JLabel tUsuario;
	public JLabel tPass;
	public JLabel tBD;
	public JButton btnEjecutarConsulta;
	public JButton btnCerrarSesion;
	public JScrollPane scrollPane;
	public JTable table;
	public DefaultTableModel tableModel;
	public JScrollPane scrollPane_2;
	public JCheckBox checkConexionBD;

	/**
	 * Constructor de la clase Vista que inicializa y configura la interfaz gráfica.
	 */
	public Vista() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 811, 431);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		tUsuario = new JLabel("Usuario:");
		tUsuario.setBounds(207, 11, 61, 16);
		contentPane.add(tUsuario);
		
		rUsuario = new JTextField();
		rUsuario.setBounds(268, 6, 130, 26);
		contentPane.add(rUsuario);
		rUsuario.setColumns(10);
		
		tPass = new JLabel("Contraseña:");
		tPass.setBounds(410, 11, 86, 16);
		contentPane.add(tPass);
		
		rPass = new JTextField();
		rPass.setColumns(10);
		rPass.setBounds(492, 6, 130, 26);
		contentPane.add(rPass);
		
		btnIniciarSesion = new JButton("Iniciar Sesion (BD)");
		btnIniciarSesion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnIniciarSesion.setBounds(624, 6, 145, 29);
		contentPane.add(btnIniciarSesion);
		
		tBD = new JLabel("Ejecuta las consultas SQL:");
		tBD.setBounds(29, 66, 172, 16);
		contentPane.add(tBD);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(29, 94, 758, 82);
		contentPane.add(scrollPane);
		
		textField = new JTextField();
		scrollPane.setViewportView(textField);
		textField.setColumns(10);
		
		btnEjecutarConsulta = new JButton("Ejecutar");
		btnEjecutarConsulta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnEjecutarConsulta.setBounds(366, 179, 117, 29);
		contentPane.add(btnEjecutarConsulta);
		
		btnCerrarSesion = new JButton("Cerrar Sesión (BD)");
		btnCerrarSesion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnCerrarSesion.setBounds(625, 365, 145, 29);
		contentPane.add(btnCerrarSesion);
		
		scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(29, 222, 758, 134);
		contentPane.add(scrollPane_2);
		
		tableModel = new DefaultTableModel();
		table = new JTable(tableModel);
		scrollPane_2.setViewportView(table);
		
		checkConexionBD = new JCheckBox("Connexión BD");
		checkConexionBD.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		checkConexionBD.setSelected(true);
		checkConexionBD.setBounds(29, 366, 128, 23);
		contentPane.add(checkConexionBD);
		
		setVisible(true);
	}
	
	/**
	 * Muestra los resultados de una consulta en la tabla de la interfaz gráfica.
	 * El modelo de tabla que contiene los resultados de la consulta.
	 * @param model
	 */
	public void mostrarResultados(DefaultTableModel model) {
		this.tableModel = model;
		table.setModel(tableModel);
	}
}
