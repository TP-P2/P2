/*
 * Oficina.java
 * 
 * Cristian Bogdan Bucutea & Borja Rando Jarque
 * 
 * 03/2020
 * 
 */

package control;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import modelo.*;
import vista.OficinaVista;

/**
 * Oficina de venta de billetes
 * 
 */
public class Oficina {
	public static String VERSION = "Venta de billetes 1.3";
	private final static String INFO_VIAJES = "InformacionViajes.txt";
	private int totalViajes;
	private Set<Viaje> viajes = new HashSet<>();

	private OficinaVista vista;
	
	private static boolean modoDebug = false;

	/**
	 * Construye una Oficina
	 * 
	 */
	public Oficina() throws FileNotFoundException {
		cargarViajes(INFO_VIAJES);
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
	 * Escribe mensaje error
	 * 
	 */
	private void mensajeError(String mensaje, Exception e) {
		if (esModoDebug()) {
			e.printStackTrace();
		}
		// vista.mensajeDialogo(mensaje);
	}

	public static boolean esModoDebug() {
		return modoDebug;
	}

	/**
	 * Sobreescribe toString
	 *
	 */
	@Override
	public String toString() {
		String s = "";
		for (Viaje viaje : viajes) {
			s += viaje;
		}
		return s;
	}
}
