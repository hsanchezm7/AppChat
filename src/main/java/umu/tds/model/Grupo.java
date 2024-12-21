package umu.tds.model;

import java.util.List;

public class Grupo extends Contacto {
	
	/* Atributos */
	// Puede haber más de 1 administrador? Si no, final, no?
	private Usuario administrador;
	private List<Usuario> miembros;
	private String imagenGrupoURL;
	
	/* Constructor */
	public Grupo(String nombre, Usuario administrador, List<Usuario> miembros, String imagenGrupoURL) {
		super(nombre);
		this.administrador = administrador;
		this.miembros = miembros;
		this.imagenGrupoURL = imagenGrupoURL;
	}
	
}
