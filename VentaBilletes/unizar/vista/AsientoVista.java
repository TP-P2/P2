/**
 * AsientoVista.java
 * 
 * Cristian Bogdan Bucutea & Borja Rando Jarque
 * 
 * 05/2020
 * 
 */

package vista;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import modelo.Asiento;

/**
 * Vista de un asiento a partir de un JLabel
 * 
 */
public class AsientoVista extends JLabel {
	
	private OficinaVista vista;
	
	private static final int INC_FUENTE = 8;
	
	private static final Color COLOR_SELECCIONADO = Color.GREEN;
	private static final Color COLOR_OCUPADO = Color.ORANGE;
	private static final Color COLOR_PASILLO = new Color(190,190,190);
	private static Color COLOR_DESOCUPADO;
	
	
	private boolean seleccionado = false;
	private boolean ocupado = false;
	
	private Asiento asiento;
	private Font fuente;
	private Map atributos;
	private int tamañoNormal;

	public enum Formato {DESTACADO, NORMAL};

	/**
	 * Construye la vista del asiento
	 * 
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
	 * 
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

	/**
	 * Obtiene el asiento 
	 * 
	 */
	public Asiento obtenerAsiento() {
		return asiento;
	}

	/**
	 * Pone el asiento
	 * 
	 */
	public void ponerAsiento(Asiento asiento) {
		this.asiento = asiento;
	}

	/**
	 * Selecciona asiento
	 * 
	 */
	public void seleccionar() {
		seleccionado = true;
		setBackground(COLOR_SELECCIONADO);
	}

	/**
	 * Deselecciona asiento
	 * 
	 */
	public void deseleccionar() {
		seleccionado = false;
		if (!ocupado) {
			setBackground(COLOR_DESOCUPADO);
		} else {
			setBackground(COLOR_OCUPADO);
		}
	}

	/**
	 * Indica si un asiento está seleccionado
	 * 
	 */
	public boolean estaSeleccionado() {
		return seleccionado;
	}

	/**
	 * Pone ocupado
	 * 
	 */
	public void ponerOcupado() {
		ocupado = true;
		setBackground(COLOR_OCUPADO);
	}

	/**
	 * Pone desocupado
	 * 
	 */
	public void ponerDesocupado() {
		ocupado = false;
		
	}
	
	/**
	 * Pone pasillo
	 * 
	 */
	public void ponerPasillo() {
		setBackground(COLOR_PASILLO);
	}

	/**
	 * Inicia asiento vista
	 * 
	 */
	public void iniciar() {
		ponerDesocupado();
		deseleccionar();
		asiento = null;
	}

	/**
	 * Pone texto con formato
	 * 
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
	 * 
	 */
	public void ponerTexto(String string) {
		ponerTexto(string, Formato.NORMAL);
	}

	/**
	 * Sobreescribe toString
	 * 
	 */
	@Override
	public String toString() {
		return asiento.toString();
	}



}