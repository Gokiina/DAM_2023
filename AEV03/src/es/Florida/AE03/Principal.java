package es.Florida.AE03;

/**
 * La clase Principal contiene el método principal (main) que inicia la aplicación.
 */
public class Principal {

	/**
	 * Punto de entrada principal para la aplicación.
	 * @param args Los argumentos de la línea de comandos (no se utilizan en esta aplicación).
	 */
	public static void main(String[] args) {
		
		// Se crea una instancia de la clase Vista para la interfaz gráfica.
		Vista vista = new Vista();
		
		// Se crea una instancia de la clase Modelo para manejar la lógica de datos y base de datos.
		Modelo modelo = new Modelo();
		
		// Se crea una instancia de la clase Controlador, que conecta la Vista y el Modelo.
		Controlador controlador = new Controlador (vista, modelo);
	}
}

