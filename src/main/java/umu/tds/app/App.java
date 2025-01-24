package umu.tds.app;

import javax.swing.UIManager;

import umu.tds.controlador.AppChat;
import umu.tds.vista.VentanaLogin;

public class App {
	public static void main(String[] args) {
		initialize();
	}

	private static void initialize() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

			/* Inicializar la instancia-controlador única de AppChat */
			AppChat.getInstance();

			VentanaLogin ventanaLogin = new VentanaLogin();
			ventanaLogin.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
