package umu.tds.dao;

import umu.tds.model.Grupo;

public interface IAdaptadorGrupoDAO {
	
	public void registrarGrupo(Grupo grupo);
	public void borrarGrupo(Grupo grupo);
	public void modificarGrupo(Grupo grupo);
	public Grupo recuperarGrupo(int codigo);

}
