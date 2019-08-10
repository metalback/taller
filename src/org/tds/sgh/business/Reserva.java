package org.tds.sgh.business;

import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.tds.sgh.infrastructure.Infrastructure;



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
		
		this.huespedes = new HashMap<String, Huesped>();
		
		this.habitacion = new Habitacion();
		
		this.tipoHabitacion = tipoHabitacion;
		
		this.hotel = hotel;
	}
	
	// --------------------------------------------------------------------------------------------
	
	
	public void cambiarEstadoTomada() {
		this.estado = EstadoReserva.Tomada;
	}
	public void asignarHabitacion(Habitacion habitacion) {
		this.habitacion = habitacion;
	}
	
	public Reserva actualizarReserva(TipoHabitacion tipoHabitacion, GregorianCalendar fechaInicio, GregorianCalendar fechaFin, Boolean modificablePorCliente) {
		return this;
	}

	public boolean coincide(TipoHabitacion tipoHabitacion, GregorianCalendar fechaInicio, GregorianCalendar fechaFin) {
		return this.tipoHabitacion == tipoHabitacion && 
			!(Infrastructure.getInstance().getCalendario().esAnterior(fechaFin, this.fechaInicio) && 
			Infrastructure.getInstance().getCalendario().esPosterior(this.fechaFin, fechaInicio));
	}
	
	public void asociarHuesped(String nombre, String documento){
		this.huespedes.put(documento, new Huesped(nombre, documento));
	}
	
	// --------------------------------------------------------------------------------------------
	
	
	public EstadoReserva getEstado() {
		return this.estado;
	}
	

	public void setCodigoReserva(long codigoReserva) {
		this.codigoReserva = codigoReserva;
	}

	public Boolean getModificablePorHuesped() {
		return this.modificablePorHuesped;
	}
	
	public void setModificablePorHuesped(Boolean modificablePorHuesped) {
		this.modificablePorHuesped = modificablePorHuesped;
	}

	public Collection<Huesped> getHuespedes() {
		return this.huespedes.values();
	}

	public void setHuespedes(Map<String, Huesped> huespedes) {
		this.huespedes = huespedes;
	}

	public TipoHabitacion getTipoHabitacion() {
		return this.tipoHabitacion;
	}

	public void setTipoHabitacion(TipoHabitacion tipoHabitacion) {
		this.tipoHabitacion = tipoHabitacion;
	}
	// --------------------------------------------------------------------------------------------

	public long getCodigoReserva() {
		return codigoReserva;
	}


	public GregorianCalendar getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(GregorianCalendar fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public GregorianCalendar getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(GregorianCalendar fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Habitacion getHabitacion() {
		return habitacion;
	}

	public void setHabitacion(Habitacion habitacion) {
		this.habitacion = habitacion;
	}

	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}

	public void setEstado(EstadoReserva estado) {
		this.estado = estado;
	}
	
	
}
