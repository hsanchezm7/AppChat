package umu.tds.controlador;

import java.util.List;

import umu.tds.model.Mensaje;
import umu.tds.model.RepositorioUsuarios;
import umu.tds.model.Usuario;

public class AppChat {
	
	// Atributos
	private Usuario user;
	private RepositorioUsuarios repoUsuarios;
	
	// Consulta
	public Usuario getCurrentUser() {
		return user;
	}

	public void setUser(Usuario user) {
		this.user = user;
	}

	// CAMBIAR: habrá que pasar uuna copia como en POO
	public RepositorioUsuarios getRepoUsuarios() {
		return repoUsuarios;
	}

	public void setRepoUsuarios(RepositorioUsuarios repoUsuarios) {
		this.repoUsuarios = repoUsuarios;
	}
	
	// Métodos
	public static List<Mensaje> obtenerMensajesRecientesPorUsuario;
	
}
