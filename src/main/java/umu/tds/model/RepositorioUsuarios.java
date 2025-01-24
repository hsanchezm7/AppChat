package umu.tds.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import umu.tds.dao.AdaptadorUsuarioDAO;
import umu.tds.dao.DAOFactory;

/**
 * Clase que contiene y organiza todos los usuarios del sistema.
 */
public class RepositorioUsuarios {

	/* Instancia Singleton */
	private static RepositorioUsuarios unicaInstancia = null;

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
	
	/* Atributos */
	
	private DAOFactory daoFactory;
	
	private AdaptadorUsuarioDAO usuarioDAO;

	private List<Usuario> userRepo;
	private HashMap<String, Usuario> phoneUserMap;
	
	

	/* Constructores */
	/**
	 * Constructor que inicializa el repositorio de usuarios.
	 */
	public RepositorioUsuarios() {
		// TODO: ¿Añadir try-catch?
		this.daoFactory = DAOFactory.getInstance();

		this.usuarioDAO = daoFactory.getUsuarioDAO();

		initRepo();
	}

	/* Métodos */
	/**
	 * Agrega un nuevo usuario al repositorio. Comprueba que el nombre de usuario
	 * esté usado. ya por otro usuario.
	 *
	 * @param usuario el usuario a agregar
	 * @return {@code false} si no se ha podido añadir al usuario o el teléfono no
	 *         es válido {@code null}, {@code true} si el usuario fue añadido
	 *         correctamente
	 */
	public boolean addUserToRepo(Usuario usuario) {
		String phone = usuario.getPhone();
		if (phone == null || phone.isEmpty() || phoneUserMap.containsKey(phone)) {
			return false;
		}

		System.out.println("Registrado usuario: " + usuario.toString());
		return (phoneUserMap.put(usuario.getPhone(), usuario) == null) && userRepo.add(usuario);
	}

	/* Consulta */

	/**
	 * Elimina un usuario del repositorio.
	 *
	 * @param usuario el usuario del repositorio a eliminar
	 */
	public void deleteUserFromRepo(Usuario usuario) {
		String username = usuario.getPhone();
		if (phoneUserMap.remove(username) != null) {
			userRepo.remove(usuario);
		}
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

	/**
	 * Inicializa el repositorio cargando los objetos persistentes y creando y
	 * poblando las estructuras de datos.
	 */
	private void initRepo() {
		// TODO: ¿Añadir try-catch ?
		List<Usuario> usuarios = usuarioDAO.recuperarAllUsuarios();
		this.userRepo = new LinkedList<>(usuarios); // Castea a LinkedList<>

		this.phoneUserMap = new HashMap<>(); // Inicializar mapa vacío

		// Poblar mapa con usuarios
		for (Usuario usuario : usuarios) {
			phoneUserMap.put(usuario.getPhone(), usuario);
		}
	}

}
