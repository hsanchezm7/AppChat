package umu.tds.app;

import javax.swing.UIManager;

import umu.tds.vista.VentanaLogin;
import umu.tds.controlador.AppChat;
import umu.tds.dao.DAOFactory;
import umu.tds.model.RepositorioUsuarios;

public class App {
    public static void main(String[] args) {
    	try {
    		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    		
    		DAOFactory daoFactory = DAOFactory.getInstance();
    		
    		RepositorioUsuarios userRepo = new RepositorioUsuarios();
    		AppChat.getInstance(userRepo);		// Crea la única instancia de AppChat
    		
    		VentanaLogin ventanaLogin = new VentanaLogin();
    		ventanaLogin.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
