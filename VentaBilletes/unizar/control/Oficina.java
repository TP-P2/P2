/**
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
import java.time.LocalDate;
import java.util.Map;

import modelo.*;
import vista.OficinaVista;

/**
 * Oficina de venta de billetes
 * 
 */
public class Oficina implements OyenteVista {
	public static String VERSION = "Venta de billetes 2.0";
	private Viajes viajes;
	private OficinaVista vista;

	private static boolean modoDebug = false;

	/**
	 * Construye una Oficina
	 * 
	 */
	public Oficina() {

		try {
			viajes = new Viajes();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		vista = OficinaVista.instancia(this, viajes);
		viajes.nuevoObservador(vista);
	}

	/**
	 * Recibe eventos de vista
	 *
	 */
	public void eventoProducido(Evento evento, Object obj) {
		switch (evento) {
		case NUEVO_VIAJERO:
			Tupla<Viaje, Asiento, String, String> tupla = (Tupla<Viaje, Asiento, String, String>) obj;
			viajes.nuevo(tupla.a, tupla.b, new Viajero(tupla.c, tupla.d));
			break;

		case ELIMINAR_VIAJERO:
			Tupla2<Viaje, Asiento> tupla2 = (Tupla2<Viaje, Asiento>) obj;
			viajes.eliminar(tupla2.a, tupla2.b);
			break;

		case CREAR_HOJA_VIAJE:
			/*
			 * try {
			 * 
			 * // 1-Usar tupla.a pero cambiar parámetro de generarHojaViaje Tupla<Viaje,
			 * Asiento, String, String> tupla = (Tupla<Viaje, Asiento, String, String>)
			 * obj; viajes.generarHojaViaje(tuplason.a); //¿¿CAMBIAR método
			 * generarHojaViaje(String id) -> generarHojaViaje(Viaje viaje) ??
			 * 
			 * // 2-Usar id de los viajes pero tener que crear objeto "viaje" String id =
			 * viaje.getId(); viajes.generarHojaViaje(id);
			 * 
			 * 
			 * } catch (FileNotFoundException err1) { throw new FileNotFoundException
			 * ("Error fichero no encontrado."); } catch (IOException err2) { throw new
			 * IOException ("Error escritura."); }
			 */
			break;

		case SALIR:
			System.exit(0);
			break;
		}
	}

	/**
	 * Comprueba si está en modo depuración
	 * 
	 */
	public static boolean esModoDebug() {
		return modoDebug;
	}

	/**
	 * Comprueba la validez de un viaje
	 * 
	 */
	private boolean comprobarViaje(Viaje viaje, String id, LocalDate fecha) {
		return viaje.getId().equals(id) && viaje.getFecha().equals(fecha);
	}

	/**
	 * Obtiene los viajes existentes
	 * 
	 */
	public Map<String, Viaje> getViajes() {
		return viajes.getViajes();
	}

	/**
	 * Método main
	 * 
	 */
	public static void main(String[] args) {
		new Oficina();
	}

	/**
	 * Sobreescribe toString
	 * 
	 */
	@Override
	public String toString() {
		String s = "";
		Map<String, Viaje> listaViajes = viajes.devolverViajes();
		for (Map.Entry<String, Viaje> viaje : listaViajes.entrySet()) {
			if (comprobarViaje(viaje.getValue(), viaje.getKey(), viaje.getValue().getFecha())) {
				s = s + viaje.toString();
			}
		}

		return s;
	}

}
