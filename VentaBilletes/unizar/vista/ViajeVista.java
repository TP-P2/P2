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
	private static final int ALTURA_FILA = 200;
	private static final int ANCHURA_COLUMNA = 25;
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
			asientoVista.eliminarOcupado();
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
	 * Pone días de mes vista
	 * 
	 */
	void ponerAsientos(Viaje viaje) {
		int asiento = 1;

		iniciarViajeVista();

		for (int fila = 0; fila < vista.NUM_FILAS; fila++) {
			for (int columna = 0; columna < vista.NUM_COLUMNAS; columna++) {

				AsientoVista asientoVista = asientosVista[fila][columna];
				Asiento asientoActual = new Asiento(asiento);
				asientoVista.ponerAsiento(asientoActual);
				// if (1=1 /* asiento en el que se ha hecho clic */) {
				//asientoVista.ponerTexto(Integer.toString(asiento),
				//		AsientoVista.Formato.DESTACADO);
				// } else {
				asientoVista.ponerTexto(""+ asiento);
				// }
				// he cambiado ponerViajero por ponerTexto porque aquí creo que indicamos
				// los nºs de asiento solamente
				// tendremos que hacer otro para que cuando le demos clic a uno nos
				// muestre información del viajero sentado

				// igual no es necesario el try/catch
				// el método estaOcupado() lo más seguro habrá que crearlo en la clase
				// "Viajeros" que es = Recordatorios de Agenda
				/*
				 * try { if (viajero.estaOcupado(asiento)) { asientoVista.ponerOcupado();
				 * } } catch (Exception e) { if (VentaBilletes.esModoDebug()) {
				 * DebugVista.devolverInstancia() .mostrar(vista.ERROR_OBTENER_OCUPACION,
				 * e); } }
				 */

				asiento++;

			}
		}
	}

	public Viaje obtenerViaje() {
		// TODO Auto-generated method stub
		return null;
	}

}