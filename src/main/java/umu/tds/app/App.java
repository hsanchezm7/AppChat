package umu.tds.app;

import java.time.LocalDate;
import java.util.List;

import javax.swing.UIManager;

import umu.tds.vista.VentanaLogin;
import umu.tds.controlador.AppChat;
import umu.tds.dao.AdaptadorUsuarioDAO;
import umu.tds.dao.DAOFactory;
import umu.tds.model.RepositorioUsuarios;
import umu.tds.model.Usuario;

public class App {
    public static void main(String[] args) {
    	try {
    		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    		
    		DAOFactory factoriaDAO = DAOFactory.getInstance();
    		AdaptadorUsuarioDAO usuarioDAO = factoriaDAO.getUsuarioDAO();
    		
    		List<Usuario> usuarios = usuarioDAO.recuperarAllUsuarios();
    		RepositorioUsuarios userRepo = new RepositorioUsuarios(usuarios);
    		
    		
    		AppChat.getInstance(userRepo, factoriaDAO);		// Crea la única instancia de AppChat
    		
    		
    		VentanaLogin ventanaLogin = new VentanaLogin();
    		ventanaLogin.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
