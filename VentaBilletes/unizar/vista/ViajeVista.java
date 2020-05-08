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
	private static final int ALTURA_FILA = 25;
	private static final int ANCHURA_COLUMNA = 200;
	private AsientoVista[][] asientosVista;
	private OficinaVista vista;
	private Viajes viajes;
	private ViajeVista viajeVistaSeleccionado;
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
	 * Busca un AsientoVista
	 * 
	 */
	private AsientoVista buscarAsientoVista(Asiento asiento) {
		for (int fila = 0; fila < vista.NUM_FILAS; fila++) {
			for (int columna = 0; columna < vista.NUM_COLUMNAS; columna++) {
				Asiento asientoConcreto = asientosVista[fila][columna].obtenerAsiento();
				// ¿¿ AÑADIR en AsientoVista un método que haga return de un asiento ??
				// obtenerAsiento()
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
	void ponerOcupado(Asiento asiento) { //Habrá que pasar tupla con viaje, asiento, dni y nombre pasajero
		AsientoVista asientoVista = buscarAsientoVista(asiento);
		if (asientoVista != null) {
			asientoVista.ponerOcupado();
		}
	}

	/**
	 * Elimina ocupado de un asiento
	 * 
	 */
	void eliminarOcupado(Asiento asiento) { //Tupla con viaje y asiento
		AsientoVista asientoVista = buscarAsientoVista(asiento);
		if (asientoVista != null) {
			asientoVista.ponerDesocupado();
		}
	}

	/**
	 * Inicia vista del viaje
	 * 
	 */
	private void iniciarViajeVista() {
		for (int fila = 0; fila < vista.NUM_FILAS; fila++) {
			for (int asientoFila = 0; asientoFila < vista.NUM_COLUMNAS; asientoFila++) {
				asientosVista[fila][asientoFila].iniciar();
			}
		}
	}

	/**
	 * Pone asientos de viaje vista
	 * 
	 */
	void ponerAsientos(Viaje viaje) {
		int asiento = 0;

		iniciarViajeVista();

		for (int fila = 0; fila < vista.NUM_FILAS; fila++) {
			for (int columna = 0; columna < vista.NUM_COLUMNAS; columna++) {
				Asiento asientoActual = viajes.buscarAsientoPorPosicion("TV001", asiento);
				AsientoVista asientoVista = asientosVista[fila][columna];
				if (!asientoActual.esPasillo()) {
					asientoVista.ponerAsiento(asientoActual);
					asientoVista.ponerTexto("" + asientoActual);
					if (asientoActual.estaOcupado())
						asientoVista.ponerOcupado();
					else
						asientoVista.ponerDesocupado();
				} else
					asientoVista.ponerPasillo();

				asiento++;
			}
		}
	}

	public Viaje obtenerViaje() {
		// TODO Auto-generated method stub
		return null;
	}

}