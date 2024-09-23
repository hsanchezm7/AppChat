package umu.tds.apps;

import javax.swing.*;
import java.awt.*;

public class VentanaLogin extends JFrame {
	private static final long serialVersionUID = -7659074376023869511L;
	private static final String NOMBRE_VENTANA = "Iniciar sesión en AppChat";
	private static final int SIZE_X = 400;
	private static final int SIZE_Y = 400;
	
	public VentanaLogin() {
		initComponents();
	}
	
	public void initComponents() {
		setTitle(NOMBRE_VENTANA);
		setSize(SIZE_X, SIZE_Y);
		setResizable(false);
		
        setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}
