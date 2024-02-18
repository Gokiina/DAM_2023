package es.florida;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador para la API de películas
 */
@RestController
public class AE3Controller {
	/**
	 * Método para mostrar la lista de películas.
	 * @param strVariable El párametro de la solicitud.
	 * @return ResponseEntity con la respuesta http.
	 */
	@GetMapping("/APIpelis/t")
	ResponseEntity<Object> mostrarListaPeliculas(@RequestParam(value = "id") String strVariable) {
		String htmlResponse = "";
		// http://localhost:8080/APIpelis/t?id=all
		if (strVariable.equals("all")) {
			String obj = obtenerTodasPeliculas();
			htmlResponse = obj;
			return ResponseEntity.ok(htmlResponse);
		}
		// http://localhost:8080/APIpelis/t?id={id}
		else if (validNum(strVariable)) {
			String obj = obtenerResenyas(String.valueOf(strVariable));
			htmlResponse = obj;
			return ResponseEntity.ok(htmlResponse);
		} else {
			htmlResponse = "Parámetro no reconocido";
			System.out.println("código 404 (not found)");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	/**
	 * Método para obtener todas las películas.
	 * @return String con la lista de películas en formato JSON.
	 */
	private String obtenerTodasPeliculas() {
		File directorio = new File("pelis");
		String[] listaFicheros = directorio.list(new FiltroExtension(".txt"));
		Arrays.sort(listaFicheros);
		StringBuilder resultado = new StringBuilder();

		if (directorio.list().length == 0) {
			resultado.append("{“titulos”: []}");
			return resultado.toString();
		}

		for (String pelicula : listaFicheros) {
			String id = pelicula.replace(".txt", "");
			String titulo = tituloPelicula(pelicula);
			resultado.append("{\"id\": \"").append(id).append("\", \"titulo\": \"").append(titulo).append("\"},");
		}

		if (resultado.length() > 0) {
			resultado.setLength(resultado.length() - 1);
		}
		resultado.insert(0, "{\"titulos\": [");
		resultado.append("]}");

		return resultado.toString();
	}

	/**
	 * Método para obtener el título de una película según el ID.
	 * @param idPelicula El ID de la película.
	 * @return String con el título de la película.
	 */
	private String tituloPelicula(String idPelicula) {
		String titulo = "[]";
		try {
			FileReader fr = new FileReader("pelis/" + idPelicula);
			BufferedReader br = new BufferedReader(fr);
			String linea = br.readLine();
			if (linea != null && linea.startsWith("Titulo: ")) {
				titulo = linea.replace("Titulo: ", "");
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return titulo;
	}

	/**
	 * Método para validar si una cadena es un número.
	 * @param strNum La cadena a verificar.
	 * @return Boleean que indica si la cadena es un número
	 */
	public static boolean validNum(String strNum) {
		if (strNum == null) {
			return false;
		}
		try {
			Integer.parseInt(strNum);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	/**
	 * Método para obtener las reseñas de una película.
	 * @param id El ID de la película.
	 * @return String con las reseñas de la película en formato JSON.
	 */
	private String obtenerResenyas(String id) {
		File directorio = new File("pelis");
		File archivo = new File(directorio, id + ".txt");
		StringBuilder resultado = new StringBuilder();

		if (archivo.exists()) {
			String idPelicula = id;
			String titulo = tituloPelicula(id + ".txt");
			List<String> resenyas = new ArrayList<>();

			try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
				String linea;
				while ((linea = reader.readLine()) != null) {
					if (linea.startsWith("Usuario")) {
						resenyas.add("\"" + linea + "\"");
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			resultado.append("{\"id\": \"").append(idPelicula).append("\", \"titulo\": \"").append(titulo)
					.append("\", \"resenyas\": ").append(resenyas).append("}");

			return resultado.toString();
		} else {
			return "código 404 (not found)";
		}
	}

	// http://localhost:8080/APIpelis/nuevaResenya
	/**
	 * Método para añadir una nueva reseña a una película.
	 * @param cuerpoPeticion El cuerpo con la información de la reseña.
	 * @return ResponseEntity con la respuesta http.
	 */
	@PostMapping("/APIpelis/nuevaResenya")
	public ResponseEntity<Object> postResenya(@RequestBody String cuerpoPeticion) {
		JSONObject jsonObject = new JSONObject(cuerpoPeticion);

		String nombreUsuario = (String) jsonObject.get("usuario");
		String idPelicula = (String) jsonObject.get("id");
		String textoResenya = (String) jsonObject.get("resenya");
		String comprobUser = (String) jsonObject.get("usuario");

		try {
			BufferedReader reader = new BufferedReader(new FileReader("autorizados.txt"));
			List<String> autorizados = new ArrayList<>();
			String line;
			while ((line = reader.readLine()) != null) {
				autorizados.add(line);
			}
			reader.close();
			if (!autorizados.contains(comprobUser)) {
				System.out.println("código 401 (cliente no autorizado)");
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			FileWriter fw = new FileWriter("pelis/" + idPelicula + ".txt", true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write("Usuario " + nombreUsuario + ": " + textoResenya + "\n");
			System.out.println(">>> Nueva reseña de " + nombreUsuario + " anyadida con exito\n");
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("respuesta al cliente sin contenido");
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	// http://localhost:8080/APIpelis/nuevaPeli
	/**
	 * Método para añadir una nueva película.
	 * @param cuerpoPeticion El cuerpo de la petición con la información de la película.
	 * @return ResponseEntity con la respuesta http.
	 */
	@PostMapping("/APIpelis/nuevaPeli")
	ResponseEntity<Object> postPeli(@RequestBody String cuerpoPeticion) {
		JSONObject jsonObject = new JSONObject(cuerpoPeticion);

		String tituloPelicula = (String) jsonObject.get("titulo");
		String comprobUser = (String) jsonObject.get("usuario");

		try {
			BufferedReader reader = new BufferedReader(new FileReader("autorizados.txt"));
			List<String> autorizados = new ArrayList<>();
			String line;
			while ((line = reader.readLine()) != null) {
				autorizados.add(line);
			}
			reader.close();
			if (!autorizados.contains(comprobUser)) {
				System.out.println("código 401 (cliente no autorizado)");
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		File dir = new File("pelis/");
		int nextId = dir.list().length;

		try {
			FileWriter fw = new FileWriter("pelis/" + nextId + ".txt");
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write("Titulo: " + tituloPelicula + "\n");
			System.out.println(">>> Pelicula " + tituloPelicula + " anyadida con exito\n");
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("respuesta al cliente sin contenido");
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	// http://localhost:8080/APIpelis/nuevoUsuario
	/**
	 * Método para añadir un nuevo usuario.
	 * @param nombreUser El nombre del nuevo usuario.
	 * @return RespondeEntity con la respuesta http.
	 */
	@PostMapping("/APIpelis/nuevoUsuario")
	ResponseEntity<Object> postNewUser(@RequestBody String nombreUser) {
		JSONObject jsonObject = new JSONObject(nombreUser);

		String nombreUsuario = (String) jsonObject.get("usuario");

		try {
			FileWriter fw = new FileWriter("autorizados.txt", true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(nombreUsuario + "\n");
			System.out.println(">>> Nuevo usuario: " + nombreUsuario + "\n");
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("respuesta al cliente sin contenido");
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
