package umu.tds.model;

import umu.tds.controlador.AppChat;

public class DescuentoMensajes implements EstrategiaDescuento {
	
	private static final double DESCUENTO_PORCIEN = 50.0;
	private int minimoMensajes;
	
	public DescuentoMensajes(int minimo) {
		this.minimoMensajes = minimo;
	}

	@Override
	public double calcularDescuento(Usuario usuario) {
		int mensajesUltimoMes = usuario.getMensajesEnviadosUltimoMes();
		double original = AppChat.getInstance().getPrecioBaseAppchatPremium();
		
		if (mensajesUltimoMes >= minimoMensajes) {
			double descuento = (DESCUENTO_PORCIEN / 100) * original;
			
			return original - descuento;
		}
		
		return original;
	}

}
