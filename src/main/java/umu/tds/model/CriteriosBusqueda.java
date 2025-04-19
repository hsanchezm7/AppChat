package umu.tds.model;

import java.util.function.Predicate;

//Clase que encapsula los criterios de búsqueda
public class CriteriosBusqueda {
	private String texto;
	private String telefono;
	private String nombreContacto;

	public CriteriosBusqueda(String texto, String telefono, String nombreContacto) {
		this.texto = texto;
		this.telefono = telefono;
		this.nombreContacto = nombreContacto;
	}

	public Predicate<Mensaje> toPredicate() {
		return mensaje -> {
			boolean cumple = true;

			if (texto != null && !texto.isEmpty()) {
				cumple = mensaje.getTexto().toLowerCase().contains(texto.toLowerCase());
			}

			if (cumple && telefono != null && !telefono.isEmpty()) {
				cumple = coincideTelefono(mensaje, telefono);
			}

			if (cumple && nombreContacto != null && !nombreContacto.isEmpty()) {
				cumple = coincideNombre(mensaje, nombreContacto);
			}

			return cumple;
		};
	}

	private boolean coincideTelefono(Mensaje mensaje, String telefono) {
		boolean emisorCoincide = mensaje.getEmisor().getPhone().equals(telefono);
		boolean receptorCoincide = false;

		if (mensaje.getReceptor() instanceof ContactoIndividual) {
			ContactoIndividual contacto = (ContactoIndividual) mensaje.getReceptor();
			receptorCoincide = contacto.getMovil().equals(telefono);
		}

		return emisorCoincide || receptorCoincide;
	}

	private boolean coincideNombre(Mensaje mensaje, String nombre) {
		boolean emisorCoincide = mensaje.getEmisor().getName().toLowerCase().contains(nombre.toLowerCase());
		boolean receptorCoincide = mensaje.getReceptor().getNombre().toLowerCase().contains(nombre.toLowerCase());

		return emisorCoincide || receptorCoincide;
	}

	public boolean isVacio() {
		return (texto == null || texto.isEmpty()) && (telefono == null || telefono.isEmpty())
				&& (nombreContacto == null || nombreContacto.isEmpty());
	}
}