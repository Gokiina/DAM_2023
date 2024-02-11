<?php
	if(isset($_GET["name"])){
		$name = $_GET["name"];
		$servidor = "localhost";
		$usuario = "root";
		$password = "";
		$dbname = "AE04";

		$conexion = mysqli_connect($servidor, $usuario, $password, $dbname);
		
		if (!$conexion) {
			echo "Error en la conexion a MySQL: ".mysqli_connect_error();
			exit();
		}
		
		$sql = "SELECT name,description,comics FROM Marvel WHERE name='$name'";
		$result = mysqli_query($conexion, $sql);
		if (mysqli_num_rows($result) > 0) {
			$row = mysqli_fetch_assoc($result);
            echo json_encode($row);
        } else {
            echo json_encode(["error" => "No se encontraron resultados"]);
        }
	} else {
		echo json_encode(["error" => "Nombre de personaje no proporcionado"]);
}
?>