package umu.tds.model;

public class ContactoIndividual extends Contacto {

	/* Atributos */
	private String movil;
	private Usuario usuario;

	/* Constructor */
	public ContactoIndividual(String nombre, String movil, Usuario usuario) {
		super(nombre);
		this.movil = movil;
		this.usuario = usuario;
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

}
