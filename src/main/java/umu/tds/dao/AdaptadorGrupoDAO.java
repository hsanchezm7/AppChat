package umu.tds.dao;

import umu.tds.model.Grupo;

public interface AdaptadorGrupoDAO {

	public void registrarGrupo(Grupo grupo);

	public void borrarGrupo(Grupo grupo);

	public void modificarGrupo(Grupo grupo);

	public Grupo recuperarGrupo(int codigo);

}
