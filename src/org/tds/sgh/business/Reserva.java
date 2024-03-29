package org.tds.sgh.business;

import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.tds.sgh.infrastructure.Infrastructure;


@Entity
public class Reserva
{
	// --------------------------------------------------------------------------------------------
	
	private long id;
	
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
	
	public Reserva actualizarReserva(Hotel hotel, TipoHabitacion tipoHabitacion, GregorianCalendar fechaInicio, GregorianCalendar fechaFin, Boolean modificablePorCliente) {
		if(this.getHotel() != hotel) {
			this.getHotel().quitarReserva(this);
		}
		
		this.setTipoHabitacion(tipoHabitacion);
		this.setFechaInicio(fechaInicio);
		this.setFechaFin(fechaFin);
		this.setModificablePorHuesped(modificablePorCliente);
		this.setHotel(hotel);
		Infrastructure.getInstance().getSistemaMensajeria().enviarMail(this.getCliente().getMail(), "", "");
		return this;
	}

	public boolean coincide(TipoHabitacion tipoHabitacion, GregorianCalendar fechaInicio, GregorianCalendar fechaFin) {
		return this.tipoHabitacion == tipoHabitacion && 
			!(Infrastructure.getInstance().getCalendario().esAnterior(fechaFin, this.fechaInicio) || 
			Infrastructure.getInstance().getCalendario().esPosterior(fechaInicio, this.fechaFin) ||
			Infrastructure.getInstance().getCalendario().esMismoDia(fechaInicio, this.fechaFin) );
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
	@OneToMany(cascade = CascadeType.ALL)
	public Map<String, Huesped> getHuespedes() {
		return this.huespedes;
	}

	public void setHuespedes(Map<String, Huesped> huespedes) {
		this.huespedes = huespedes;
	}

	@ManyToOne
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

	@ManyToOne
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	@ManyToOne(cascade = CascadeType.ALL)
	public Habitacion getHabitacion() {
		return habitacion;
	}

	public void setHabitacion(Habitacion habitacion) {
		this.habitacion = habitacion;
	}
	@ManyToOne
	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}

	public void setEstado(EstadoReserva estado) {
		this.estado = estado;
	}

	public Reserva cancelar() {
		this.getCliente().quitarReserva(this);
		this.setEstado(EstadoReserva.Cancelada);
		return this;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	
	
	
}
