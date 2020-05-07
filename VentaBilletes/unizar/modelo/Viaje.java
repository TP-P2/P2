/*
 * Viaje.java
 * 
 * Cristian Bogdan Bucutea & Borja Rando Jarque
 * 
 * 03/2020
 * 
 */

package modelo;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 * Viaje de la Oficina
 *
 */
public class Viaje {
	public enum ResultadoOperacion {
		VALIDO, ASIENTO_YA_OCUPADO, ASIENTO_YA_DESOCUPADO, ASIENTO_NO_EXISTE,
		VIAJE_NO_EXISTE
	}

	private String idViaje;
	private String origen;
	private String destino;
	private LocalDate fecha;
	private LocalTime hora;
	private Autobus autobus;
	private static final DateTimeFormatter FORMATEADOR = DateTimeFormatter
			.ofPattern("dd-MM-uuuu");

	/**
	 * Construye un Viaje
	 * 
	 */
	public Viaje() {
	}

	/**
	 * Carga de fichero la información de un Viaje
	 * 
	 */
	public void cargarViaje(Scanner sc) throws FileNotFoundException {
		idViaje = sc.nextLine();
		origen = sc.nextLine();
		destino = sc.nextLine();
		fecha = LocalDate.parse(sc.nextLine(), FORMATEADOR);
		hora = LocalTime.parse(sc.nextLine());
		autobus = new Autobus(sc.nextLine());
	}

	/**
	 * Ocupa un Asiento del Autobus correspondiente al viaje
	 * 
	 */
	public ResultadoOperacion ocuparAsiento(int numAsiento, Viajero viajero) {
		if (!existeAsiento(numAsiento))
			return Viaje.ResultadoOperacion.ASIENTO_NO_EXISTE;
		if (autobus.ocuparAsiento(numAsiento, viajero))
			return ResultadoOperacion.VALIDO;
		return Viaje.ResultadoOperacion.ASIENTO_YA_OCUPADO;
	}

	/**
	 * Desocupa un Asiento del Autobus correspondiente al viaje
	 * 
	 */
	public ResultadoOperacion desocuparAsiento(int numAsiento) {
		if (!existeAsiento(numAsiento))
			return Viaje.ResultadoOperacion.ASIENTO_NO_EXISTE;
		if (autobus.desocuparAsiento(numAsiento))
			return ResultadoOperacion.VALIDO;
		return Viaje.ResultadoOperacion.ASIENTO_YA_DESOCUPADO;
	}

	/**
	 * Guarda en fichero la hoja del Viaje
	 * 
	 */
	public void generarHoja() throws IOException {
		String destino = idViaje + ".txt";
		FileWriter ficheroDestino = new FileWriter(destino);
		ficheroDestino.write(this.toString());
		ficheroDestino.close();
	}

	/**
	 * Determina si existe el asiento para el Viaje
	 * 
	 */
	public boolean existeAsiento(int numAsiento) {
		return autobus.esAsiento(numAsiento);
	}

	/**
	 * Obtiene el el número de asientos disponibles para el Viaje
	 * 
	 */
	public int obtenerAsientosDisponibles() {
		return autobus.numAsientosDisponibles();
	}

	/**
	 * Devuelve la ocupación del Autobus asignado como cadena de caracteres
	 */
	public String obtenerOcupacion() {
		return autobus.obtenerOcupacion();
	}

	/**
	 * Devuelve el id del Viaje
	 * 
	 */
	public String getId() {
		return idViaje;
	}

	/**
	 * Sobreescribe toString
	 *
	 */
	@Override
	public String toString() {
		String s = idViaje + " " + origen + " - " + destino + " "
				+ fecha.format(FORMATEADOR) + " " + hora + " " + autobus + "\n";
		return s;
	}
}