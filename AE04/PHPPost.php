<?php
	if(isset($_POST["name"])){
		$name = $_POST["name"];
		$description = $_POST["description"];
		$comics = $_POST["comics"];
		$servidor = "localhost";
		$usuario = "root";
		$password = "";
		$dbname = "AE04";

		$conexion = mysqli_connect($servidor, $usuario, $password, $dbname);
		
		if (!$conexion) {
			echo "Error en la conexion a MySQL: ".mysqli_connect_error();
			exit();
		}
		
		$sql = "INSERT INTO Marvel (name, description, comics) VALUES ('$name','$description', '$comics')";
		if (mysqli_query($conexion, $sql)) {
			echo "Registro insertado correctamente.";
		} else {
			echo "Error: " . mysqli_error($conexion);
		}
	}
?>