package umu.tds.model;

public class DescuentoMensajes extends Descuento{
	
	private int mensajesMinimoNecesarios;
	private double porcentajeDescuento;

	public DescuentoMensajes(int mensajesMinimoNecesarios, double porcentajeDescuento) {
		this.mensajesMinimoNecesarios = mensajesMinimoNecesarios;
		this.porcentajeDescuento = porcentajeDescuento;
	}

	@Override
	public double calcularDescuento(Usuario usuario) {
		int mensajesUltimoMes = usuario.getMensajesEnviadosUltimoMes();
		if (mensajesUltimoMes >= mensajesMinimoNecesarios) {
			double precioOriginal = Usuario.getPrecioOriginal();
			double descuento = (porcentajeDescuento / 100) * precioOriginal;
			return precioOriginal - descuento;
		}
		return Usuario.getPrecioOriginal();
	}
	
	

}
