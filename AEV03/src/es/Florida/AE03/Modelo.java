package es.Florida.AE03;

import java.io.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.*;
import org.bson.Document;
import org.json.*;
import com.mongodb.MongoClient;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import org.apache.commons.codec.binary.Base64;
import static com.mongodb.client.model.Filters.*;

/**
 * La clase modelo se encarga de gestionar la aplicación
 */
public class Modelo {
	static MongoCollection<Document> coleccionImagenes;
	static MongoCollection<Document> coleccionUsuarios;
	static MongoCollection<Document> coleccionRecords;
	public String usuarioConectado;
	List<JSONObject> resultados = new ArrayList<>();

	/**
	 * Inicia la base de datos y lee la configuración de un archivo JSON
	 */
	public void inicioBD() {
		// Eliminar cabecero rojo
		Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
		mongoLogger.setLevel(Level.SEVERE);

		// Leer json conexion
		String path = "memory.conexion.json";
		try {
			String content = new String(Files.readAllBytes(Paths.get(path)));
			JSONObject jsonObject = new JSONObject(content);

			String ip = jsonObject.getString("ip");
			int puerto = Integer.parseInt(jsonObject.getString("puerto"));
			String bbdd = jsonObject.getString("bbdd");
			JSONArray colecciones = jsonObject.getJSONArray("colecciones");

			// acceso a la bbdd
			accederBD(ip, puerto, bbdd, colecciones);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Accedemos a la bbdd con los datos del JSON
	 * @param ip La dirección IP del servidor
	 * @param puerto El puerto del servidor
	 * @param bbdd Nombre de la base de datos
	 * @param nombresColecciones Array con los nombres de las colecciones
	 */
	public void accederBD(String ip, int puerto, String bbdd, JSONArray nombresColecciones) {
		// Conexión
		MongoClient mongoClient = new MongoClient(ip, puerto);
		MongoDatabase database = mongoClient.getDatabase(bbdd);
		for (int i = 0; i < nombresColecciones.length(); i++) {
			String nombreColeccion = nombresColecciones.getString(i);
			// Verificar si la colección ya existe
			boolean collectionExists = database.listCollectionNames().into(new ArrayList<String>())
					.contains(nombreColeccion);
			if (!collectionExists) {
				// Si la colección no existe, la creamos
				database.createCollection(nombreColeccion);
			}
			// Asignar la colección a la variable correspondiente
			if (nombreColeccion.equals("imagenes")) {
				coleccionImagenes = database.getCollection(nombreColeccion);
			} else if (nombreColeccion.equals("usuarios")) {
				coleccionUsuarios = database.getCollection(nombreColeccion);
			} else if (nombreColeccion.equals("records")) {
				coleccionRecords = database.getCollection(nombreColeccion);
			}
		}
		System.out.println("<Conexión a la base de datos establecida>");
		System.out.println("\n >>> Inicie sesión");
	}

	/**
	 * Método que registra un nuevo usuario a la base de datos
	 * @param user Nombre del usuario
	 * @param password Contraseña del usuario
	 * @param confirmPassword Confirmación de la contraseña
	 * @return True si el registro ha funcionado, false si da error
	 */
	public boolean registrarUsuario(String user, String password, String confirmPassword) {
		// Comprobación de coincidencia entre contraseñas
		if (!password.equals(confirmPassword)) {
			System.out.println("Las contraseñas no coinciden.");
			return false;
		}

		// Comprobación de si el usuario ya existe
		Document existingUser = coleccionUsuarios.find(Filters.eq("user", user)).first();
		if (existingUser != null) {
			System.out.println("El usuario ya existe");
			return false;
		}

		// Cifrar la contraseña
		String hashedPass = generarSHA256(password);

		// Crear nuevo usuario
		Document newUser = new Document();
		newUser.append("user", user);
		newUser.append("pass", hashedPass);
		coleccionUsuarios.insertOne(newUser);

		return true;
	}

	/**
	 *  Cifrado de contraseña
	 * @param pass Contraseña a cifrar
	 * @return Contraseña cifrada
	 */
	public String generarSHA256(String pass) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] hash = md.digest(pass.getBytes(StandardCharsets.UTF_8));
			BigInteger num = new BigInteger(1, hash);
			StringBuilder hexString = new StringBuilder(num.toString(16));
			while (hexString.length() < 32) {
				hexString.insert(0, '0');
			}
			return hexString.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Método que comprueba si el usuario introducido es correcto
	 * @param user Nombre de usuario
	 * @param password Contraseña usuario
	 * @return True si la verificación es correcta, false si da error
	 */
	public boolean verificarUsuario(String user, String password) {
		// Buscar el usuario en la base de datos
		Document existingUser = coleccionUsuarios.find(Filters.eq("user", user)).first();
		if (existingUser == null) {
			System.out.println("El usuario no existe");
			return false;
		}

		// Comprobar la contraseña
		String hashedPass = generarSHA256(password);
		if (!hashedPass.equals(existingUser.getString("pass"))) {
			System.out.println("La contraseña es incorrecta");
			return false;
		}

		usuarioConectado = user;
		System.out.println("Usuario autenticado exitosamente");
		return true;
	}

	/**
	 *  Método que obtiene las imagenes de la base de datos y las guarda en un directorio
	 *  Pasandolás de base64 a jpg
	 */
	public void obtenerImagenDeJSON() {
		MongoCursor<Document> cursor = coleccionImagenes.find().iterator();
		File dir = new File("img");
		
		// Crea el directorio /img si no existe
		if (!dir.exists())
			dir.mkdirs();

		int count = 0;
		// Recorremos todos los elementos de la coleccionImagenes
		while (cursor.hasNext()) {
			Document docActual = cursor.next();
			String string64 = docActual.getString("base64"); // obtenemos la imagen en base64
			if (string64 != null) {
				byte[] btDataFile = new Base64().decode(string64); // descodificamos la imagen
				try {
					ByteArrayInputStream bais = new ByteArrayInputStream(btDataFile); // leemos la img descodificada
					BufferedImage imagen = ImageIO.read(bais);
					File outputfile = new File(dir, "imagen" + count + ".jpg"); // guardamos la imagen con su nuevo nombre
					ImageIO.write(imagen, "jpg", outputfile);
					count++;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Método que obtiene las imagenes en formato jpg y las prepara para el memory de dos en dos
	 * @param totalCartas El numero de cartas necesitadas
	 * @return La lista de las imagenes que formarán la partida
	 */
	public List<File> obtenerImagenes(int totalCartas) {
		obtenerImagenDeJSON();

		// Obtenemos la lista de todas las imágenes
		File dir = new File("img");
		File[] files = dir.listFiles((d, name) -> name.endsWith(".jpg"));

		// Comprueba si hay imágenes en el directorio
		if (files != null && files.length > 0) {
			// Selecciona aleatoriamente la mitad de las cartas
			List<File> selectedImages = new ArrayList<>();
			Random rand = new Random();
			Set<Integer> selectedIndices = new HashSet<>();
			while (selectedIndices.size() < totalCartas / 2) {
				int index = rand.nextInt(files.length);
				if (!selectedIndices.contains(index)) {
					File randomImage = files[index];
					selectedImages.add(randomImage);
					selectedIndices.add(index);
				}
			}

			// Duplica las cartas seleccionadas para tener pares
			selectedImages.addAll(new ArrayList<>(selectedImages));

			// Mezcla las cartas
			Collections.shuffle(selectedImages);

			return selectedImages;
		} else {
			// No hay imágenes en el directorio
			System.out.println("No hay imágenes en el directorio 'img'.");
			return new ArrayList<>();
		}
	}

	/**
	 * Registra el resultado del juego
	 * @param dificultad Grado de dificultad 2x4 o 4x4
	 * @param timestamp Timestamp del inicio del juego
	 * @param tiempoTranscurrido Tiempo de la partida
	 * @return
	 */
	public boolean registrarResultado(String dificultad, long timestamp, int tiempoTranscurrido) {
		String user = usuarioConectado;

		// Convertir la dificultad a un número entero
		String[] numeros = dificultad.split("x");
		int dificultadInt = Integer.parseInt(numeros[0]) * Integer.parseInt(numeros[1]);

		// Convertir el timestamp a una cadena en el formato deseado
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
		String timestampStr = sdf.format(new Date(timestamp));

		// Crear un nuevo documento para almacenar el resultado
		Document resultado = new Document();
		resultado.put("usuario", user);
		resultado.put("dificultad", dificultadInt); 
		resultado.put("timestamp", timestampStr);
		resultado.put("duracion", tiempoTranscurrido);

		coleccionRecords.insertOne(resultado);

		// Verificar si el usuario ha obtenido el menor tiempo para el grado de dificultad
		int menorTiempo = Integer.MAX_VALUE;

		// Obtener todos los resultados de la base de datos
		FindIterable<Document> resultados = coleccionRecords.find();

		for (Document res : resultados) {
			if (res.getInteger("dificultad") == dificultadInt) { 
				int duracion = res.getInteger("duracion");
				if (duracion < menorTiempo) {
					menorTiempo = duracion;
				}
			}
		}
		return tiempoTranscurrido == menorTiempo;
	}

	/**
	 * Muestra las clasificaciones de los juegos
	 */
	public void mostrarClasificaciones() {
		// Crear listas para cada grado de dificultad
		List<Document> clasificacion8 = new ArrayList<>();
		List<Document> clasificacion16 = new ArrayList<>();

		// Obtener todos los registros de la base de datos y ordenarlos por tiempo
		FindIterable<Document> resultados = coleccionRecords.find().sort(Sorts.ascending("duracion"));

		for (Document res : resultados) {
			int dificultad = res.getInteger("dificultad");
			if (dificultad == 8) {
				clasificacion8.add(res);
			} else if (dificultad == 16) {
				clasificacion16.add(res);
			}
		}
	}

	/**
	 * Obtiene los resultados por nivel de dificultad
	 * @param dificultad Dificultad del juego 2x4 o 4x4
	 * @return Lista con los resultados de las partidas
	 */
	public List<Document> obtenerResultadosPorDificultad(int dificultad) {
		// Crear una lista para almacenar los resultados
		List<Document> clasificacion = new ArrayList<>();

		// Obtener todos los registros de la base de datos y ordenarlos por tiempo
		FindIterable<Document> resultados = coleccionRecords.find(eq("dificultad", dificultad)).sort(Sorts.ascending("duracion"));

		// Agregar los resultados a la lista
		for (Document res : resultados) {
			clasificacion.add(res);
		}
		return clasificacion;
	}
}