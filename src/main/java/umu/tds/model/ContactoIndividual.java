package umu.tds.model;

public class ContactoIndividual extends Contacto {
	
	/* Atributos */
	private String movil;
	private int codigo;

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

	public int getId() {
		return codigo;
	}

	public void setId(int codigo) {
		this.codigo = codigo;
	}

	
	
	

}
