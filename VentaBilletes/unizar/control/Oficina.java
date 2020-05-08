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
			Tupla<Viaje, Asiento, String, String> tuplaso = (Tupla<Viaje, Asiento, String, String>) obj;
			viajes.eliminar(tuplaso.a, tuplaso.b);
			break;
			
		case CREAR_HOJA_VIAJE:
		break;
		
		case SALIR:
			System.exit(0);
			break;
		}
	}
	

	public static boolean esModoDebug() {
		return modoDebug;
	}

	public static void main(String[] args) {
		new Oficina();
	}

}
