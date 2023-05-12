package actividad07.libreria;

import java.sql.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * 
 * @author Antonio Ruiz Benito -> DAW
 *
 */
public class GestionBBDD {
	
	// Atributos
	private static String datosConexion = "jdbc:mysql://localhost:3306/"; // Direccion de la conexion
	private static String baseDatos = "librosAutores"; // Nombre de la base de datos
	private static String usuario = "root";
	private static String password = ""; 
	private static Connection con; // Atributo para guardar los datos de conexion
	
	// Constructor
	public GestionBBDD() {
		
		try {
			// Establezco la conexion a la base de datos con el metodo getConnection()
			con = DriverManager.getConnection(datosConexion + "?useSSL=true", usuario, password);
			
			try {
				// Invoco al metodo crearBDD para crear la base de datos
				crearBDD();
			
				// Invoco al metodo crearTablaAutor() para crear la tabla Autor
				crearTablaAutor();
				
				// Invoco al metodo crearTablas() para crear la tabla Libro
				crearTablaLibro();
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		


}
	/**
	 * 
	 * Metodo para crear una vase de datos si no existe
	 *  
	 * @throws SQLException
	 */
	private void crearBDD() throws SQLException {
		// Guardo el codigo SQL para crear la base de datos si no existe
		String query = "create database if not exists " + baseDatos + ";";
		// Creo un objeto Statement para poder ejecutar las sentencias SQL inicialmente a null
		Statement stmt = null; 
		
		try {
			// Creo el objeto con el metodo createStatement()
			stmt = con.createStatement();
			// Con el metodo executeUpdate() ejecuto la sentencia query pasando la por parametro
			stmt.executeUpdate(query);
			// Vuelvo a establecer la conexion con la base de datos utilizando el metodo getConnection()
			con = DriverManager.getConnection(datosConexion + baseDatos + "?useSSL=true", usuario, password);
			// Informo si la operacion se ha podido realizar
			System.out.println("Operación realizada con exito.");
			
		} catch(SQLException e) { // Controlo cualquier posible excepcion con la base de datos
			e.printStackTrace();
		} finally {
			stmt.close(); // Libero recursos 
		}
		
		
	}
	
	/**
	 * Metodo para crear una tabla llamada 'Libros' si no existe
	 *  con todos sus campos, tipo y longitud
	 * @throws SQLException 
	 */
	public void crearTablaLibro() throws SQLException {
		// Guardo la sentencia para crear la tabla si no existe con todos sus atributos,tipo y tamaño maximo
		String query = "create table if not exists Libros("
						+ "id int primary key auto_increment," // auto_increment generara automaticamente un numero para el id 
						+ "titulo varchar(50),"
						+ "pais varchar(40),"
						+ "paginas int,"
						+ "genero varchar(30),"
						+ "id_autor int,"
						+ "constraint fk_autor foreign key (id_autor) references Autor(id)"
						+ ");";
		// Creo un objeto Statement para poder ejecutar las sentencias SQL inicialmente a null
		Statement stmt = null;
		
		try {
			// Creo el objeto con el metodo createStatement()
			stmt = con.createStatement();
			// Con el metodo executeUpdate() ejecuto la sentencia query pasando la por parametro
			stmt.executeUpdate(query);
			// Informo si la operacion se ha podido realizar
			System.out.println("Operación realizada con exito.");
						
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			stmt.close(); // Libero recursos
		}
		
	}
	
	/**
	 * Metodo para crear la tabla Autor si no existe con todos sus
	 * campos y longitud
	 * @throws SQLException 
	 */
	public void crearTablaAutor() throws SQLException {
		// Guardo la sentencia para crear la tabla si no existe con todos sus atributos,tipo y tamaño maximo
		String query = "create table if not exists Autor("
				+ "id int primary key auto_increment," // auto_increment generara automaticamente un numero para el id 
				+ "nombre varchar(30),"
				+ "apellidos varchar(50)"
				+ ");";
		
		// Creo un objeto Statement para poder ejecutar las sentencias SQL inicialmente a null
		Statement stmt = null;
		
		try {
			// Creo el objeto con el metodo createStatement()
			stmt = con.createStatement();
			// Con el metodo executeUpdate() ejecuto la sentencia query pasando la por parametro
			stmt.executeUpdate(query);
			// Informo si la operacion se ha podido realizar
			System.out.println("Operación realizada con exito.");
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			stmt.close(); // Libero recursos
		}
		
	}
	
	/**
	 * Metodo para inserta autores en la tabla Autor
	 * 
	 * @param nombre -> Nombre del autor
	 * @param apellidos -> Apellidos del autor
	 * @throws SQLException 
	 */
	public void insertAutor() throws SQLException {
		
		System.out.println("Introduce los datos necesarios para rellenar la tabla autores:");
		// Variable para controlar el bucle while
		boolean control = true;
		
		while(control) { // Mientras control sea true seguira iterando
			Scanner sc = new Scanner(System.in);
			
			System.out.println("Introduce el nombre del autor con un maximo de 30 caracteres:");
			// Guardo el valor introducido
			String nombre = sc.nextLine();
			
			System.out.println("Introduce los apellidos del autor con un maximo de 50 caracteres:");
			// Guardo el valor introducido
			String apellidos = sc.nextLine();
			
			// Guardo la sentencia para insertar autores en la tabla Autor
			String query = "insert into Autor(nombre, apellidos)"
						+ " values('" + nombre + "', '" + apellidos + "');";
			// Creo un objeto Statement para poder ejecutar las sentencias SQL inicialmente a null
			Statement stmt = null;
			
			try {
				// Creo el objeto con el metodo createStatement()
				stmt = con.createStatement();
				// Con el metodo executeUpdate() ejecuto la sentencia query pasando la por parametro
				stmt.executeUpdate(query);
				// Informo si la operacion se ha podido realizar
				System.out.println("Operación insert realizada con exito.");
				
			} catch(SQLException e) { // Controlo las excepciones que pudieran surgir con la base de datos
				e.printStackTrace();
			} finally {
				stmt.close(); // Libero recursos
			}
			
			// Pregunto si desea seguir introduciendo autores
			System.out.println("Desea introducir un autor más? Pulse:\n"
					+ "1 - Si\n"
					+ "2 - No");
			
			try {
				
				// Guardo el valor introducido
				int opcion = sc.nextInt();
				
				// Si la opcion es igual a 2
				if (opcion == 2) {
					control = false; // Cambio el valor de la variable a false y salgo del bucle
				}
			} catch(InputMismatchException ime) { // Controlo si el usuario no introduce un numero
				System.out.println("Opción no valida.");
			}
			
			sc.nextLine(); // Consumo la ultima linea del buffer
			
			
		}
			
	}
	
	/**
	 * Metodo para insertar un libro en la tabla Libros y asociarlo
	 * al autor de la tabla Autor 
	 * @throws SQLException
	 */
	public void insertarLibro() throws SQLException {
		// 1 insertar el libro 
		System.out.println("Introduce los datos necesarios para rellenar la tabla autores:");
		// Variable para controlar el bucle while
		boolean control = true;
		
		while(control) { // Mientras control sea true seguira iterando
			Scanner sc = new Scanner(System.in);
			
			System.out.println("Introduce el nombre del libro con un maximo de 50 caracteres:");
			// Guardo el valor introducido
			String titulo = sc.nextLine();
			
			System.out.println("Introduce el nombre del pais con un maximo de 40 caracteres:");
			// Guardo el valor introducido
			String pais = sc.nextLine();
			
			System.out.println("Introduce el número de paginas.");
			// Guardo el valor introducido
			int paginas = sc.nextInt();
			
			sc.nextLine(); // Consumo el salto de linea
			
			System.out.println("Introduce el genero del libro con un maximo de 30 caracteres:");
			// Guardo el valor introducido
			String genero = sc.nextLine();
			
			System.out.println("Introduce el identificador del autor:");
			// Guardo el valor introducido
			int idAutor = sc.nextInt(); // Variable que relaciona la tabla Autor y Libros
			
			// Guardo la sentencia para insertar libros en la tabla Libros
			String query = "insert into Libros(titulo, pais, paginas, genero, id_autor)"
						+ " values('" + titulo + "', '" + pais + "', '" + paginas + "', '" + genero + "', '" + idAutor + "');";
			
			// Creo un objeto Statement para poder ejecutar las sentencias SQL inicialmente a null
						Statement stmt = null;
						
						try {
							// Creo el objeto con el metodo createStatement()
							stmt = con.createStatement();
							// Con el metodo executeUpdate() ejecuto la sentencia query pasando la por parametro
							stmt.executeUpdate(query);
							// Informo si la operacion se ha podido realizar
							System.out.println("Operación insert realizada con exito.");
							
						} catch(SQLException e) { // Controlo las excepciones que pudieran surgir con la base de datos
							e.printStackTrace();
						} finally {
							stmt.close(); // Libero recursos
						}
						
						// Pregunto si desea seguir introduciendo libros
						System.out.println("Desea introducir un libro más? Pulse:\n"
								+ "1 - Si\n"
								+ "2 - No");
						
						try {
							
							// Guardo el valor introducido
							int opcion = sc.nextInt();
							
							// Si la opcion es igual a 2
							if (opcion == 2) {
								control = false; // Cambio el valor de la variable a false y salgo del bucle
							}
						} catch(InputMismatchException ime) { // Controlo si el usuario no introduce un numero
							System.out.println("Opción no valida.");
						}
						
						sc.nextLine(); // Consumo la ultima linea del buffer
						
			
		}
	}

	
	/**
	 * Metodo para listar los autores de la tabla Autor e imprimir por consola
	 * el nombre y apellido de los autores que contiene la tabla
	 * 
	 * @return -> ArrayList con los autores de la tabla
	 * @throws SQLException
	 */
	public ArrayList<String> listarAutores() throws SQLException {
		// Creo un ArrayList para almacenar la lista de autores de la tabla
		ArrayList<String> autoresLista = new ArrayList<String>();
		// Guardo la sentencia select
		String query = "select * from Autor;";
		// Creo un objeto Statement para poder ejecutar las sentencias SQL inicialmente a null
		Statement stmt = null;
		// Creo un objeto ResultSet para poder alamcenar los datos resultantes de las sentencias SQL inicialmente a null
		ResultSet rs = null;
		
		try {
			// Creo el objeto con el metodo createStatement()
			stmt = con.createStatement();
			// Con el metodo executeUpdate() ejecuto la sentencia query pasando la por parametro
			rs = stmt.executeQuery(query);
			
			while(rs.next()) { // next() mueve el cursor a la siguiente fila
				autoresLista.add(rs.getInt(1)+" - "+ rs.getString(2)+" " + rs.getString(3)); // Con los metodos getInt() y getString() recupero cada campo de la fila
			}
		} catch(SQLException e) { // Controlo las excepciones que pudieran surgir con la base de datos
			e.printStackTrace();
		} finally {
			rs.close(); // Cierro el ResultSet 
			stmt.close(); // Libero recursos
		}
		
		// Con un bucle for each recorro el ArrayList y lo imprimo por consola
		for (String autor : autoresLista) {
			System.out.println(autor);
		}
		
		return autoresLista; // Retorno el array con la lista de autores
		
	}
	
	/**
	 * Metodo para obtener guardar e imprimir los datos de 
	 * la tabla Libros
	 * @return
	 * @throws SQLException
	 */
	public static ArrayList<String> listarLibros() throws SQLException {
		// Creo un ArrayList para almacenar la lista de libros
		ArrayList<String> librosLista = new ArrayList<String>();
		// Guardo la sentencia select
		String query = "select * from Libros;";
		// Creo un objeto Statement para poder ejecutar las sentencias SQL inicialmente a null
		Statement stmt = null;
		// Creo un objeto ResultSet para almacenar los datos resultantes de las sentencias SQL inicialmente a null
		ResultSet rs = null;
		
		try {
			// Creo el objeto con el metodo createStatement()
			stmt = con.createStatement();
			// Con el metodo executeQuery() ejecuto la sentencia query pasando la por parametro
			rs = stmt.executeQuery(query);
			// Con un bucle while y el metodo next() voy recorriendo cada fila de la tabla
			while(rs.next()) {  // Con los metodos getString(lugar que ocupa el campo en la tabla) y getInt() accedo a los datos de cada fila 
				librosLista.add(rs.getInt(1) + " - " + rs.getString(2) + " - " + rs.getString(3) + " - " + rs.getInt(4) + " - " + rs.getString(5) + " - " + rs.getInt(6));
			}
			// Controlo las excepciones que se pudieran producir con la base de datos
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			rs.close(); // Cierro el ResultSet
			stmt.close(); // Libero recursos 
		}
		
		// Con un bucle for each recorro el ArrayList y lo imprimo por consola
		for(String libro : librosLista) {
			System.out.println(libro);
		}
		
		return librosLista; // Retorno el ArrayList con todos los datos de la tabla
	}
	
	/**
	 * Metodo para modificar una fila eligiendo el campo que se desea modificar
	 */
	public void modificarAutor() {
		
		System.out.println("Lista de autores:");
		
		try {
			// Invoco al metodo listarAutores para poder mostrar la lista de autores
			ArrayList<String> datosAutores = listarAutores();
		
			System.out.println("Indica el id numérico del autor que deseas modificar:");
			// Creo un objeto Scanner para interactuar con el usuario y guardar los valores que introduzca
			Scanner sc = new Scanner(System.in);
			// Guardo la opcion escogida
			int opcionId = sc.nextInt();
			
			sc.nextLine(); // Salto de linea
			
			// Si el valor introducido es menor a 1 o mayor a la longitud del ArrayList se informa por consola
			if (opcionId < 1 || opcionId > datosAutores.size()) { // Con el metodo size() obtengo el numero de elementos que contiene el ArrayList
				System.out.println("Has escogido mal el identificador.");
			}
			
			System.out.println("Introduce que campo deseas modificar del autor:");
			// Con el metodo trim() quito los espacios y con el metodo toLowerCase() lo convierto todo a minusculas
			String campo = sc.nextLine().trim().toLowerCase();
			
			// Creo un objeto Statement para poder ejecutar las sentencias SQL inicialmente a null
			Statement stmt = null;
			
			// Si el nombre del campo es igual a nombre, ignorando mayusculas y minusculas
			if(campo.equalsIgnoreCase("nombre")) {
				System.out.println("Introduce el nuevo nombre:");
				
				String nuevoNombre = sc.nextLine();
				
				// Guardo la sentencia select
				String query = "update Autor "
						+ "set nombre = '" + nuevoNombre + "'"
						+ "where id = " + opcionId + ";";
				// Creo el objeto con el metodo createStatement()
				stmt = con.createStatement();
				// Con el metodo executeUpdate() ejecuto la sentencia query para actualizar el valor pasando la por parametro
				stmt.executeUpdate(query);
				// Informo si la operacion se ha podido realizar
				System.out.println("Operación update realizada con exito.");
				// Libero recursos
				stmt.close();

				
			// Si los apellidos del campo es igual a apellidos, ignorando mayusculas y minusculas
			} else if (campo.equalsIgnoreCase("apellidos")) {
				System.out.println("Introduce los nuevos apellidos:");
				
				String nuevoApellido = sc.nextLine();
				
				// Guardo la sentencia update
				String query = "update Autor "
						+ "set apellidos = '" + nuevoApellido + "'"
						+ "where id = " + opcionId + ";";
				// Creo el objeto con el metodo createStatement()
				stmt = con.createStatement();
				// Con el metodo executeUpdate() ejecuto la sentencia query para actualizar el valor pasando la por parametro
				stmt.executeUpdate(query);
				// Informo si la operacion se ha podido realizar
				System.out.println("Operación update realizada con exito.");
				// Libero recursos
				stmt.close();
				
			} else { // Si introduce un campo que no existe
				System.out.println("El campo introducido no existe.");
			}
					
		} catch (SQLException e) { // Controlo cualquier error con la base de datos
			e.printStackTrace();
		} catch (InputMismatchException e) { // Controlo si el usuario no introduce un valos numerico cuando es necesario
			System.out.println("Debes introducir un valor numérico.");
		} 
		
	}
	
	/**
	 * Metodo para borrar una fila de la tabla Libros
	 */
	public static void eliminarLibro() {
		System.out.println("Lista de libros disponibles:");
		
		try {
			// Obtengo, muestro y guardo la lista de libros con el metodo listarLibros()
			ArrayList<String> listaLibros = listarLibros();
			
			System.out.println("Introduce el identificador numérico del libro que deseas eliminar:");
			// Creo un objeto Scanner para interactuar y guardar los valores introducidos por el usuario
			Scanner sc = new Scanner(System.in);
			// Guardo el valor introducido por el usuario
			int opcionId = sc.nextInt();
			
			sc.nextLine(); // Salto de linea
			
			// Creo un objeto Statement para poder ejecutar las sentencias SQL inicialmente a null
			Statement stmt = null;
						
			// Si el valor introducido es menor a 1 o mayor a la longitud del ArrayList se informa por consola
			if (opcionId < 1 || opcionId > listaLibros.size()) { // Con el metodo size() obtengo el numero de elementos que contiene el ArrayList
				System.out.println("Has escogido mal el identificador.");
			} else {
				// Guardo la sentencia delete
				String query = "delete from Libros where id = " + opcionId +";";
				// Creo el objeto con el metodo createStatement()
				stmt = con.createStatement();
				// Con el metodo executeUpdate() ejecuto la sentencia query para borrar la fila del id pasando por parametro
				stmt.executeUpdate(query);
				// Informo si la operacion se ha podido realizar
				System.out.println("Operación delete realizada con exito.");
				// Libero recursos
				stmt.close();

			}	
			
		} catch (SQLException e) { // Controlo cualquier error con la base de datos
			e.printStackTrace();
		} catch (InputMismatchException e) { // Controlo si el usuario no introduce un valos numerico cuando es necesario
			System.out.println("Debes introducir un valor numérico.");
		} 
	}
		
}


















