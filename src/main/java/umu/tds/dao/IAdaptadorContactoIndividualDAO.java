package umu.tds.dao;

import umu.tds.model.ContactoIndividual;

public interface IAdaptadorContactoIndividualDAO {
	
	public void registrarContactoIndividual(ContactoIndividual contactoIndividual);
	public void borrarContactoIndividual(ContactoIndividual contactoIndividual);
	public void modificarContactoIndividual(ContactoIndividual contactoIndividual);
	public ContactoIndividual recuperarContactoIndividual(int codigo);
	
}
