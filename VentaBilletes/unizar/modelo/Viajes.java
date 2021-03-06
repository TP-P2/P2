/**
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
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import control.Oficina;
import vista.OficinaVista;

/**
 * Viajes de la oficina
 * 
 */
public class Viajes {
	private Map<String, Viaje> viajes;
	private int totalViajes;
	private PropertyChangeSupport observadores;
	private final static String INFO_VIAJES = "InformacionViajes.txt";
	public static String NUEVO_VIAJERO = "Nuevo viajero";
	public static String ELIMINAR_VIAJERO = "Eliminar viajero";

	/**
	 * Construye viajes
	 * 
	 */
	public Viajes() throws FileNotFoundException {
		viajes = new HashMap<>();
		cargarViajes(INFO_VIAJES);
		observadores = new PropertyChangeSupport(this);
	}

	/**
	 * A�ade nuevo observador de los viajes
	 * 
	 */
	public void nuevoObservador(PropertyChangeListener observador) {
		this.observadores.addPropertyChangeListener(observador);
	}

	/**
	 * Carga la informaci�n de los viajes contenida en el fichero
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
						sc.nextLine(); // L�nea en blanco como separador de viajes
						Viaje viaje = new Viaje();
						viaje.cargarViaje(sc);
						viajes.put(viaje.getId(), viaje);
						
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

	public Map<String, Viaje> getViajes(){
		return viajes;
	}
	
	public Map<String, Viaje> buscarViajesPorFecha(LocalDate fecha){
		Map<String, Viaje> viajesFecha = new HashMap<>();
		for(Map.Entry<String, Viaje> viaje : viajes.entrySet()) {
			if(viaje.getValue().getFecha().equals(fecha))
				viajesFecha.put(viaje.getKey(), viaje.getValue());
		}
		return viajesFecha;
	}
	
	public Asiento buscarAsiento(String idViaje, int numAsiento) {
		return getViajePorId(idViaje).buscarAsiento(numAsiento); 
	}
	
	public Asiento buscarAsientoPorPosicion(String idViaje, int posicion) {
		return getViajePorId(idViaje).buscarAsientoPorPosicion(posicion); 
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
	 * Devuelve la ocupaci�n del Autobus asignado a un Viaje como cadena de
	 * caracteres
	 * 
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
	public Viaje getViajePorId(String idViaje) {
		for (Map.Entry<String, Viaje> viaje : viajes.entrySet()) {
			if (viaje != null && viaje.getKey().equals(idViaje))
				return viaje.getValue();
		}
		return null;
	}

	/**
	 * Obtiene viajero para un asiento
	 */
	public Viajero obtenerViajero(Asiento asiento) {
		return asiento.getViajero();// MODIFICAR
	}

	/**
	 * Indica si hay viajero para un asiento
	 * 
	 */
	public boolean hayViajero(Asiento asiento) {
		return (asiento.estaOcupado());
	}

	/**
	 * A�ade nuevo viajero
	 * 
	 */
	public void nuevo(Viaje viaje, Asiento asiento, Viajero viajero) {
		viaje.ocuparAsiento(asiento.getNumero(), viajero); // comprobar !=null
		this.observadores.firePropertyChange(NUEVO_VIAJERO, null, asiento);
	}

	/**
	 * Elimina viajero para un asiento
	 * 
	 */
	public void eliminar(Viaje viaje, Asiento asiento) {
		viaje.desocuparAsiento(asiento.getNumero()); // comprobar !=null
		this.observadores.firePropertyChange(ELIMINAR_VIAJERO, null, asiento);
	}
	
	/**
	 * Escribe mensaje error
	 * 
	 */
	private void mensajeError(String mensaje, Exception e) {
		if (Oficina.esModoDebug()) {
			e.printStackTrace();
		}
		// vista.mensajeDialogo(mensaje);
	}

	/**
	 * Sobreescribe toString
	 *
	 */
	@Override
	public String toString() {
		String s = "";
		for (Map.Entry<String, Viaje> viaje : viajes.entrySet()) {
			s += viaje;
		}
		return s;
	}

	/**
	 * Devuelve el viaje con el idViaje
	 * 
	 */
	public Map<String, Viaje> devolverViajes() {
		return viajes;
	}
	
}
