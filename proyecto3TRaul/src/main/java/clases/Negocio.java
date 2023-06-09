package clases;

import java.util.ArrayList;
import java.util.HashMap;

import enumeraciones.Comidas;

public class Negocio {

	private String cif;
//	private String comidas;
	private String nombre;
	private int telefono;
	private String direccion;
	private String email;

	private ArrayList<Comidas> tipoComida;
	private MenuDelDia menuDelDia;
	private Menu menu;

	public Negocio(String nombre, String cif, String direccion, String email, int telefono, /* Comidas comidas, */
			ArrayList<Comidas> tipoComida, MenuDelDia menuDelDia, Menu menu) {
		super();
		this.nombre = nombre;
		this.cif = cif;
		this.direccion = direccion;
		this.email = email;
		this.telefono = telefono;
//		this.comidas = comidas.name();
		this.tipoComida = tipoComida;
		this.menuDelDia = menuDelDia;
		this.menu = menu;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCif() {
		return cif;
	}

	public void setCif(String cif) {
		this.cif = cif;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getTelefono() {
		return telefono;
	}

	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}

//	public String getComidas() {
//		return comidas;
//	}
//
//	public void setComidas(Comidas comidas) {
//		this.comidas = comidas.name();
//	}

	public ArrayList<Comidas> getTipoComida() {
		return tipoComida;
	}

	public void setTipoComida(ArrayList<Comidas> tipoComida) {
		this.tipoComida = tipoComida;
	}

	public MenuDelDia getMenuDelDia() {
		return menuDelDia;
	}

	public void setMenuDelDia(MenuDelDia menuDelDia) {
		this.menuDelDia = menuDelDia;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public HashMap<String, Object> columnas() {
		HashMap<String, Object> columnas = new HashMap<String, Object>();
		columnas.put("nombre", nombre);
		columnas.put("CIF", cif);
		columnas.put("direccion", direccion);
		columnas.put("email", email);
		columnas.put("telefono", telefono);
		columnas.put("tipoComida", tipoComida);
		columnas.put("MenuDelDia", menuDelDia);
		columnas.put("Menu", menu);
//		columnas.put("comidas", comidas);
		return columnas;
	}


	@Override
	public String toString() {
		return "\n\t -Nombre: " + this.nombre + "\n\t -CIF: " + this.cif + "\n\t -Dirección: " + this.direccion
				+ "\n\t -Telefono: " + this.telefono + "\n\t -Email: " + this.email + /*"\n\t -Comidas: " + this.comidas*/
				"\n\t -Tipo/s de comida: "+this.tipoComida+"\n\t -Menu del dia: "+this.menuDelDia+"\n\t -Menu: "+this.menu;
//				+ "\n\t -Tipo/s de comida/s: "
//				+ this.tipoComida + "\n\t -Menu del dia: " + this.menuDelDia + "\n\t -Manu: " + this.menu;
	}

}
