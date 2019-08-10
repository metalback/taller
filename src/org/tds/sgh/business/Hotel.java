package org.tds.sgh.business;

import java.util.GregorianCalendar;
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
	private Map<String, Reserva> reservas;
	private Map<String, Habitacion> habitaciones;
	

	
	
	
	// --------------------------------------------------------------------------------------------
	
	public Hotel(String nombre, String pais, String codigoHotel, String telefono, String direccion,
			Map<String, org.tds.sgh.business.Reserva> reservas,
			Map<String, org.tds.sgh.business.Habitacion> habitaciones) {
		this.nombre = nombre;
		this.pais = pais;
		this.codigoHotel = codigoHotel;
		this.telefono = telefono;
		this.direccion = direccion;
		this.reservas = reservas;
		this.habitaciones = habitaciones;
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
		Reserva reserva = new Reserva(fechaInicio, fechaFin, modificableHuesped,cliente,this);
		this.reservas.put(reserva.getCodigoReserva(), reserva);
		return reserva;
	}
	
	
	public void agregarReserva(Reserva reserva){}
	
	public boolean confirmarDisponibilidad(TipoHabitacion tipoHabitacion ,GregorianCalendar fechaInicio, GregorianCalendar fechaFin){
		for (Map.Entry<String, Habitacion> entry : this.habitaciones.entrySet()) {
		    String key = entry.getKey();
		    Object value = entry.getValue();
		    if(value.getTipoHabitacion().getNombre() == tipoHabitacion.getNombre()) {
		    	
		    }
		}
		
		for (Map.Entry<String, Reserva> entry : this.reservas.entrySet()) {
		    String key = entry.getKey();
		    Object value = entry.getValue();
		    if(value.getEstado() == EstadoReserva.NoTomada) {  //ENUM
		    	
		    }
		}
		
		//int cantRes = 
		
		
		return false;
	}
	
	public boolean estaEnPais(String pais){
		if(this.pais == pais) {
			return true;
		}
		return false;
	}
	
	public Reserva tomarReserva(String codigoReserva, String rut){
	    Reserva reserva = this.reservas.get(codigoReserva);
	    
	    for (Map.Entry<String, Habitacion> entry : this.habitaciones.entrySet()) {
	    	boolean disponible = true;
	        Habitacion habitacionHotel = entry.getValue();
	        TipoHabitacion tipoHabitacionHotel = habitacionHotel.getTipoHabitacion();
	        for (Map.Entry<String, Reserva> entry_reserva : this.reservas.entrySet()) {
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
	public Map<String, Reserva> buscarReservasPendientes(){	
		
	
		HashSet<Reserva> reservasPendientes;
		
		for (Map.Entry<String, Reserva> entry : this.reservas.entrySet()) {
		    String key = entry.getKey();
		    Object value = entry.getValue();
		    if(value.getEstado() == EstadoReserva.Pendiente) {  //ENUM
		    	reservasPendientes.reservas.put(value.getCodigoReserva(), value);
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

	public Map<String, Reserva> getReservas() {
		return reservas;
	}

	public void setReservas(Map<String, Reserva> reservas) {
		this.reservas = reservas;
	}

	public Map<String, Habitacion> getHabitaciones() {
		return habitaciones;
	}

	public void setHabitaciones(Map<String, Habitacion> habitaciones) {
		this.habitaciones = habitaciones;
	}
	

}
