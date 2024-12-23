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
    		AppChat.getInstance(userRepo);		// Crea la única instancia de AppChat
    		
    		VentanaLogin ventanaLogin = new VentanaLogin();
    		ventanaLogin.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
