package com.integrador.exception;

public class DBException extends Exception{
	public static final int ERROR_1 = 1; //Conectarse
	public static final int ERROR_2 = 2; //Cerrar conexión
	public static final int ERROR_3 = 3; //Obtener peliculas
	public static final int ERROR_4 = 4; //Insertar pelicula
	public static final int ERROR_5 = 5; //Obtener peliculas por titulo
	public static final int ERROR_6 = 6; //Obtener peliculas por generos
	public static final int ERROR_7 = 7; //Obtener pelicula por codigo
	public static final int ERROR_8 = 8; //Eliminar pelicula
	public static final int ERROR_9 = 9; //Modificar pelicula;
	public static final int ERROR_10 = 10; //Obtener generos;
	public static final int ERROR_11 = 11; //Obtener genero por id;
	public static final int ERROR_12 = 12; //Obtener genero por nombre;
	

	private int error;
	
	public DBException(Integer error, String message) {
		super(message);
		this.error = error;
	}

	public DBException(Integer error, Throwable cause) {
		super(cause);
		this.error = error;
	}

	public DBException(Integer error, String message, Throwable cause) {
		super(message, cause);
		this.error = error;
	}
	
	public String getMessage() {
		switch (error) {
		case ERROR_1:
			return "Se produjo un error conectando a la DB. Error: " + super.getMessage();
		case ERROR_2:
			return "Se produjo un error cerrando la conexión. Error: " + super.getMessage();
		case ERROR_3:
			return "Se produjo un error descargando las peliculas. Error: " + super.getMessage();
		case ERROR_4:
			return "Se produjo un error ingresando la pelicula. Error: " + super.getMessage();
		case ERROR_5:
			return "Se produjo un error obteniendo las peliculas por titulo. Error: " + super.getMessage();
		case ERROR_6:
			return "Se produjo un error obteniendo las peliculas por genero. Error: " + super.getMessage();
		case ERROR_7:
			return "Se produjo un error obteniendo la pelicula por codigo. Error: " + super.getMessage();
		case ERROR_8:
			return "Se produjo un error eliminando la pelicula por codigo. Error: " + super.getMessage();
		case ERROR_9:
			return "Se produjo un error modificando la pelicula por codigo. Error: " + super.getMessage();
		case ERROR_10:
			return "Se produjo un error obteniendo los generos. Error: " + super.getMessage();
		case ERROR_11:
			return "Se produjo un error obteniendo el genero por id. Error: " + super.getMessage();
		case ERROR_12:
			return "Se produjo un error obteniendo el genero por nombre. Error: " + super.getMessage();
		default:
			return super.getMessage();
		}
	}
}
