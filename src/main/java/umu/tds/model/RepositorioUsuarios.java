package umu.tds.model;

import java.util.ArrayList;
import java.util.List;


/**
 * Clase que contiene y organiza todos los usuarios de la plataforma.
 */
public class RepositorioUsuarios {
	
	// Atributos
	private List<Usuario> usuarios;
	
	
	// Constructores	
	/**
     * Constructor que inicializa el repositorio de usuarios.
     */
    public RepositorioUsuarios() {
        this.usuarios = new ArrayList<>();
    }

    // Métodos
    /**
     * Agrega un nuevo usuario al repositorio.
     * 
     * @param usuario el usuario a agregar.
     * @return {@code true} si el usuario fue eliminado exitosamente.
     */
    public boolean addUsuarioToRepo(Usuario usuario) {
        return this.usuarios.add(usuario);
    }

    /**
     * Elimina un usuario del repositorio.
     * 
     * @param usuario el usuario del repositorio a eliminar.
     * @return {@code true} si el usuario fue eliminado exitosamente.
     */
    public boolean deleteUsuarioToRepo(Usuario usuario) {
        return this.usuarios.remove(usuario);
    }
    
}
