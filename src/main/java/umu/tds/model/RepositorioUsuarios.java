package umu.tds.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Clase que contiene y organiza todos los usuarios de la plataforma.
 */
public class RepositorioUsuarios {
	
	/* Atributos */
	private List<Usuario> userRepo;
	private HashMap<String, Usuario> nameUserMap;
	
	
	/* Constructores */
	/**
     * Constructor que inicializa el repositorio de usuarios.
     */
    public RepositorioUsuarios() {
        this.userRepo = new ArrayList<>();
        this.nameUserMap = new HashMap<>();
    }
    
    /* Consulta */
    /**
     * Devuelve el objeto Usuario
     * 
     * @param usuario el usuario a agregar.
     * @return {@code true} si el usuario fue añadido correctamente.
     */
    public Usuario getUserByUsername(String username) {
        return nameUserMap.get(username);
    }

    /* Métodos */
    /**
     * Agrega un nuevo usuario al repositorio. Comprueba que el nombre de usuario esté usado.
     * ya por otro usuario.
     * 
     * @param usuario el usuario a agregar.
     * @return 	{@code false} si no se ha podido añadir al usuario o el nombre es {@code null},
     * 			{@code true} si el usuario fue añadido correctamente.
     */
    public boolean addUserToRepo(Usuario usuario) {
    	String username = usuario.getUsername();
    	if (username == null || username.isEmpty() || nameUserMap.containsKey(username)) return false;
    	
    	return (nameUserMap.put(usuario.getUsername(), usuario) == null) && userRepo.add(usuario);
    }

    /**
     * Elimina un usuario del repositorio.
     * 
     * @param usuario el usuario del repositorio a eliminar.
     */
    public void deleteUserFromRepo(Usuario usuario) {
    	String username = usuario.getUsername();
    	if(nameUserMap.remove(username) != null) userRepo.remove(usuario);
    }
    
}
