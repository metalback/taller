package org.tds.sgh.business;

import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.tds.sgh.*;
import org.tds.sgh.infrastructure.Infrastructure;



@Entity
public class Hotel
{
	// --------------------------------------------------------------------------------------------
	
	
	
	private String nombre;
	private String pais;
	private String codigoHotel;
	private String telefono;
	private String direccion;
	private Map<Long, Reserva> reservas;
	private Map<String, Habitacion> habitaciones;
	

	// --------------------------------------------------------------------------------------------
	
	public Hotel(String nombre, String pais) {
		this.nombre = nombre;
		this.pais = pais;
		this.reservas = new HashMap<Long, Reserva>();
		this.habitaciones = new HashMap<String, Habitacion>();
	}

	// --------------------------------------------------------------------------------------------
	
	public Habitacion agregarHabitacion(TipoHabitacion tipoHabitacion, String nombre) throws Exception
	{
		if (this.habitaciones.containsKey(nombre))
		{
			throw new Exception("El hotel ya tiene una habitación con el nombre indicado.");
		}
		
		Habitacion habitacion = new Habitacion(tipoHabitacion, nombre);
		
		this.habitaciones.put(habitacion.getNombre(), habitacion);
		
		return habitacion;
	}
	
	
	public Set<Habitacion> listarHabitaciones()
	{
		return new HashSet<Habitacion>(this.habitaciones.values());
	}
	
	//////////////////////////////////////////
	
	public Reserva crearReserva(Cliente cliente,TipoHabitacion tipoHabitacion ,GregorianCalendar fechaInicio, GregorianCalendar fechaFin,Boolean modificableHuesped){
		Reserva reserva = new Reserva(fechaInicio, fechaFin, modificableHuesped,cliente,this,tipoHabitacion); 
		cliente.agregarReservaAColeccion(reserva);
		this.reservas.put(reserva.getCodigoReserva(), reserva);
		return reserva;
	}
	
	
	public void agregarReserva(Reserva reserva){}
	
	public void quitarReserva(Reserva reserva){
		this.reservas.remove(reserva.getCodigoReserva());
	}
	
	public boolean confirmarDisponibilidad(TipoHabitacion tipoHabitacion ,GregorianCalendar fechaInicio, GregorianCalendar fechaFin){
		int contarHabitacionMismoTipo = 0;
		int contarHabitacionConReserva = 0;
		for(Habitacion h: this.habitaciones.values()) {
			if(h.getTipoHabitacion().getNombre().equals(tipoHabitacion.getNombre())) {
				contarHabitacionMismoTipo++;
			}
		}
		
		for(Reserva r: this.reservas.values()) {
			if(r.coincide(tipoHabitacion, fechaInicio, fechaFin)) {
				contarHabitacionConReserva++;
			}
		}
	
		return contarHabitacionMismoTipo > contarHabitacionConReserva;
	}
	
	public boolean estaEnPais(String pais){
		if(this.pais == pais) {
			return true;
		}
		return false;
	}
	
	public Reserva tomarReserva(Long codigoReserva, String rut){
	    Reserva reserva = this.reservas.get(codigoReserva);
	    
	    Set<Habitacion> habitacionesP = new HashSet<Habitacion>();
	    
	    for(Habitacion h: this.habitaciones.values()) {
	    	if(h.getTipoHabitacion() == reserva.getTipoHabitacion()) {
	    		habitacionesP.add(h);
	    	}
	    }
	    
	    
	    for(Reserva r: this.reservas.values()) {
	    	if(r != reserva && r.getHabitacion() != null) {
	    		if(r.coincide(reserva.getTipoHabitacion(), reserva.getFechaInicio(), reserva.getFechaFin())) {
	    			habitacionesP.remove(r.getHabitacion());
	    		}
	    	}
	    }
	    
	    Habitacion hab = habitacionesP.stream().findFirst().get();
	    reserva.setHabitacion(hab);
	    reserva.cambiarEstadoTomada();
	    return reserva;
	}
	
	
	
	///////////////////////////////////////
	public HashSet<Reserva> buscarReservasPendientes() throws Exception{
		HashSet<Reserva> reservasPendientes = new HashSet<Reserva>();
		
		for (Map.Entry<Long, Reserva> entry : this.reservas.entrySet()) {
		    Reserva reserva = entry.getValue();
		    if(reserva.getEstado() == EstadoReserva.Pendiente) {  //ENUM
		    	
		    	reservasPendientes.add(reserva);
		    }
		}
		return reservasPendientes;
	
    }	
	/////////////////////////////////////////

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getCodigoHotel() {
		return codigoHotel;
	}

	public void setCodigoHotel(String codigoHotel) {
		this.codigoHotel = codigoHotel;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Reserva buscarReservas(Long codigoReserva) {
		return this.reservas.get(codigoReserva);
	}

	public boolean confirmarDisponibilidad(Reserva reserva, TipoHabitacion tipoHabitacion,
			GregorianCalendar fechaInicio, GregorianCalendar fechaFin) {
		int contarHabitacionMismoTipo = 0;
		int contarHabitacionConReserva = 0;
		for(Habitacion h: this.habitaciones.values()) {
			if(h.getTipoHabitacion().getNombre().equals(tipoHabitacion.getNombre())) {
				contarHabitacionMismoTipo++;
			}
		}
		
		for(Reserva r: this.reservas.values()) {
			if(r.coincide(tipoHabitacion, fechaInicio, fechaFin) && reserva != r) {
				contarHabitacionConReserva++;
			}
		}
	
		return contarHabitacionMismoTipo > contarHabitacionConReserva;
	}
	@OneToMany(cascade = CascadeType.ALL)
	public Map<Long, Reserva> getReservas() {
		return reservas;
	}

	public void setReservas(Map<Long, Reserva> reservas) {
		this.reservas = reservas;
	}
	@OneToMany(cascade = CascadeType.ALL)
	public Map<String, Habitacion> getHabitaciones() {
		return habitaciones;
	}

	public void setHabitaciones(Map<String, Habitacion> habitaciones) {
		this.habitaciones = habitaciones;
	}
	
}
