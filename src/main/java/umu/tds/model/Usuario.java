package umu.tds.model;

//Haría falta poner un atributo de premium para ver si el usuario es premium o no??
import java.time.LocalDate;
import java.util.List;

/**
 * Clase que modela los usuarios de AppChat.
 */
public class Usuario {
	private static final String DEFAULT_SALUDO = "Hey there! I'm using AppChat";

	// Atributos
	private String usuario;
	private String password;
	private String telefono;
	private LocalDate fechaNacimiento;
	private String imagenURL;
	private String saludo;
	private List<Contacto> contactos;


	// Constructores
	/**
	 * Crea un usuario con una lista de contactos vacía.
	 * @param usuario mombre de usuario.
	 * @param password contraseña.
	 * @param telefono número de teléfono.
	 * @param fechaNacimiento fecha de nacimiento.
	 * @param imagenURL URL de la imagen de perfil.
	 * @param saludo texto de saludo (opcional).
	 */
	public Usuario(String usuario, String password, String telefono, LocalDate fechaNacimiento, String imagenURL, String saludo) {
		this.usuario = usuario;
		this.password = password;
		this.telefono = telefono;
		this.fechaNacimiento = fechaNacimiento;
		this.imagenURL = imagenURL;
		this.saludo = saludo;
	}
	
	/**
	 * Crea un usuario con una lista de contactos vacía y con un mensaje de saludo por defecto.
	 * @param usuario mombre de usuario.
	 * @param password contraseña.
	 * @param telefono número de teléfono.
	 * @param fechaNacimiento fecha de nacimiento.
	 * @param imagenURL URL de la imagen de perfil.
	 */
	public Usuario(String usuario, String password, String telefono, LocalDate fechaNacimiento, String imagenURL) {
		this(usuario, password, telefono, fechaNacimiento, imagenURL, DEFAULT_SALUDO);
	}

	// Métodos de consulta
	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getImagenURL() {
		return imagenURL;
	}

	public void setImagenURL(String imagenURL) {
		this.imagenURL = imagenURL;
	}

	public String getSaludo() {
		return saludo;
	}

	public void setSaludo(String saludo) {
		this.saludo = saludo;
	}

	// Métodos
	
	//Si está la clase ContactoIndividual, para añadir contacto se usaría esa??
	/**
	 * Añade el contacto pasado como parámetro a la lista de contactos del usuario.
	 * @param contacto
	 */
	public void addContacto(Contacto contacto) {
		this.contactos.add(contacto);
	}

	/**
	 * Elimina el contacto pasado como parámetro de la lista de contactos del usuario.
	 * @param contacto
	 */
	public void deleteContacto(Contacto contacto) {
		this.contactos.remove(contacto);
	}

}
