package org.tds.sgh.business;

import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.tds.sgh.system.Reserva;


public class CadenaHotelera
{
	// --------------------------------------------------------------------------------------------
	
	private Map<String, Cliente> clientes;
	
	private Map<String, Hotel> hoteles;
	
	private String nombre;
	
	private Map<String, TipoHabitacion> tiposHabitacion;
	
	// --------------------------------------------------------------------------------------------
	
	public CadenaHotelera(String nombre)
	{
		this.clientes = new HashMap<String, Cliente>();
		
		this.hoteles = new HashMap<String, Hotel>();
		
		this.nombre = nombre;
		
		this.tiposHabitacion = new HashMap<String, TipoHabitacion>();
	}
	
	// --------------------------------------------------------------------------------------------
	
	public Cliente agregarCliente(
		String rut,
		String nombre,
		String direccion,
		String telefono,
		String mail) throws Exception
	{
		if (this.clientes.containsKey(rut))
		{
			throw new Exception("Ya existe un cliente con el RUT indicado.");
		}
		
		Cliente cliente = new Cliente(rut, nombre, direccion, telefono, mail);
		
		this.clientes.put(cliente.getRut(), cliente);
		
		return cliente;
	}
	
	public Hotel agregarHotel(String nombre, String pais) throws Exception
	{
		if (this.hoteles.containsKey(nombre))
		{
			throw new Exception("Ya existe un hotel con el nombre indicado.");
		}
		
		Hotel hotel = new Hotel(nombre, pais);
		
		this.hoteles.put(hotel.getNombre(), hotel);
		
		return hotel;
	} 
	
	public TipoHabitacion agregarTipoHabitacion(String nombre) throws Exception
	{
		if (this.tiposHabitacion.containsKey(nombre))
		{
			throw new Exception("Ya existe un tipo de habitación con el nombre indicado.");
		}
		
		TipoHabitacion tipoHabitacion = new TipoHabitacion(nombre);
		
		this.tiposHabitacion.put(tipoHabitacion.getNombre(), tipoHabitacion);
		
		return tipoHabitacion;
	}
	
	public Cliente buscarCliente(String rut) throws Exception
	{
		Cliente cliente = this.clientes.get(rut);
		
		if (cliente == null)
		{
			throw new Exception("No existe un cliente con el nombre indicado.");
		}
		
		return cliente;
	}
	
	public Set<Cliente> buscarClientes(String patronNombreCliente)
	{
		Set<Cliente> clientesEncontrados = new HashSet<Cliente>();
		
		for (Cliente cliente : this.clientes.values())
		{
			if (cliente.coincideElNombre(patronNombreCliente))
			{
				clientesEncontrados.add(cliente);
			}
		}
		
		return clientesEncontrados;
	}
	
	public Hotel buscarHotel(String nombre) throws Exception
	{
		Hotel hotel = this.hoteles.get(nombre);
		
		if (hotel == null)
		{
			throw new Exception("No existe un hotel con el nombre indicado.");
		}
		
		return hotel;
	}
	
	public TipoHabitacion buscarTipoHabitacion(String nombre) throws Exception
	{
		TipoHabitacion tipoHabitacion = this.tiposHabitacion.get(nombre);
		
		if (tipoHabitacion == null)
		{
			throw new Exception("No existe un tipo de habitación con el nombre indicado.");
		}
		
		return tipoHabitacion;
	}
	
	public String getNombre()
	{
		return this.nombre;
	}
	
	public Set<Cliente> listarClientes()
	{
		return new HashSet<Cliente>(this.clientes.values());
	}
	
	public Set<Hotel> listarHoteles()
	{
		return new HashSet<Hotel>(this.hoteles.values());
	}
	
	public Set<TipoHabitacion> listarTiposHabitacion()
	{
		return new HashSet<TipoHabitacion>(this.tiposHabitacion.values());
	}

	public Cliente seleccionarCliente(String rut) {
		return this.clientes.get(rut);
	}

	public Boolean confirmarDisponibilidad(String nombreHotel, String nombreTipoHabitacion,
			GregorianCalendar fechaInicio, GregorianCalendar fechaFin) {
		TipoHabitacion tipoHabitacion = this.tiposHabitacion.get(nombreTipoHabitacion);
		Hotel hotel = this.hoteles.get(nombreHotel);
		Boolean disponilidad = hotel.confirmarDisponibilidad(tipoHabitacion, fechaInicio, fechaFin);
		return disponibilidad;
	}

	public Reserva registrarReserva(String nombreHotel, String nombreTipoHabitacion, GregorianCalendar fechaInicio,
			GregorianCalendar fechaFin, boolean modificablePorHuesped) {
		Hotel hotel = this.hoteles.get(nombreHotel);
		TipoHabitacion tipoHabitacion = this.tiposHabitacion.get(nombreTipoHabitacion);
		Cliente cliente = this.clientes.get(this.idCliente); // idCliente tengo que obtenerlo de alguna parte
		Reserva reserva =  hotel.crearReserva(cliente, tipoHabitacion, fechaInicio, fechaFin, modificablePorHuesped);
		return reserva;
	}

	public HashSet<Hotel> sugerirAlternativas(String pais, String nombreTipoHabitacion, GregorianCalendar fechaInicio,
			GregorianCalendar fechaFin) {
		TipoHabitacion tipoHabitacion = this.tiposHabitacion.get(nombreTipoHabitacion);
		int cont = 0;
		HashSet<Hotel> hoteles_sugeridos = new HashSet<Hotel>();
		
		for (Map.Entry<String, Hotel> entry : this.hoteles.entrySet()) {
		    String key = entry.getKey();
		    Hotel aux_hotel = entry.getValue();
		    if(aux_hotel.estaEnPais(pais)) {
				if(aux_hotel.confirmarDisponibilidad(tipoHabitacion, fechaInicio, fechaFin)) {
					hoteles_sugeridos.add(aux_hotel);
				}
			}
		}
		return hoteles_sugeridos;
	}

	public Map<String, Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(Map<String, Cliente> clientes) {
		this.clientes = clientes;
	}

	public Map<String, Hotel> getHoteles() {
		return hoteles;
	}

	public void setHoteles(Map<String, Hotel> hoteles) {
		this.hoteles = hoteles;
	}

	public Map<String, TipoHabitacion> getTiposHabitacion() {
		return tiposHabitacion;
	}

	public void setTiposHabitacion(Map<String, TipoHabitacion> tiposHabitacion) {
		this.tiposHabitacion = tiposHabitacion;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public HashSet<Reserva> buscarReservasPendientes(String nombreHotel) {
		Hotel hotel = this.hoteles.get(nombreHotel);
		HashSet<Reserva> reservas_pendientes = hotel.buscarReservasPendientes();
		return reservas_pendientes;
	}

	public Reserva seleccionarReserva(long codigoReserva, String rut) {
		Cliente cliente = this.seleccionarCliente(rut);
		return cliente.SeleccionarReserva(codigoReserva);
		
	}

	public Reserva registrarHuesped(String rut, String codigoReserva, String nombre, String documento) {
		Cliente cliente = this.buscarCliente(rut);
		return cliente.asociarHuesped(codigoReserva, nombre, documento);
	}
}
