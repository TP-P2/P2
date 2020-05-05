/*
 * Viajes.java
 * 
 * Cristian Bogdan Bucutea & Borja Rando Jarque
 * 
 * 03/2020
 * 
 */

package modelo;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Viajes de la oficina
 */
public class Viajes {
	private Map<Asiento, Viaje> viajes;
	private PropertyChangeSupport observadores;
	public static String NUEVO_VIAJERO = "Nuevo viajero";
	public static String ELIMINAR_VIAJERO = "Eliminar viajero";

  /**
  * Construye recordatorios
  */   
  public Viajes() {
    viajes = new HashMap<>();  
    observadores = new PropertyChangeSupport(this);
  }

	/**
	 * Añade nuevo observador de los viajes
	 */
	public void nuevoObservador(PropertyChangeListener observador) {
		this.observadores.addPropertyChangeListener(observador);
	}

	/**
	 * Obtiene viaje para un asiento
	 */
	public Viaje obtenerViaje(Object asiento) {
		return viajes.get(fecha); // MODIFICAR
	}

	/**
	 * Indica si hay viajero para un asiento
	 */
	public boolean hayViajero(Asiento asiento) {
		return (obtenerViaje(asiento) != null);
	}

	/**
	 * Añade nuevo viajero
	 */
	public void nuevo(Asiento asiento) {
		viajes.put(asiento.obtenerViajero(), asiento);

		this.observadores.firePropertyChange(NUEVO_VIAJERO, null,
				asiento.obtenerViajero());
	}

	/**
	 * Elimina viajero para un asiento
	 */
	public void eliminar(Asiento asiento) {
		viajes.remove(asiento);

		this.observadores.firePropertyChange(ELIMINAR_VIAJERO, null, fecha);
	}

	/**
	 * toString
	 */
	public String toString() {
		return viajes.toString();
	}
}
