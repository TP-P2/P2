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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import modelo.*;
import vista.OficinaVista;

/**
 * Oficina de venta de billetes
 * 
 */
public class Oficina implements OyenteVista {
	public static String VERSION = "Venta de billetes 1.3";
	private Viajes viajes;
	private OficinaVista vista;

	private static boolean modoDebug = false;

	/**
	 * Construye una Oficina
	 * 
	 */
	public Oficina() throws FileNotFoundException {

		viajes = new Viajes();
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
			GregorianCalendar fecha = (GregorianCalendar) obj;
			viajes.eliminar(fecha);
			break;

		case SALIR:
			System.exit(0);
			break;
		}
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
