package umu.tds.dao.tds;

import beans.Entidad;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import umu.tds.dao.AdaptadorGrupoDAO;
import umu.tds.model.Grupo;

public class AdaptadorGrupoTDS implements AdaptadorGrupoDAO {
	
	private static AdaptadorGrupoTDS unicaInstancia = null;
	private static ServicioPersistencia servPersistencia;
	
	private AdaptadorGrupoTDS() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}
	
	public static AdaptadorGrupoTDS getInstance() {
		
		if (unicaInstancia == null) {
			return new AdaptadorGrupoTDS();
		}
		else {
			return unicaInstancia;
		}
		
	}
	
	
	public void registrarGrupo(Grupo grupo) {
		
		Entidad entGrupo;
		
		if (servPersistencia.recuperarEntidad(grupo.getId()) != null)
			return;
		
	}
	
	public void borrarGrupo(Grupo grupo) {
		
		
		
	}
	
	public void modificarGrupo(Grupo grupo) {
		
		
		
	}
	
	public Grupo recuperarGrupo(int id) {
		
	}

}
