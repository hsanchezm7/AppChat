package umu.tds.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Clase que contiene y organiza todos los usuarios de la plataforma.
 */
public class RepositorioUsuarios {

	/* Instancia Singleton */
	private static RepositorioUsuarios unicaInstancia = null;

	/* Atributos */
	private List<Usuario> userRepo;
	private HashMap<String, Usuario> phoneUserMap;

	/* Constructores */
	/**
	 * Constructor que inicializa el repositorio de usuarios.
	 */
	public RepositorioUsuarios() {
		this.userRepo = new ArrayList<>();
		this.phoneUserMap = new HashMap<>();
	}

	/* Consulta */
	/**
	 * Obtiene la instancia única de la clase {@code RepositorioUsuarios}.
	 *
	 * Si la instancia aún no ha sido creada, se inicializa en este momento.
	 *
	 * @return la instancia única de {@code RepositorioUsuarios}.
	 */
	public static RepositorioUsuarios getInstance() {
		if (unicaInstancia == null) {
			unicaInstancia = new RepositorioUsuarios();
		}
		return unicaInstancia;
	}

	/**
	 * Devuelve el Usuario asociado a un número de teléfono. Devuelve {@code null}
	 * si no está registrado.
	 * 
	 * @param phone el número de teléfono del usuario
	 * @return Usuario el usuario asociado
	 */
	public Usuario getUserByPhone(String phone) {
		return phoneUserMap.get(phone);
	}

	/* Métodos */
	/**
	 * Agrega un nuevo usuario al repositorio. Comprueba que el nombre de usuario
	 * esté usado. ya por otro usuario.
	 * 
	 * @param usuario el usuario a agregar
	 * @return {@code false} si no se ha podido añadir al usuario o el nombre es
	 *         {@code null}, {@code true} si el usuario fue añadido correctamente
	 */
	public boolean addUserToRepo(Usuario usuario) {
		String username = usuario.getTelefono();
		if (username == null || username.isEmpty() || phoneUserMap.containsKey(username))
			return false;

		System.out.println("Registrado usuario: " + usuario.toString());
		return (phoneUserMap.put(usuario.getTelefono(), usuario) == null) && userRepo.add(usuario);
	}

	/**
	 * Elimina un usuario del repositorio.
	 * 
	 * @param usuario el usuario del repositorio a eliminar
	 */
	public void deleteUserFromRepo(Usuario usuario) {
		String username = usuario.getTelefono();
		if (phoneUserMap.remove(username) != null)
			userRepo.remove(usuario);
	}

}
