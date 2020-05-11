/**
 * Autobus.java
 * 
 * Cristian Bogdan Bucutea & Borja Rando Jarque
 * 
 * 03/2020
 * 
 */

package modelo;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Autobus
 *
 */
public class Autobus {
	private final static String DISPOSICION_AUTOBUSES = "DisposicionAutobuses.txt";
	private String matricula;
	private int totalAsientos;
	private int numFilas;
	private int numColumnas;
	private int totalPosiciones;
	private List<Asiento> asientos = new ArrayList<>(); 

	/**
	 * Construye un Autobus
	 * 
	 */
	Autobus(String matricula) throws FileNotFoundException {
		this.matricula = matricula;
		cargarDisposicion(DISPOSICION_AUTOBUSES);
	}

	/**
	 * Carga la disposición de asientos del Autobus
	 * 
	 */
	private void cargarDisposicion(String nombreFichero) throws FileNotFoundException {
		File ficheroDisposicionAutobus = new File(nombreFichero);
		Scanner sc = new Scanner(ficheroDisposicionAutobus);
		int numAsiento = 0;

		if (sc.hasNext()) {
			numFilas = sc.nextInt();
			numColumnas = sc.nextInt();
			totalPosiciones = numFilas * numColumnas;

			for (int i = 0; i < totalPosiciones; i++) {
				numAsiento = sc.nextInt();
				Asiento asiento = new Asiento(numAsiento, i);
				asientos.add(asiento);
				if (!esPasillo(numAsiento))
					totalAsientos++;
			}

		}
		sc.close();
	}

	/**
	 * Ocupa un Asiento del Autobus
	 * 
	 */
	public boolean ocuparAsiento(int numAsiento, Viajero viajero) {
		if (numAsientosDisponibles() > 0) {
			Asiento asiento = getAsientoPorNumero(numAsiento);
			if (asiento != null && !asiento.estaOcupado()) {
				asiento.ocupar(viajero);
				return true;
			}
		}
		return false;
	}

	/**
	 * Desocupa un Asiento del Autobus
	 * 
	 */
	public boolean desocuparAsiento(int numAsiento) {
		Asiento asiento = getAsientoPorNumero(numAsiento);
		if (asiento != null && asiento.estaOcupado()) {
			asiento.desocupar();
			return true;
		}
		return false;
	}

	/**
	 * Obtiene el asiento con el número buscado
	 * 
	 */
	public Asiento getAsientoPorNumero(int numAsiento) {
		for (Asiento asiento : asientos) {
			if (esAsiento(numAsiento) && asiento.getNumero() == numAsiento)
				return asiento;
		}
		return null;
	}
	
	public Asiento getAsientoPorPosicion(int posicion) {
		for (Asiento asiento : asientos) {
			if(asiento.getPosicion() == posicion)
				return asiento;
		}
		return null;
	}

	/**
	 * Obtiene el número de asientos disponibles del Autobus
	 * 
	 */
	public int numAsientosDisponibles() {
		int disponibles = 0;
		for (Asiento asiento : asientos) {
			if (esAsiento(asiento.getNumero()) && !asiento.estaOcupado())
				disponibles++;
		}
		return disponibles;
	}

	/**
	 * Determina si una posición es pasillo al leer la configuración
	 * 
	 */
	public boolean esPasillo(int numAsiento) {
		return numAsiento == Asiento.COD_PASILLO;
	}

	/**
	 * Determina si una posición es Asiento
	 * 
	 */
	public boolean esAsiento(int numAsiento) {
		return numAsiento > 0 && numAsiento <= totalAsientos;
	}

	/**
	 * Devuelve la ocupación de un Autobus como cadena de caracteres
	 * 
	 */
	public String obtenerOcupacion() {
		String s = "";
		int i = 0;
		for (Asiento asiento : asientos) {
			s += asiento.devuelveSimbolo();
			i++;
			if (i == numColumnas) {
				s += "\n";
				i = 0;
			}
		}
		return s;
	}

	/**
	 * Sobreescribe toString
	 *
	 */
	@Override
	public String toString() {
		String s = "Autobus: " + matricula + "\n";
		for (Asiento asiento : asientos) {
			if (esAsiento(asiento.getNumero()))
				s = s + asiento;
		}
		return s;
	}
}