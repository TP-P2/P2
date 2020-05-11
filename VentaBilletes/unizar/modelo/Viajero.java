/**
 * Viajero.java
 * 
 * Cristian Bogdan Bucutea & Borja Rando Jarque
 * 
 * 03/2020
 * 
 */

package modelo;

/**
 * Viajero
 *
 */
public class Viajero {
	private String DNI;
	private String nombre;

	/**
	 * Construye un Viajero
	 * 
	 */
	public Viajero(String DNI, String nombre) {
		this.DNI = DNI;
		this.nombre = nombre;
	}

	/**
	 * Sobreescribe toString
	 *
	 */
	@Override
	public String toString() {
		String s = nombre + " - " + DNI;
		return s;
	}
}