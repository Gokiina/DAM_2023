package florida.AEV02;
import org.w3c.dom.*;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.*;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import org.w3c.dom.Document;

/**
 * La clase Modelo gestiona la lógica de la aplicación y la interacción con la base de datos.
 */
public class Modelo {

	public String url;
    public String user;
    public String pass;
    public Connection con;
    public boolean usuarioActivo = false;
    
	/**
	 * Inicia la conexión a la base de datos utilizando la información cargada desde el archivo XML client.
     */
	public void inicioBD() {
		try {
			DocumentBuilderFactory dFact = DocumentBuilderFactory.newInstance();
			DocumentBuilder build = dFact.newDocumentBuilder();
			Document document = build.parse(new File("client.xml"));
			
			NodeList nodeList = document.getElementsByTagName("connection");
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item (i);
				if (node.getNodeType () == Node.ELEMENT_NODE ) {
					Element eElement = (Element) node;
					url = eElement.getElementsByTagName("url").item(0).getTextContent();
					user = eElement.getElementsByTagName("user").item(0).getTextContent();
					pass = eElement.getElementsByTagName("pass").item(0).getTextContent();
				}
			} accederBD(url, user, pass);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	/**
	 * Establece la conexión a la base de datos utilizando la información proporcionada.
	 * @param url La URL de la base de datos
	 * @param user El nombre de usuario de la conexión
	 * @param pass La contraseña para la conexión
	 */
	public void accederBD(String url, String user, String pass){
        System.out.println("Conexión a la base de datos establecida.");
		System.out.println("Bienvenido!");
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, user, pass);
        } catch (Exception e) {
            System.out.println(e);
        }
	}
	

	
	/**
	 * Valida las credenciales del usuario en la base de datos y establece la conexión.
	 * @param userBooks El nombre del usuario de la BD Books
	 * @param passBooks La contraseña del usuario de la BD Books
	 * @return El nombre de usuario si la validación es correcta o un mensaje de error.
	 */
	public String usuariosBooks(String userBooks, String passBooks) {
        try {
        	// contraseña a MD5
        	String passBooksMD5 = passMD5(passBooks);
        	
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM users WHERE user = ? AND pass = ?;");
            stmt.setString(1, userBooks);
            stmt.setString(2, passBooksMD5);
            
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                usuarioActivo = true;
                return userBooks;
            } else {
                usuarioActivo = false;
                return "Error";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            usuarioActivo = false;
            return "Error al validar el usuario: " + e.getMessage();
        }
    }
	
	/**
	 * Obtiene el tipo de usuario y realiza la conexión correspondiente (admin o cliente).
	 * @param user El nombre de usuario para determinar de que tipo es
	 * @throws SQLException Si ocurre un error al obtener el tipo de usuario.
	 */
	public void obtenerTipoUsuario(String user) throws SQLException{
		if ("administrador1".equals(user)) {
			cerrarBD();
	        inicioBDAdmin();
	        System.out.println("Conectado como 'admin'.");
		} else if ("client1".equals(user)) {
			System.out.println("Conectado como 'client'.");
		} else {
			System.out.println("Tipo de usuario no reconocido.");
		}
	}
	
	/**
	 * Inicia la conexión como administrador cargando la información desde un archivo XML admin.
	 */
	public void inicioBDAdmin() {
		try {
			DocumentBuilderFactory dFact = DocumentBuilderFactory.newInstance();
			DocumentBuilder build = dFact.newDocumentBuilder();
			Document document = build.parse(new File("admin.xml"));
				
			NodeList nodeList = document.getElementsByTagName("connection");
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item (i);
				if (node.getNodeType () == Node.ELEMENT_NODE ) {
					Element eElement = (Element) node;
					url = eElement.getElementsByTagName("url").item(0).getTextContent();
					user = eElement.getElementsByTagName("user").item(0).getTextContent();
					pass = eElement.getElementsByTagName("pass").item(0).getTextContent();
				}
			} 
			accederBD(url, user, pass);
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	
	/**
	 * Cierra la conexión a la base de datos
	 * @throws SQLException Si ocurre un error al cerrar la conexión
	 */
	public void cerrarBD() throws SQLException{
		con.close();
    }
	
	/**
	 * Ejecuta una consulta SQL (INSERT, UPDATE, DELETE) y devuelve un mensaje de resultado.
	 * @param consulta La consulta SQL a ejecutar.
	 * @param userBook El tipo de usuario que realiza la consulta.
	 * @return Un mensaje indicando el resultado de la operación.
	 */
	public String ejecutarConsulta(String consulta, String userBook) {
		if (usuarioActivo = false) {
			return "Es necesario iniciar sesión";
		}
		if (userBook.equals("client1")) {
			return "Es necesario ser administrador";
		}
		try {
			Statement stmt = con.createStatement();
			stmt.execute(consulta);
			return "La operación se ha realizado correctamente";
		} catch (SQLException e) {
            return "Error al ejecutar la consulta: " + e.getMessage();
		}          
	}
		
	/**
	 * Ejecuta una consulta SELECT y devuelve los resultados en un modelo de tabla.
	 * @param consulta La consulta SELECT a ejecutar.
	 * @return Un modelo de tabla con los resultados de la consulta.
	 */
	public DefaultTableModel ejecutarSelect(String consulta) {
		try {
            Statement stmt = con.createStatement();
            boolean isResultSet = stmt.execute(consulta);

            if (isResultSet) {
                ResultSet rs = stmt.getResultSet();
                ResultSetMetaData rsmd = rs.getMetaData();
                int columnsNumber = rsmd.getColumnCount();
                
                DefaultTableModel model = new DefaultTableModel();
                for (int i = 1; i <= columnsNumber; i++) {
                    model.addColumn(rsmd.getColumnName(i));
                }
                while (rs.next()) {
                    Object[] row = new Object[columnsNumber];
                    for (int i = 1; i <= columnsNumber; i++) {
                        row[i - 1] = rs.getObject(i);
                    }
                    model.addRow(row);
                } 
                return model;
            }
		} catch (SQLException e) {
			return new DefaultTableModel();
		} 
		return null;        
    }
	
	/**
	 * Convierte una contraseña a su representación MD5.
	 * @param passBooks La contraseña a convertir.
	 * @return La representación MD5 de la contraseña.
	 */
	public String passMD5(String passBooks) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(passBooks.getBytes());

            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
