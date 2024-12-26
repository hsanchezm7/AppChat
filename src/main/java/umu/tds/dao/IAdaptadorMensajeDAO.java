package umu.tds.dao;

import java.util.List;

import umu.tds.model.Mensaje;

public interface IAdaptadorMensajeDAO {
	
	public void registrarMensaje(Mensaje mensaje);
	public void borrarMensaje(Mensaje mensaje);
	public void modificarMensaje(Mensaje mensaje);
	public Mensaje recuperarMensaje(int codigo);
	public List<Mensaje> recuperarTodosMensajes();
}
