package umu.tds.dao;

import umu.tds.model.Contacto;

public interface AdaptadorContactoDAO {

	public void registrarContacto(Contacto contacto);

	public void borrarContacto(Contacto contacto);

	public void modificarContacto(Contacto contacto);

	public Contacto recuperarContacto(int codigo);

}