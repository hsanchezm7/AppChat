package umu.tds.controlador;

import java.util.List;

import umu.tds.model.Mensaje;
import umu.tds.model.RepositorioUsuarios;
import umu.tds.model.Usuario;

/**
 * Clase controlador de la aplicación.
 */
public class AppChat {
	/* TODO: IMPLEMENTAR Singleton */
	private static AppChat singleton = null;

	/* Atributos */
	private Usuario user;
	private RepositorioUsuarios repoUsuarios;
	
	/* Constructor */
	private AppChat(Usuario user, RepositorioUsuarios repoUsuarios) {
		this.user = null;
		this.repoUsuarios = repoUsuarios;
	}
	
	/* Consulta */
	public Usuario getCurrentUser() {
		return user;
	}
	
	/* Métodos */
    /**
     * Inicia sesión en la aplicación.
     * 
     * @param username el nombre de usuario.
     * @param password la contraseña del usuario.
     * @return 	{@code false} si no existe un usuario registrado con ese nombre 
     * 			o la contraseña es incorrecta, {@code true} si se ha iniciado
     * 			sesión exitosamente.
     */
	public boolean login(String username, String password) {
		Usuario usuarioReg = repoUsuarios.getUserByUsername(username);
		if (!isUserRegistered(username) || !usuarioReg.getPassword().equals(password)) return false;
		
		this.user = usuarioReg;
		
		return true;
	}
	
	public boolean isUserRegistered(String username) {
		return repoUsuarios.getUserByUsername(username) != null;
	}
	
	public static List<Mensaje> obtenerMensajesRecientesPorUsuario;
	
}
