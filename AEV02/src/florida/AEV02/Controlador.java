package florida.AEV02;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

/**
 * La clase Controlador gestiona la interacción entre la Vista y el Modelo en la aplicación.
 */
public class Controlador {

	private Vista vista;
	private Modelo modelo;
	public String userBooks;
	public String passBooks;
	
	/**
	 * Constructor de la clase Controlador.
	 * @param vista La instancia de la Vista.
	 * @param modelo La instancia del Modelo.
	 */
	public Controlador (Vista vista, Modelo modelo){
		this.vista = vista;
		this.modelo = modelo;
		
		modelo.inicioBD();
		System.out.println("Ingresa tus datos de BOOKS");
		
		initEventHandlers();
	}
	
	/**
	 * Inicializa y gestiona los componentes de la interfaz gráfica.
	 */
	public void initEventHandlers() {
		// Manejador de eventos para el botón de inicio de sesión
		vista.btnIniciarSesion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				userBooks = vista.rUsuario.getText();
				passBooks = vista.rPass.getText();
				String result = modelo.usuariosBooks(userBooks, passBooks);
				if ("Error".equals(result)) {
					JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos", "ERROR!", JOptionPane.INFORMATION_MESSAGE);
				} else {
	                try {
						modelo.obtenerTipoUsuario(userBooks);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		
		// Manejador de eventos para el botón de ejecutar consulta
		vista.btnEjecutarConsulta.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			try {
			if (modelo.usuarioActivo == false) {
				JOptionPane.showMessageDialog(null, "Usuario no activo. Debes iniciar sesión primero.", "HEY!", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
				String consulta = vista.textField.getText();
				if (consulta.trim().toLowerCase().startsWith("insert") || consulta.trim().toLowerCase().startsWith("update") || consulta.trim().toLowerCase().startsWith("update")) {
                    int response = JOptionPane.showConfirmDialog(null, "¿Estás seguro de que quieres ejecutar esta consulta?", "Confirmar", JOptionPane.YES_NO_OPTION);
                    if (response != JOptionPane.YES_OPTION) {
                        return;
                    }
                    
                    String respuesta = modelo.ejecutarConsulta(consulta, userBooks);	
    				if (respuesta.startsWith("Error")) {
                        JOptionPane.showMessageDialog(null, respuesta, "Error", JOptionPane.INFORMATION_MESSAGE);
                        modelo.usuarioActivo = true;
                    }
    				else if (respuesta.equals("Es necesario iniciar sesión")) {
                        JOptionPane.showMessageDialog(null, "Es necesario iniciar sesión", "Error", JOptionPane.INFORMATION_MESSAGE);
                    }
    				else if (respuesta.equals("Es necesario ser administrador")) {
                        JOptionPane.showMessageDialog(null, "Es necesario ser administrador", "Error", JOptionPane.INFORMATION_MESSAGE);
                        modelo.usuarioActivo = true;
                    }else {
                        JOptionPane.showMessageDialog(null, "Consulta realizada", "Resultado", JOptionPane.INFORMATION_MESSAGE);
                        modelo.usuarioActivo = true;
                    }
				} else if (consulta.trim().toLowerCase().startsWith("select")) {
					DefaultTableModel model = modelo.ejecutarSelect(consulta);
	                if (model.getRowCount() == 0) {
	                	JOptionPane.showMessageDialog(null, "Consulta no valida", "Error", JOptionPane.INFORMATION_MESSAGE);
	                	modelo.usuarioActivo = true;
	                } else {
	                	vista.mostrarResultados(model);
	                	JOptionPane.showMessageDialog(null, "Consulta realizada", "Resultado", JOptionPane.INFORMATION_MESSAGE);
	                	modelo.usuarioActivo = true;
	                }
				} 
			} catch (Exception ex) {
	            ex.printStackTrace();
	            JOptionPane.showMessageDialog(null, "Se produjo un error inesperado: " + ex.getMessage(), "Error", JOptionPane.INFORMATION_MESSAGE);
	        }
		}});
		
		// Manejador de eventos para el botón de cerrar sesión
		vista.btnCerrarSesion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (modelo.usuarioActivo == false) {
					JOptionPane.showMessageDialog(null, "Ya estás desconectado", "", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				int response = JOptionPane.showConfirmDialog(null, "¿Estás seguro de que quieres desconectarte?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if (response != JOptionPane.YES_OPTION) {
                    return;
                }
				modelo.usuarioActivo = false;
				System.out.println("Desconectado.");
			}
		});
		
		// Manejador de eventos para la opción de conexión a la base de datos
		vista.checkConexionBD.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	 if (vista.checkConexionBD.isSelected()) {
		    		 int response = JOptionPane.showConfirmDialog(null, "¿Estás seguro de que quieres iniciar la conexión a la base de datos?", "Confirmar", JOptionPane.YES_NO_OPTION);
		    		 if (response != JOptionPane.YES_OPTION) {
		    			 vista.checkConexionBD.setSelected(false);
		    			 return;
		    		 }
		    		 modelo.inicioBD();
		    	 } else {
		    		 int response = JOptionPane.showConfirmDialog(null, "¿Estás seguro de que quieres cerrar la conexión a la base de datos?", "Confirmar", JOptionPane.YES_NO_OPTION);
		    		 if (response != JOptionPane.YES_OPTION) {
	                	vista.checkConexionBD.setSelected(true);
	                    return;
		    		 }
		    		 try {
		    			 modelo.usuarioActivo = false;
		    			 System.out.println("Usuario desconectado.");
		    			 modelo.cerrarBD();
		    			 System.out.println("Conexión con la base de datos cerrada.");
		    		 } catch (SQLException e1) {
		                e1.printStackTrace();
		            }
		    	} 
		    }
		});
	}

}
