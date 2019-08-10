package org.tds.sgh.business;

import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;





public class Hotel implements Cloneable
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
	
	
	
	public Hotel(String nombre, String pais, String codigoHotel, String telefono, String direccion) {
		super();
		this.nombre = nombre;
		this.pais = pais;
		this.codigoHotel = codigoHotel;
		this.telefono = telefono;
		this.direccion = direccion;
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
		for (Map.Entry<String, Habitacion> entry : this.habitaciones.entrySet()) {
		    String key = entry.getKey();
		    Object value = entry.getValue();
		    if(((Reserva) value).getTipoHabitacion().getNombre() == tipoHabitacion.getNombre()) {
		    	
		    }
		}
		
		for (Map.Entry<String, Reserva> entry : this.reservas.entrySet()) {
		    String key = entry.getKey();
		    Object value = entry.getValue();
		    if(((Reserva) value).getEstado() == EstadoReserva.NoTomada) {  //ENUM
		    	
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
	
	public boolean tomarReserva(long idReserva, long idCliente){return false;}
	
	
	
	///////////////////////////////////////
	public Map<String, Reserva> buscarReservasPendientes() throws Exception{	
		
	
		HashMap<String, Reserva> reservasPendientes = null;
		
		for (Map.Entry<String, Reserva> entry : this.reservas.entrySet()) {
		    Reserva reserva = entry.getValue();
		    if(reserva.getEstado() == EstadoReserva.Pendiente) {  //ENUM
		    	
		    	reservasPendientes.put(reserva.getCodigoReserva(), reserva);
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
