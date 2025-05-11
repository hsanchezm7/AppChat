package umu.tds.model;

import java.time.LocalDate;

import umu.tds.controlador.AppChat;

public class DescuentoFecha implements EstrategiaDescuento {

	private static final double DESCUENTO_PORCIEN = 25.0;

	private LocalDate fechaInicio;
	private LocalDate fechaFin;

	public DescuentoFecha(LocalDate inicio, LocalDate fin) {
		this.fechaInicio = inicio;
		this.fechaFin = fin;
	}

	@Override
	public double calcularDescuento(Usuario usuario) {
		LocalDate fechaRegistro = usuario.getFechaRegistro();
		double original = AppChat.getInstance().getPrecioBaseAppchatPremium();

		if ((fechaRegistro.isEqual(fechaInicio) || fechaRegistro.isAfter(fechaInicio))
				&& (fechaRegistro.isEqual(fechaFin) || fechaRegistro.isBefore(fechaFin))) {

			double descuento = (DESCUENTO_PORCIEN / 100) * original;

			return original - descuento;
		}

		return original;
	}

}
