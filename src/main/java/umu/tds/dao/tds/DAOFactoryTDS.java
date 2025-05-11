package umu.tds.dao.tds;

import umu.tds.dao.AdaptadorContactoDAO;
import umu.tds.dao.AdaptadorContactoIndividualDAO;
import umu.tds.dao.AdaptadorGrupoDAO;
import umu.tds.dao.AdaptadorMensajeDAO;
import umu.tds.dao.AdaptadorUsuarioDAO;
import umu.tds.dao.DAOFactory;

public class DAOFactoryTDS extends DAOFactory {

	public DAOFactoryTDS() {
	}

	@Override
	public AdaptadorUsuarioDAO getUsuarioDAO() {
		return AdaptadorUsuarioTDS.getInstance();
	}

	@Override
	public AdaptadorMensajeDAO getMensajeDAO() {
		return AdaptadorMensajeTDS.getUnicaInstancia();
	}

	@Override
	public AdaptadorContactoDAO getContactoDAO() {
		return AdaptadorContactoTDS.getInstance();
	}

	@Override
	public AdaptadorGrupoDAO getGrupoDAO() {
		return AdaptadorGrupoTDS.getInstance();
	}

	@Override
	public AdaptadorContactoIndividualDAO getContactoIndividualDAO() {
		return AdaptadorContactoIndividualTDS.getInstance();
	}

}
