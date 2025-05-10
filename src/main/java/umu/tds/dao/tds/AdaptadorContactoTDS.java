package umu.tds.dao.tds;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import beans.Entidad;
import beans.Propiedad;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import umu.tds.dao.AdaptadorContactoDAO;
import umu.tds.dao.AdaptadorContactoIndividualDAO;
import umu.tds.dao.AdaptadorGrupoDAO;
import umu.tds.dao.AdaptadorUsuarioDAO;
import umu.tds.dao.DAOFactory;
import umu.tds.model.Contacto;
import umu.tds.model.ContactoIndividual;
import umu.tds.model.Grupo;
import umu.tds.model.Mensaje;
import umu.tds.model.Usuario;

public class AdaptadorContactoTDS implements AdaptadorContactoDAO {
		
		// public static final String ENTITY_TYPE = "Contacto";
		
		private static AdaptadorContactoTDS unicaInstancia = null;
		
		private static ServicioPersistencia servPersistencia;
		
		private static AdaptadorGrupoDAO adapterG;
		private static AdaptadorContactoIndividualDAO adapterCI;
		
		private AdaptadorContactoTDS() {
			servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
			
			adapterG = DAOFactory.getInstance().getGrupoDAO();
			adapterCI = DAOFactory.getInstance().getContactoIndividualDAO();
		}
		
		public static AdaptadorContactoTDS getInstance() {
			
			if (unicaInstancia == null) {
				unicaInstancia = new AdaptadorContactoTDS();
				return unicaInstancia;
			}
			else {
				return unicaInstancia;
			}
			
		}
		
		@Override
		public void registrarContacto(Contacto contacto) {
			if (contacto instanceof ContactoIndividual) {
                adapterCI.registrarContactoIndividual((ContactoIndividual) contacto);
            } else if (contacto instanceof Grupo) {
            	adapterG.registrarGrupo((Grupo) contacto);
            }
		}
		
		@Override
		public void borrarContacto(Contacto contacto) {
			if (contacto instanceof ContactoIndividual) {
                adapterCI.borrarContactoIndividual((ContactoIndividual) contacto);
            } else if (contacto instanceof Grupo) {
            	adapterG.borrarGrupo((Grupo) contacto);
            }
		}
		
		@Override
		public void modificarContacto(Contacto contacto) {
			if (contacto instanceof ContactoIndividual) {
                adapterCI.modificarContactoIndividual((ContactoIndividual) contacto);
            } else if (contacto instanceof Grupo) {
            	adapterG.modificarGrupo((Grupo) contacto);
            }
		}
		
		@Override
		public Contacto recuperarContacto(int id) {
			Entidad entContacto = servPersistencia.recuperarEntidad(id);
			
			if (entContacto.getNombre().equals(AdaptadorContactoIndividualTDS.ENTITY_TYPE)) {
				return adapterCI.recuperarContactoIndividual(id);
            } else if (entContacto.getNombre().equals(AdaptadorGrupoTDS.ENTITY_TYPE)) {
            	return adapterG.recuperarGrupo(id);
            }
			
			return null;

		}

	}