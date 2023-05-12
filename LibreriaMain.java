package actividad07.libreria;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * 
 * @author Antonio Ruiz Benito -> DAW
 *
 */
public class LibreriaMain {

	public static void main (String[] args) throws SQLException {
		
		System.out.println("Bienvenido a la app de registro de libros. "
							+ "Pro favor seleccione un opción: ");

		
		// Variable para controlar el bucle
		boolean control = false;
		// Bucle para que se muestre el menu hasta que el usuario decida salir
		do {
			
			try {
				// Creo un objeto Scanner para interactuar con el usuario y poder recoger datos
				Scanner sc = new Scanner(System.in);
				
				// Menu de opciones
				System.out.println("0 - Salir de la app.\n"
						+ "1 - Restablecer la base de datos.\n"
						+ "2 - Mostrar autores.\n"
						+ "3 - Mostrar libros.\n"
						+ "4 - Modificar autor.\n"
						+ "5 - Eliminar libro.\n");
				
				// Guardo la opcion escogida por el usuario
				int opcionUsuario = sc.nextInt();
				

				if (opcionUsuario < 0 || opcionUsuario > 5) {
					System.out.println("Debes introducir un valor numérico dentro del rango de opciones.");
					continue;
				}
				
				switch(opcionUsuario) {
					// Funcionalidad para salir del programa
					case 0: 
						control = true; // Cambio la variable a true para salir del bucle
						System.out.println("Gracias por utilizar la app.");
						System.out.println("Ejercicio realizado por Antonio Ruiz Benito.");
						break;
					// Funcionalidad para restablecer la base de datos	
					case 1:
						// Creo un objeto GestionBBDD para trabajar con la base de datos
						GestionBBDD baseDatos = new GestionBBDD();
						baseDatos.insertAutor(); // Invoco al metodo insertarAutor() para insertar autores en la tabla Autor
						baseDatos.insertarLibro(); // Invoco al metodo insertarLibro() para insertar libros a los autores
					break;	
					// Funcionalidad para listar los autores de la tabla autores
					case 2:
						// Creo un objeto GestionBBDD para trabajar con la base de datos
						GestionBBDD listarTablaAutores = new GestionBBDD();
						// Invoco al metodo listarAutores() para mostrar una lista con todos los autores de la tabla
						listarTablaAutores.listarAutores();
					break;
					// Funcionalidad para listar los datos de los libros que contiene la tabla Libros
					case 3:
						// Creo un objeto GestionBBDD para poder trabajar con sus funciones
						GestionBBDD listarLibros = new GestionBBDD();
						// Invoco al metodo listarLibros()
						listarLibros.listarLibros();
					break;
					// Funcionalidad para modificar los datos de un autor en la tabla Autor
					case 4:
						// Creo un objeto GestionBBDD para poder trabajar con sus funciones
						GestionBBDD modificarAutor = new GestionBBDD();
						// Invoco al metodo modificarAutor()
						modificarAutor.modificarAutor();
					break;
					// Funcionalidad para eliminar un libro de la tabla Libros
					case 5:
						// Creo un objeto GestionBBDD para poder trabajar con sus funciones
						GestionBBDD eliminarLibro = new GestionBBDD();
						// Invoco al metodo eliminarLibro()
						eliminarLibro.eliminarLibro();
					break;
				}
				
			} catch(InputMismatchException ime) { // Excepcion para capturar si el usuario no introduce un valor numerico
				System.out.println("Debes introducir un valor numérico dentro del rango de opciones.");
			}
			

			
		} while(!control); // Mientras la variable control no sea true, el bucle seguira iterando
		
	}
	
}
