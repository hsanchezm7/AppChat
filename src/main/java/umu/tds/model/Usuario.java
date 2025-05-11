package umu.tds.model;

//Haría falta poner un atributo de premium para ver si el username es premium o no??
import java.time.LocalDate;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Clase que modela los usuarios de AppChat.
 */
public class Usuario {
	public static final String DEFAULT_SALUDO = "Hey there! I'm using AppChat";

	/* Atributos */
	private String phone;
	private char[] password;
	private String name;
	private LocalDate fechaNacim;
	private String imagenURL;
	private String saludo;
	private List<Contacto> contactos;
	private List<Mensaje> mensajes;

	private boolean premium;
	private LocalDate fechaRegistro;
	private int id;

	/* Constructores */
	/**
	 * Crea un username con una lista de contactos vacía.
	 *
	 * @param phone           número de teléfono.
	 * @param password        contraseña.
	 * @param name            nombre completo del usuario.
	 * @param fechaNacimiento fecha de nacimiento.
	 * @param imagenURL       URL de la imagen de perfil.
	 * @param saludo          texto de saludo (opcional).
	 */
	public Usuario(String phone, char[] password, String name, LocalDate fechaNacimiento, String imagenURL,
			String saludo, LocalDate fechaRegistro) {
		this.phone = phone;
		this.password = password;
		this.name = name;
		this.fechaNacim = fechaNacimiento;
		this.imagenURL = imagenURL;
		this.saludo = saludo;
		this.premium = false;
		this.fechaRegistro = fechaRegistro;

		this.contactos = new LinkedList<>();
	}

	/**
	 * Crea un usuario con una lista de contactos vacía y con un mensaje de saludo
	 * por defecto.
	 *
	 * @param telefono        número de teléfono.
	 * @param password        contraseña.
	 * @param name            nombre completo del usuario.
	 * @param fechaNacimiento fecha de nacimiento.
	 * @param imagenURL       URL de la imagen de perfil.
	 */
	public Usuario(String telefono, char[] password, String name, LocalDate fechaNacimiento, String imagenURL,
			LocalDate fechaRegistro) {
		this(telefono, password, name, fechaNacimiento, imagenURL, DEFAULT_SALUDO, fechaRegistro);
	}

	/* Consulta */
	public String getPhone() {
		return phone;
	}

	public void setPhone(String telefono) {
		this.phone = telefono;
	}

	public char[] getPassword() {
		return password;
	}

	public void setPassword(char[] password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getFechaNacimiento() {
		return fechaNacim;
	}

	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacim = fechaNacimiento;
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

	public boolean isPremium() {
		return premium;
	}

	public void setPremium(boolean premium) {
		this.premium = premium;
	}

	public LocalDate getFechaRegistro() {
		return fechaRegistro;
	}

	public int getId() {
		return id;
	}

	public void setId(int codigo) {
		this.id = codigo;
	}

	public void setMensajes(List<Mensaje> mensajes) {
		this.mensajes = mensajes;
	}

	/* Métodos */
	// ¿Está contando todos los mensajes, tanto enviados como recibididos?
	/**
	 * Obtiene el número de mensajes enviados por el usuario en el último mes. El
	 * último mes no se refiere sólo al mes acutal, sino a los últimos 30 o 31 días,
	 * dependiendo del mes.
	 *
	 * @return número de mensajes enviados.
	 */
	public int getMensajesEnviadosUltimoMes() {
		int nMensajesSent = 0;
		LocalDate fechaUltimoMes = LocalDate.now().minusMonths(1);
		for (Contacto contacto : contactos) {
			nMensajesSent += contacto.contarMensajesDesdeFecha(fechaUltimoMes);
		}

		return nMensajesSent;
	}

	/**
	 * Añade el contacto pasado como parámetro a la lista de contactos del usuario.
	 *
	 * @param contacto
	 */
	public void addContacto(Contacto contacto) {
		contactos.add(contacto);
	}

	/**
	 * Elimina el contacto pasado como parámetro de la lista de contactos del
	 * usuario.
	 *
	 * @param contacto
	 */
	public void deleteContacto(Contacto contacto) {
		contactos.remove(contacto);
	}

	public List<Contacto> getContactos() {
		return contactos;
	}

	public void setContactos(List<Contacto> contactos) {
		this.contactos = contactos;
	}

	public List<Mensaje> getMensajes() {
		return mensajes;
	}

	public void addMensaje(Mensaje mensaje) {
		mensajes.add(mensaje);
	}

	// Método para obtener mensajes con un contacto específico
	public List<Mensaje> getMensajesConContacto(Contacto contacto) {
		return contacto.getMensajes();
	}

	@Override
	public String toString() {
		return "Usuario [phone=" + phone + ", password=" + Arrays.toString(password) + ", name=" + name
				+ ", fechaNacim=" + fechaNacim + ", imagenURL=" + imagenURL + ", saludo=" + saludo + ", contactos="
				+ contactos + ", premium=" + premium + ", fechaRegistro=" + fechaRegistro + ", id=" + id + "]";
	}

}
