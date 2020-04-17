package vista;

import javax.swing.JFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class OficinaVista extends JFrame
		implements ActionListener, PropertyChangeListener {
	public static final String FICHERO_NO_ENCONTRADO = "Fichero no encontrado";
	public static final String VIAJES_NO_LEIDOS = "No pudo leerse el fichero de viajes";
	@Override
	public void propertyChange(PropertyChangeEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}
}
