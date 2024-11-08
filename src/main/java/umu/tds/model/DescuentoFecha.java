package umu.tds.model;

import java.time.LocalDate;

public class DescuentoFecha extends Descuento{
	
	private LocalDate fechaIntervaloIncio;
	private LocalDate fechaIntervaloFin;
	private double porcentajeDescuento;

	public DescuentoFecha(LocalDate fechaIntervaloIncio, LocalDate fechaIntervaloFin, double porcentajeDescuento) {
		this.fechaIntervaloIncio = fechaIntervaloIncio;
		this.fechaIntervaloFin = fechaIntervaloFin;
		this.porcentajeDescuento = porcentajeDescuento;
	}


	//Aquí lo que no sé es si considera también el mismo día isAfter isBefore??
	@Override
	public double calcularDescuento(Usuario usuario) {
		LocalDate fechaRegistro = usuario.getFechaRegistro();
		if (fechaRegistro.isAfter(fechaIntervaloIncio) && fechaRegistro.isBefore(fechaIntervaloFin)) {
			double precioOriginal = Usuario.getPrecioOriginal();
			double descuento = (porcentajeDescuento / 100) * precioOriginal;
			return precioOriginal - descuento;
		}
		return Usuario.getPrecioOriginal();
	}
	
	

}
