/**
 * ViajeVista.java
 * 
 * Cristian Bogdan Bucutea & Borja Rando Jarque
 * 
 * 05/2020
 * 
 */

package vista;

import control.Oficina;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JPanel;

import java.util.Calendar;
import java.util.GregorianCalendar;

import modelo.Asiento;
import modelo.Viaje;
import modelo.Viajes;

/**
 * Vista del viaje a partir de un JPanel
 * 
 */
class ViajeVista extends JPanel {
	private static final int ALTURA_FILA = 15;
	private static final int ANCHURA_COLUMNA = 100;
	private AsientoVista[][] asientosVista;
	private OficinaVista vista;
	private Viajes viajes;
	private Viaje viajeVista;
	public static final boolean RECIBE_EVENTOS_RATON = true;
	public static final boolean NO_RECIBE_EVENTOS_RATON = false;

	/**
	 * Construye la vista del viaje
	 * 
	 */
	ViajeVista(OficinaVista vista, Viajes viajes, boolean recibeEventosRaton) {
		this.vista = vista;
		this.viajes = viajes;

		crearAsientos(recibeEventosRaton);

		this.setPreferredSize(new Dimension(vista.NUM_FILAS * ALTURA_FILA,
				vista.NUM_COLUMNAS * ANCHURA_COLUMNA));
	}

	/**
	 * Crea asientos
	 * 
	 */
	private void crearAsientos(boolean recibeEventosRaton) {
		int filas = vista.NUM_FILAS;
		int asientos = vista.NUM_COLUMNAS;

		setLayout(new GridLayout(filas, asientos));
		asientosVista = new AsientoVista[filas][asientos];

		for (int asiento = 0; asiento < filas; asiento++) {
			for (int fila = 0; fila < asientos; fila++) {

				asientosVista[asiento][fila] = new AsientoVista(vista,
						recibeEventosRaton);
				add(asientosVista[asiento][fila]);
			}
		}
	}

	/**
	 * Busca un AsientoVista para un viaje
	 * 
	 */
	private AsientoVista buscarAsientoVista(Asiento asiento) {
		for (int fila = 0; fila < vista.NUM_FILAS; fila++) {
			for (int columna = 0; columna < vista.NUM_COLUMNAS; columna++) {
				Asiento asientoConcreto = asientosVista[fila][columna].obtenerAsiento();
				if ((asientoConcreto != null) && asientoConcreto.equals(asiento)) {
					return asientosVista[fila][columna];
				}
			}
		}
		return null;
	}

	/**
	 * Pone ocupado un asiento
	 * 
	 */
	void ponerOcupado(Asiento asiento) {
		AsientoVista asientoVista = buscarAsientoVista(asiento);
		if (asientoVista != null) {
			asientoVista.ponerOcupado();
		}
	}

	/**
	 * Elimina ocupado de un asiento
	 * 
	 */
	void eliminarOcupado(Asiento asiento) {
		AsientoVista asientoVista = buscarAsientoVista(asiento);
		if (asientoVista != null) {
			asientoVista.ponerDesocupado();
		}
	}

	/**
	 * Inicia vista del viaje
	 * 
	 */
	private void iniciarViajeVista(Viaje viaje) {
		viajeVista = viaje;
		for (int fila = 0; fila < vista.NUM_FILAS; fila++) {
			for (int asientoFila = 0; asientoFila < vista.NUM_COLUMNAS; asientoFila++) {
				asientosVista[fila][asientoFila].iniciar();
			}
		}
	}

	/**
	 * Pone los asientos de viaje vista
	 * 
	 */
	void ponerAsientos(Viaje viaje) {
		int asiento = 0;

		iniciarViajeVista(viaje);

		for (int fila = 0; fila < vista.NUM_FILAS; fila++) {
			for (int columna = 0; columna < vista.NUM_COLUMNAS; columna++) {
				Asiento asientoActual = viajes.buscarAsientoPorPosicion(viaje.getId(), asiento);
				AsientoVista asientoVista = asientosVista[fila][columna];
				if (!asientoActual.esPasillo()) {
					asientoVista.ponerAsiento(asientoActual);
					asientoVista.ponerTexto("" + asientoActual.getNumero());
					if (asientoActual.estaOcupado()) {
						asientoVista.ponerOcupado();
						asientoVista.setToolTipText(asientoActual.getViajero().toString());
					
					} else {
						asientoVista.ponerDesocupado();
					}
				} else {
					asientoVista.ponerPasillo();
				}
				asiento++;
			}
		}
	}

	/**
	 * Quita los asientos de viaje vista
	 * 
	 */
	public void quitarAsientos() {
		for (int fila = 0; fila < vista.NUM_FILAS; fila++) {
			for (int columna = 0; columna < vista.NUM_COLUMNAS; columna++) {
				asientosVista[fila][columna].setText("");
			}
		}
		
		this.removeAll(); //REVISAR
		this.updateUI();
		
		revalidate();
		repaint();
	}
	
	public Viaje obtenerViaje() {
		return viajeVista;
	}
	
}