/*
 * Asiento.java
 * 
 * Cristian Bogdan Bucutea & Borja Rando Jarque
 * 
 * 03/2020
 * 
 */

package modelo;

/**
 * Asiento de un Autobus
 *
 */
public class Asiento {
	private int numero;
	private boolean ocupado;
	private Viajero viajero;

	/**
	 * Construye un Asiento
	 * 
	 */
	public Asiento(int numero) {
		this.numero = numero;
		ocupado = false;
	}

	/**
	 * Ocupa el Asiento
	 * 
	 */
	public boolean ocupar(Viajero viajero) {
		if (!ocupado) {
			this.viajero = viajero;
			ocupado = true;
			return true;
		}
		return false;
	}

	/**
	 * Desocupa el Asiento
	 * 
	 */
	public boolean desocupar() {
		if (ocupado) {
			this.viajero = null;
			ocupado = false;
			return true;
		}
		return false;
	}

	/**
	 * Devuelve el estado de ocupacion del asiento
	 * 
	 */
	public boolean estaOcupado() {
		return ocupado;
	}

	/**
	 * Devuelve el número del asiento
	 * 
	 */
	public int getNumero() {
		return numero;
	}

	/**
	 * Devuelve símbolo que representa la ocupación del Asiento
	 * 
	 */
	public String devuelveSimbolo() {
		if (numero == Autobus.COD_PASILLO)
			return "[P]";
		if (ocupado)
			return "[X]";
		return "[ ]";
	}

	/**
	 * Sobreescribe toString
	 *
	 */
	@Override
	public String toString() {
		String s = "" + numero;
		if (viajero != null) {
			s = s + " - " + viajero + "\n";
		} else {
			s = s + "\n";
		}
		return s;
	}

	public Viajero getViajero() {
		return viajero;
	}
}
