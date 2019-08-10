package org.tds.sgh.business;

import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.UUID;

import org.tds.sgh.system.Reserva;

public class Reserva
{
	// --------------------------------------------------------------------------------------------
	private long codigoReserva;
	
	private GregorianCalendar fechaInicio;
	
	private GregorianCalendar fechaFin;
	
	private Boolean modificablePorHuesped;
	
	private EstadoReserva estado;
	
	private Cliente cliente;
	
	private Map<String, Huesped> huespedes;
	
	private Habitacion habitacion;
	
	private TipoHabitacion tipoHabitacion;
	
	private Hotel hotel;
	
	
	
	// --------------------------------------------------------------------------------------------
	
	public Reserva(GregorianCalendar fechaInicio, GregorianCalendar fechaFin, Boolean modificablePorHuesped, Cliente cliente, Hotel hotel, TipoHabitacion tipoHabitacion)
	{
		this.codigoReserva = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
		
		this.fechaInicio = fechaInicio;
		
		this.fechaFin = fechaFin;
		
		this.modificablePorHuesped = modificablePorHuesped;
		
		this.estado = EstadoReserva.Pendiente;
		
		this.cliente = cliente;
		
		this.huespedes = new HashMap<String, Huesped()>;
		
		this.habitacion = new Habitacion();
		
		this.tipoHabitacion = tipoHabitacion;
		
		this.hotel = hotel;
	}
	
	// --------------------------------------------------------------------------------------------
	
	public Reserva asociarHuesped(String nombreHuesped, String documento) {
		if (this.huespedes.containsKey(documento))
		{
			throw new Exception("Ya existe un huesped con el documento indicado.");
		}
		
		Huesped huesped = new Huesped(nombreHuesped, documento);
		this.huespedes.put(documento, huesped);
		return this;
	}
	
	public void cambiarEstadoTomada() {
		this.estado = EstadoReserva.Tomada;
	}
	
	public EstadoReserva getEstado() {
		return this.estado;
	}
	
	public void asignarHabitacion(Habitacion habitacion) {
		this.habitacion = habitacion;
	}
	
}
