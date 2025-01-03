package umu.tds.model;

import java.util.List;

public class Grupo extends Contacto {
	
	/* Atributos */
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

	public Usuario getAdministrador() {
		return administrador;
	}

	public void setAdministrador(Usuario administrador) {
		this.administrador = administrador;
	}

	// ¿Usuario o contacto?
	public List<Usuario> getMiembros() {
		return miembros;
	}

	public void setMiembros(List<Usuario> miembros) {
		this.miembros = miembros;
	}

	public String getImagenGrupoURL() {
		return imagenGrupoURL;
	}

	public void setImagenGrupoURL(String imagenGrupoURL) {
		this.imagenGrupoURL = imagenGrupoURL;
	}
	
}
