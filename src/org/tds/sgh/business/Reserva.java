package org.tds.sgh.business;

import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.UUID;

import org.tds.sgh.system.Reserva;

public class Reserva
{
	// --------------------------------------------------------------------------------------------
	
	private enum EstadoReserva {
		Pendiente,
		Tomada,
		Finalizada,
		Cancelada,
		NoTomada
	}
	
	private long codigoReserva;
	
	private GregorianCalendar fechaInicio;
	
	private GregorianCalendar fechaFin;
	
	private Boolean modificablePorHuesped;
	
	private String estado;
	
	private Cliente cliente;
	
	private HashSet<Huesped> huespedes;
	
	private Habitacion habitacion;
	
	private Hotel hotel;
	
	
	
	// --------------------------------------------------------------------------------------------
	
	public Reserva(GregorianCalendar fechaInicio, GregorianCalendar fechaFin, Boolean modificablePorHuesped, Cliente cliente, Hotel hotel)
	{
		this.codigoReserva = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
		
		this.fechaInicio = fechaInicio;
		
		this.fechaFin = fechaFin;
		
		this.modificablePorHuesped = modificablePorHuesped;
		
		this.estado = EstadoReserva.Pendiente;
		
		this.cliente = cliente;
		
		this.huespedes = new HashSet<Huesped>();
		
		this.habitacion = new Habitacion();
		
		this.hotel = hotel;
	}
	
	// --------------------------------------------------------------------------------------------
	
	public Reserva asociarHuesped(String nombreHuesped, String documento) {
		Huesped huesped = new Huesped(nombreHuesped, documento);
		this.huespedes.add(huesped);
		return this;
	}
	
	public void cambiarEstadoTomada() {
		this.estado = EstadoReserva.Tomada;
	}
	
	public String getEstado() {
		return this.estado;
	}
	
	public void asignarHabitacion(Habitacion habitacion) {
		this.habitacion = habitacion;
	}
	
}
