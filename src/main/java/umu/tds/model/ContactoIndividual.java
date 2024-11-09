package umu.tds.model;

public class ContactoIndividual extends Contacto{
	
	/* Atributos */
	private String movil;

	/* Constructor */
	public ContactoIndividual(String nombre, String movil) {
		super(nombre);
		this.movil = movil;
	}

	/* Consulta */
	public String getMovil() {
		return movil;
	}

	public void setMovil(String movil) {
		this.movil = movil;
	}

}
