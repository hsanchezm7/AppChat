package umu.tds.dao;

import umu.tds.model.Usuario;

public interface IAdaptadorUsuarioDAO {
	
	public void registrarUsuario(Usuario usuario);
	public void borrarUsuario(Usuario usuario);
	public void modificarUsuario(Usuario usuario);
	public Usuario recuperarUsuario(int codigo);
	

}
