package tp;

import java.io.FileNotFoundException;
import java.io.IOException;

import control.Oficina;
import modelo.Viajero;

/*
 * VentaBilletesTest.java
 * 
 * Version 1.3
 * 
 * Cristian Bogdan Bucutea & Borja Rando Jarque
 * 
 * 03/2020
 * 
 */

public class VentaBilletesTest {

	public static void main(String[] args) {

		try {
			Oficina oficina = new Oficina();
			Viajero v1 = new Viajero("18452318Q", "Borja Rando Jarque");
			Viajero v2 = new Viajero("ABCDEFGHI", "Cristian Bucutea");
			Viajero v3 = new Viajero("123456789", "Hombrecillo Prueba");
			Viajero v4 = new Viajero("ABCDEFGHI", "Pruebo Pruebez Pruebez");

			oficina.ocuparAsiento("TV001", 2, v1);
			oficina.ocuparAsiento("TV001", 10, v4);
			oficina.ocuparAsiento("VT001", 54, v2);
			oficina.ocuparAsiento("TZ001", 3, v3);
			oficina.generarHojaViaje("TV001");
			oficina.generarHojaViaje("VT001");
			System.out.println(oficina.obtenerOcupacion("TV001"));
			System.out.println(oficina);

			// CASOS DE PRUEBA
			oficina.ocuparAsiento("TV001", 2, v2); // Ocupar asiento ya ocupado
			oficina.ocuparAsiento("TV001", 999, v2); // Ocupar asiento que no existe
			oficina.ocuparAsiento("ZZZZZ", 4, v2); // Ocupar asiento de viaje no
													// existente

			oficina.desocuparAsiento("TV001", 2);
			oficina.desocuparAsiento("TV001", 2); // Desocupar asiento ya desocupado
			oficina.desocuparAsiento("TV001", 999); // Desocupar asiento que no existe
			oficina.desocuparAsiento("ZZZZZ", 4); // Desocupar asiento de viaje no
													// existente

			oficina.generarHojaViaje("AA"); // Generar hoja de Viaje no existente

		} catch (FileNotFoundException e) {
			System.out.println("No se ha encontrado el fichero indicado.");
			e.printStackTrace();

		} catch (IOException e) {
			System.out.println("Error al generar fichero de hoja de viaje.");
			e.printStackTrace();
		}
	}
}
