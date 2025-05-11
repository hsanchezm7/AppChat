package umu.tds.model;

public class ContactoIndividual extends Contacto {

	/* Atributos */
	private String movil;
	private Usuario usuario;
	private boolean añadidoManualmente; // Nuevo campo para saber si fue añadido manualmente

	/* Constructor */
	public ContactoIndividual(String nombre, String movil, Usuario usuario) {
		super(nombre);
		this.movil = movil;
		this.usuario = usuario;
		this.añadidoManualmente = false;
	}

	/**
	 * Constructor para un contacto individual con el flag de añadido manualmente.
	 *
	 * @param nombre             El nombre asignado al contacto
	 * @param phone              El número de teléfono del contacto
	 * @param usuario            El usuario asociado al contacto
	 * @param añadidoManualmente Si fue añadido manualmente o no
	 */
	public ContactoIndividual(String nombre, String movil, Usuario usuario, boolean añadidoManualmente) {
		super(nombre);
		this.movil = movil;
		this.usuario = usuario;
		this.añadidoManualmente = añadidoManualmente;
	}

	/* Consulta */
	public String getMovil() {
		return movil;
	}

	public void setMovil(String movil) {
		this.movil = movil;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public boolean isAñadidoManualmente() {
		return añadidoManualmente;
	}

	public void setAñadidoManualmente(boolean añadidoManualmente) {
		this.añadidoManualmente = añadidoManualmente;
	}

}
