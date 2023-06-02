package clases;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Scanner;

import enumeraciones.Comidas;
import enumeraciones.TipoProducto;
import excepciones.CampoVacioException;
import excepciones.UsuarioNoExisteException;
import utils.DAO;

public class Trabajador {

	private String nombre;
	private String email;
	private String contrasenia;
	private int telefono;

	public Trabajador(String email, String nombre, String contraseña, int telefono) {
		this.nombre = nombre;
		this.email = email;
		this.contrasenia = contraseña;
		this.telefono = telefono;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContraseña() {
		return contrasenia;
	}

	public void setContraseña(String contraseña) {
		this.contrasenia = contraseña;
	}

	public int getTelefono() {
		return telefono;
	}

	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}

	public HashMap<String, Object> columnas() {
		HashMap<String, Object> columnas = new HashMap<String, Object>();
		columnas.put("email", email);
		columnas.put("nombre", nombre);
		columnas.put("contrasenia", contrasenia);
		columnas.put("telefono", telefono);

		return columnas;
	}

	@Override
	public String toString() { // NO HE PUESTO LA CONTRASEÑA AQUÍ A PROPÓSITO
		return "\n\t -Nombre: " + this.nombre + "\n\t -Email: " + this.email + "\n\t -Telefono: " + this.telefono;
	}

	public static void eliminarTrabajador(Trabajador t) {
		try {
			DAO.delete("Trabajador", t.columnas());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void actualizarTrabajador(Trabajador t, String nombre, String email, String contrasenia, int telefono)
			throws SQLException, CampoVacioException {
		if (email.isEmpty() || nombre.isEmpty() || contrasenia.isEmpty() || telefono == 0) {
			throw new CampoVacioException();
		}
		try {
			HashMap<String, Object> datosAActualizar = new HashMap<String, Object>();
			datosAActualizar.put("email", email);
			datosAActualizar.put("nombre", nombre);
			datosAActualizar.put("contrasenia", contrasenia);
			datosAActualizar.put("telefono", telefono);
			HashMap<String, Object> restricciones = new HashMap<String, Object>();
			restricciones.put("email", email);
			DAO.actualizar("Trabajador", datosAActualizar, restricciones);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Trabajador consultarTrabajador(String email, String contrasenia)
			throws SQLException, UsuarioNoExisteException, CampoVacioException {
		if (email.isEmpty() || contrasenia.isEmpty()) {
			throw new CampoVacioException();
		}
		LinkedHashSet<String> columnasSelect = new LinkedHashSet<>();
		columnasSelect.add("email");
		columnasSelect.add("nombre");
		columnasSelect.add("contrasenia");
		columnasSelect.add("telefono");
		HashMap<String, Object> restricciones = new HashMap<>();
		restricciones.put("email", email);
		restricciones.put("contrasenia", contrasenia);

		ArrayList<Object> resultado = DAO.consultar("Trabajador", columnasSelect, restricciones);
		if (resultado.isEmpty()) {
			throw new UsuarioNoExisteException("El trabajador no existe");
		} else {
			String emailResultado = String.valueOf(resultado.get(0));
			String nombre = String.valueOf(resultado.get(1));
			String contraseniaResultado = String.valueOf(resultado.get(2));
			int telefono = (int) resultado.get(3);
			Trabajador tResult = new Trabajador(emailResultado, nombre, contraseniaResultado, telefono);
			return tResult;
		}
	}

	public static void añadirTrabajador(Trabajador t, String email, String nombre, String contrasenia, int telefono)
			throws SQLException, CampoVacioException {
		if (email.isEmpty() || nombre.isEmpty() || contrasenia.isEmpty() || telefono == 0) {
			throw new CampoVacioException();
		}
		DAO.insert("Trabajador", t.columnas());
	}

	public static void añadirProducto(String nombre, String precio, String tipoProducto)
			throws SQLException, CampoVacioException, NumberFormatException {
		if (nombre.isEmpty() || precio.isEmpty()) {
			throw new CampoVacioException();
		}
		float precioF = Float.parseFloat(precio);

		HashMap<String, Object> campos = new HashMap<>();
		campos.put("nombre", nombre);
		campos.put("precio", precioF);
		campos.put("tipoProducto", tipoProducto);

		DAO.insert("Producto", campos);
	}

	public static void añadirMesa(String numero, String capacidad) throws SQLException, CampoVacioException, NumberFormatException {
		if (numero.isEmpty() || capacidad.isEmpty()) {
			throw new CampoVacioException();
		}
		int numeroInt = Integer.parseInt(numero);
		int capacidadInt = Integer.parseInt(capacidad);

		LinkedHashMap<String, Object> campos = new LinkedHashMap<>();
		campos.put("numero", numeroInt);
		campos.put("capacidad", capacidadInt);
		campos.put("estaOcupada", 0);
		DAO.insert("Mesa", campos);
	}

	public void consultarTrabajadores(Trabajador t, String restriccion) {
		Scanner sc = new Scanner(System.in);
		String respuesta;
		do {
			System.out.println("Quieres aplicar una restriccion (filtro) a la consulta?(responde con Si o No)");
			respuesta = sc.nextLine();
			if (respuesta.equals("Si")) {
				System.out.println("Dime el valor de la restriccion para hacer la consulta");
				String valor = sc.nextLine();
				HashMap<String, Object> restricciones = new HashMap<String, Object>();
				restricciones.put(restriccion, valor);
				try {
					ArrayList<Object> trabajadores = DAO.consultar("Trabajador",
							new LinkedHashSet<String>(t.columnas().keySet()), restricciones);
					for (byte i = 0; i < trabajadores.size(); i++) {
						System.out.println(trabajadores.get(i));
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (!(respuesta.equals("Si") || respuesta.equals("No"))) {
				System.out.println("Respuesta incorrecta, debes responder con 'Si' o 'No'");
			}
			if (respuesta.equals("No")) {
				try {
					ArrayList<Object> trabajadores = DAO.consultar("Trabajador",
							new LinkedHashSet<String>(t.columnas().keySet()), new HashMap<String, Object>());
					for (byte i = 0; i < trabajadores.size(); i++) {
						System.out.println(trabajadores.get(i));
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} while (!(respuesta.equals("Si") || respuesta.equals("No")));
	}

	public void añadirMesa(Mesa m) {
		try {
			DAO.insert("Mesa", m.columnas());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void eliminarMesa(Mesa m) {
		try {
			DAO.delete("Mesa", m.columnas());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

//	public void marcarMesaLibre(Mesa m) {
//		m.setEstaOcupada(false);
//		HashMap<String, Object> restricciones = new HashMap<String, Object>();
//		HashMap<String, Object> valoresNuevos = new HashMap<String, Object>();
//		valoresNuevos.put("estaOcupada", false);
//		try {
//			DAO.actualizar("Mesa", valoresNuevos, restricciones);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

//	public void marcarMesaOcupada(Mesa m) {
//		m.setEstaOcupada(true);
//		HashMap<String, Object> restricciones = new HashMap<String, Object>();
//		HashMap<String, Object> valoresNuevos = new HashMap<String, Object>();
//		valoresNuevos.put("estaOcupada", true);
//		try {
//			DAO.actualizar("Mesa", valoresNuevos, restricciones);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

	public void consultarMesasLibres(Mesa m) {
		ArrayList<Object> mesas;
		HashMap<String, Object> restricciones = new HashMap<>();
		restricciones.put("estaOcupada", 0);
		try {
			mesas = DAO.consultar("Mesa", new LinkedHashSet<String>(m.columnas().keySet()), restricciones);
			for (byte i = 0; i < mesas.size(); i++) {
				System.out.println(mesas.get(i));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void consultarMesasOcupadas(Mesa m) {
		ArrayList<Object> mesas;
		HashMap<String, Object> restricciones = new HashMap<>();
		restricciones.put("estaOcupada", 1);
		try {
			mesas = DAO.consultar("Mesa", new LinkedHashSet<String>(m.columnas().keySet()), restricciones);
			for (byte i = 0; i < mesas.size(); i++) {
				System.out.println(mesas.get(i));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public MenuDelDia consultarMenuDelDia() {
		MenuDelDia menuDDConsult = new MenuDelDia();
		return menuDDConsult;
	}

}
