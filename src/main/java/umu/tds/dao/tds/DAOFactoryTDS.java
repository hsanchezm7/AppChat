package umu.tds.dao.tds;

import umu.tds.dao.AdaptadorContactoIndividualDAO;
import umu.tds.dao.AdaptadorGrupoDAO;
import umu.tds.dao.AdaptadorMensajeDAO;
import umu.tds.dao.AdaptadorUsuarioDAO;
import umu.tds.dao.DAOFactory;

public class DAOFactoryTDS extends DAOFactory {

	public DAOFactoryTDS() { }

	@Override
	public AdaptadorUsuarioDAO getUsuarioDAO() {
		return AdaptadorUsuarioTDS.getUnicaInstancia();
	}
	
	@Override
	public AdaptadorMensajeDAO getMensajeDAO() {
		return AdaptadorMensajeTDS.getUnicaInstancia();
	}
	
	@Override
	public AdaptadorGrupoDAO getGrupoDAO() {
		return AdaptadorGrupoTDS.getUnicaInstancia();
	}

	@Override
	public AdaptadorContactoIndividualDAO getContactoIndividualDAO() {
		return AdaptadorContactoIndividualTDS.getUnicaInstancia();
	}

	

	
}
