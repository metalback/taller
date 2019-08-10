package org.tds.sgh.business;

import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.tds.sgh.*;
import org.tds.sgh.infrastructure.Infrastructure;




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
		
		this.reservas.put(reserva.getCodigoReserva(), reserva);
		return reserva;
	}
	
	
	public void agregarReserva(Reserva reserva){}
	
	public boolean confirmarDisponibilidad(TipoHabitacion tipoHabitacion ,GregorianCalendar fechaInicio, GregorianCalendar fechaFin){
		return true;
	}
	
	public boolean estaEnPais(String pais){
		if(this.pais == pais) {
			return true;
		}
		return false;
	}
	
	public Reserva tomarReserva(Long codigoReserva, String rut) throws Exception{
	    Reserva reserva = this.reservas.get(codigoReserva);
	    
	    for (Map.Entry<String, Habitacion> entry : this.habitaciones.entrySet()) {
	    	boolean disponible = true;
	        Habitacion habitacionHotel = entry.getValue();
	        TipoHabitacion tipoHabitacionHotel = habitacionHotel.getTipoHabitacion();
	        for (Map.Entry<Long, Reserva> entry_reserva : this.reservas.entrySet()) {
	            Reserva reserva_aux = entry_reserva.getValue();
	            if(reserva_aux.getTipoHabitacion().getNombre().equals(tipoHabitacionHotel.getNombre())) {
	            	if(reserva_aux.getEstado() == EstadoReserva.Tomada){
	            		if(reserva_aux.getHabitacion().getNombre().equals(habitacionHotel.getNombre())) {
	            			disponible = false;
	            			break;
	            		}
	            	}
	            }
	        }
	        
	        if(disponible){
	        	reserva.asignarHabitacion(habitacionHotel);
	        	reserva.cambiarEstadoTomada();
	        	return reserva;
	        }
	    }
	    throw new Exception("El hotel no cuenta con habitaciones disponibles para asignar a la reserva.");
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
		
		if(reservasPendientes.size() <= 0 || reservasPendientes == null) {
			throw new Exception("Ya existe un huesped con el documento indicado.");
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

	
	

}
