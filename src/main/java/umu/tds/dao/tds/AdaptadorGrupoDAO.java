package umu.tds.dao.tds;

import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;

import umu.tds.model.Grupo;

public class AdaptadorGrupoDAO implements umu.tds.dao.IAdaptadorGrupoDAO {
	
	private static AdaptadorGrupoDAO unicaInstancia = null;
	private static ServicioPersistencia servPersistencia;
	
	private AdaptadorGrupoDAO() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}
	
	public static AdaptadorGrupoDAO getUnicaInstancia() {
		
		if (unicaInstancia == null) {
			return new AdaptadorGrupoDAO();
		}
		else {
			return unicaInstancia;
		}
		
	}
	
	
	public void registrarGrupo(Grupo grupo) {
		
		
		
	}
	
	public void borrarGrupo(Grupo grupo) {
		
		
		
	}
	
	public void modificarGrupo(Grupo grupo) {
		
		
		
	}
	
	public Grupo recuperarGrupo(int codigo) {
		
		
		
	}

}
