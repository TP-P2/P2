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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import vista.OficinaVista;

/**
 * Viajes de la oficina
 */
public class Viajes {
	private List<Viaje> viajes;
	private int totalViajes;
	private PropertyChangeSupport observadores;
	private final static String INFO_VIAJES = "InformacionViajes.txt";
	public static String NUEVO_VIAJERO = "Nuevo viajero";
	public static String ELIMINAR_VIAJERO = "Eliminar viajero";

	/**
	 * Construye viajes
	 * 3
	 */
	public Viajes() throws FileNotFoundException {
		viajes = new ArrayList<>();
		cargarViajes(INFO_VIAJES);
		observadores = new PropertyChangeSupport(this);
	}

	/**
	 * Añade nuevo observador de los viajes
	 */
	public void nuevoObservador(PropertyChangeListener observador) {
		this.observadores.addPropertyChangeListener(observador);
	}

	/**
	 * Carga la información de los viajes contenida en el fichero
	 * 
	 */
	private void cargarViajes(String nombreFicheroViajes) throws FileNotFoundException {
		File ficheroInfoViajes = new File(nombreFicheroViajes);
		if (ficheroInfoViajes != null) {
			try {
				Scanner sc = new Scanner(ficheroInfoViajes);
				if (sc.hasNextInt()) {
					totalViajes = sc.nextInt();
				}

				for (int i = 0; i < totalViajes; i++) {
					if (sc.hasNext()) {
						sc.nextLine(); // Línea en blanco como separador de viajes
						Viaje viaje = new Viaje();
						viaje.cargarViaje(sc);
						viajes.add(viaje);
					}
				}
				sc.close();
			} catch (FileNotFoundException e1) {
				mensajeError(OficinaVista.FICHERO_NO_ENCONTRADO, e1);
			} catch (Exception e2) {
				mensajeError(OficinaVista.VIAJES_NO_LEIDOS, e2);
			}
		}

	}

	/**
	 * Ocupa un Asiento de un Autobus
	 * 
	 */
	public Viaje.ResultadoOperacion ocuparAsiento(String idViaje, int numAsiento,
			Viajero viajero) {
		Viaje viaje = getViajePorId(idViaje);
		if (viaje == null)
			return Viaje.ResultadoOperacion.VIAJE_NO_EXISTE;
		return viaje.ocuparAsiento(numAsiento, viajero);
	}

	/**
	 * Desocupa un Asiento de un Autobus
	 * 
	 */
	public Viaje.ResultadoOperacion desocuparAsiento(String idViaje, int numAsiento) {
		Viaje viaje = getViajePorId(idViaje);
		if (viaje == null)
			return Viaje.ResultadoOperacion.VIAJE_NO_EXISTE;
		return viaje.desocuparAsiento(numAsiento);
	}

	/**
	 * Guarda en fichero la hoja de un Viaje
	 * 
	 */
	public boolean generarHojaViaje(String idViaje) throws IOException {
		Viaje viaje = getViajePorId(idViaje);
		if (viaje != null) {
			viaje.generarHoja();
			return true;
		}
		return false;
	}

	/**
	 * Devuelve la ocupación del Autobus asignado a un Viaje como cadena de
	 * caracteres
	 */
	public String obtenerOcupacion(String idViaje) {
		Viaje viaje = getViajePorId(idViaje);
		if (viaje != null)
			return viaje.obtenerOcupacion();
		return null;
	}

	/**
	 * Obtiene el Viaje correspondiente a su identificador
	 * 
	 */
	private Viaje getViajePorId(String idViaje) {
		for (Viaje viaje : viajes) {
			if (viaje != null && viaje.getId().equals(idViaje))
				return viaje;
		}
		return null;
	}

	/**
	 * Obtiene viajero para un asiento
	 */
	public Viajero obtenerViajero(Object asiento) {
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
	public void nuevo(Viaje viaje, Asiento asiento, Viajero viajero) { 
		viaje.ocuparAsiento(asiento.getNumero(), viajero); //comprobar !=null
		this.observadores.firePropertyChange(NUEVO_VIAJERO, null,
				asiento);
	}

	/**
	 * Elimina viajero para un asiento
	 */
	public void eliminar(Viaje viaje, Asiento asiento) {
		viaje.desocuparAsiento(asiento.getNumero()); //comprobar !=null

		this.observadores.firePropertyChange(ELIMINAR_VIAJERO, null, asiento);
	}

	/**
	 * toString
	 */
	public String toString() {
		return viajes.toString();
	}
}
