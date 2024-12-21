package umu.tds.app;

import javax.swing.UIManager;

import umu.tds.controlador.AppChat;
import umu.tds.model.RepositorioUsuarios;
import umu.tds.vista.VentanaLogin;

public class App {
    public static void main(String[] args) {
    	try {
    		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    		RepositorioUsuarios userRepo= new RepositorioUsuarios();
    		AppChat controlador = AppChat.getInstance(userRepo);
    		VentanaLogin login = new VentanaLogin();
			login.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
