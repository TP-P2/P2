package vista;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import modelo.Asiento;
import modelo.Viajero;

/**
 * Vista de un asiento a partir de un JLabel
 * 
 */
public class AsientoVista extends JLabel {
	
	private OficinaVista vista;
	
	private static final int INC_FUENTE = 8;
	
	private static final Color COLOR_SELECCIONADO = Color.ORANGE;
	private static final Color COLOR_OCUPADO = Color.GREEN;
	private static final Color COLOR_PASILLO = new Color(190,190,190);
	private static Color COLOR_DESOCUPADO;
	
	private Color colorNoSeleccionado;
	
	private boolean seleccionado = false;
	private boolean ocupado = false;
	
	private Asiento asiento;
	private Font fuente;
	private Map atributos;
	private int tamañoNormal;

	public enum Formato {DESTACADO, NORMAL};

	/**
	 * Construye la vista del asiento
	 */
	AsientoVista(OficinaVista vista, boolean recibeEventosRaton) {
		this.vista = vista;

		fuente = getFont();
		atributos = fuente.getAttributes();
		tamañoNormal = fuente.getSize();
		COLOR_DESOCUPADO = this.getBackground();

		setHorizontalAlignment(SwingConstants.CENTER);
		setOpaque(true);
		setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));

		if (recibeEventosRaton) {
			recibirEventosRaton();
		}
	}

	/**
	 * Recibe los eventos de ratón
	 */
	private void recibirEventosRaton() {
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				AsientoVista asientoVista = (AsientoVista) e.getSource();

				if (asientoVista.obtenerAsiento() != null) {
					vista.seleccionarAsientoVista(asientoVista);
				}
			}
		});
	}

	public Asiento obtenerAsiento() {
		return asiento;
	}

	public void ponerAsiento(Asiento asiento) {
		this.asiento = asiento;
	}

	/**
	 * Selecciona asiento
	 */
	public void seleccionar() {
		seleccionado = true;
		setBackground(COLOR_SELECCIONADO);
	}

	/**
	 * Quita selección asiento
	 */
	public void deseleccionar() {
		seleccionado = false;
		if (!ocupado) {
			setBackground(COLOR_DESOCUPADO);
		} else {
			setBackground(COLOR_OCUPADO);
		}
	}

	public boolean estaSeleccionado() {
		return seleccionado;
	}

	/**
	 * Poner ocupado
	 */
	public void ponerOcupado() {
		ocupado = true;
		setBackground(COLOR_OCUPADO);
	}

	/**
	 * Eliminar ocupado
	 */
	public void ponerDesocupado() {
		ocupado = false;
		
	}
	
	public void ponerPasillo() {
		setBackground(COLOR_PASILLO);
	}

	/**
	 * Inicia asiento vista
	 */
	public void iniciar() {
		// ponerViajero();
		ponerDesocupado();
		deseleccionar();
		asiento = null;
	}

	/**
	 * Pone texto con formato
	 */
	public void ponerTexto(String string, Formato formato) {
		setText(string);

		if (formato == Formato.DESTACADO) {
			atributos.put(TextAttribute.SIZE, fuente.getSize() + INC_FUENTE);
		} else {
			atributos.put(TextAttribute.SIZE, tamañoNormal);
		}
		setFont(fuente.deriveFont(atributos));
	}

	/**
	 * Pone texto con formato normal
	 */
	public void ponerTexto(String string) {
		ponerTexto(string, Formato.NORMAL);
	}

	@Override
	public String toString() {
		return asiento.toString();
	}



}