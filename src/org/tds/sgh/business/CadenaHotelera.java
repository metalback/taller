package org.tds.sgh.business;

import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.tds.sgh.infrastructure.Infrastructure;

@Entity
public class CadenaHotelera
{
	// --------------------------------------------------------------------------------------------
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@OneToMany
	private Map<String, Cliente> clientes;
	
	@OneToMany
	private Map<String, Hotel> hoteles;
	
	private String nombre;
	
	@OneToMany
	private Map<String, TipoHabitacion> tiposHabitacion;
	
	private Cliente cliente;
	
	private Hotel hotel;
	
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
		
		this.cliente = cliente;
		
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
		this.cliente = this.clientes.get(rut);
		return this.cliente;
	}

	public boolean confirmarDisponibilidad(String nombreHotel, String nombreTipoHabitacion,
			GregorianCalendar fechaInicio, GregorianCalendar fechaFin) throws Exception {
		
		if(this.tiposHabitacion.isEmpty() || this.tiposHabitacion.get(nombreTipoHabitacion) == null) {
			throw new Exception("No existe el tipo de habitación solicitado.");
		}

		if(Infrastructure.getInstance().getCalendario().esPasada(fechaInicio)) {
			throw new Exception("La fecha indicada es pasada");
		}
		
		if(Infrastructure.getInstance().getCalendario().esPosterior(fechaInicio, fechaFin)) {
			throw new Exception("La fecha de inicio es superior a la fecha de fin");
		}
		
		TipoHabitacion tipoHabitacion = this.tiposHabitacion.get(nombreTipoHabitacion);
		Hotel hotel = this.hoteles.get(nombreHotel);
		return hotel.confirmarDisponibilidad(tipoHabitacion, fechaInicio, fechaFin);
	}

	public Reserva registrarReserva(String nombreHotel, String nombreTipoHabitacion, GregorianCalendar fechaInicio,
			GregorianCalendar fechaFin, boolean modificablePorHuesped) {
		Hotel hotel = this.hoteles.get(nombreHotel);
		TipoHabitacion tipoHabitacion = this.tiposHabitacion.get(nombreTipoHabitacion);
		Cliente cliente = this.clientes.get(this.cliente.getRut());
		Reserva reserva =  hotel.crearReserva(cliente, tipoHabitacion, fechaInicio, fechaFin, modificablePorHuesped);
		Infrastructure.getInstance().getSistemaMensajeria().enviarMail(cliente.getMail(), "", "");
		return reserva;
	}

	public HashSet<Hotel> sugerirAlternativas(String pais, String nombreTipoHabitacion, GregorianCalendar fechaInicio,
			GregorianCalendar fechaFin) throws Exception {
		TipoHabitacion tipoHabitacion = this.tiposHabitacion.get(nombreTipoHabitacion);
		if(tipoHabitacion == null) {
			throw new Exception("No existe el tipo de habitación.");
		}
		HashSet<Hotel> hoteles_sugeridos = new HashSet<Hotel>();
		
		for (Map.Entry<String, Hotel> entry : this.hoteles.entrySet()) {
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
	
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}

	public HashSet<Reserva> buscarReservasPendientes(String nombreHotel) throws Exception {
		Hotel hotel = this.hoteles.get(nombreHotel);
		HashSet<Reserva> reservas_pendientes = hotel.buscarReservasPendientes();
		return reservas_pendientes;
	}
	public Set<Reserva> buscarReservasDelCliente(String rutCliente){
		Cliente cliente = this.clientes.get(rutCliente);
		return cliente.getReservas();
	}

	public Reserva seleccionarReserva(Long codigoReserva, String rut) {
		Cliente cliente = this.seleccionarCliente(rut);
		return cliente.seleccionarReserva(codigoReserva);
		
	}
	
	public Reserva seleccionarReserva(Long codigoReserva) {
		for(Hotel h: this.hoteles.values()) {
			Reserva r = h.buscarReservas(codigoReserva);
			if(r != null) {
				return r;
			}
		}
		return null;
	}

	public Reserva registrarHuesped(Long codigoReserva, String nombre, String documento) throws Exception {
		Reserva r = null;
		for(Hotel h: this.hoteles.values()) {
			r = h.buscarReservas(codigoReserva);
			if(r != null) {
				break;
			}
		}
		r.asociarHuesped(nombre, documento);
		return r;
	}
	
	public Reserva tomarReserva(Long codigoReserva) throws Exception {
		Reserva r = null;
		for(Hotel h: this.hoteles.values()) {
			r = h.buscarReservas(codigoReserva);
			if(r != null) {
				break;
			}
		}
		r.getHotel().tomarReserva(codigoReserva, r.getCliente().getRut());
		return r;
	}

	public Reserva modificarReserva(String nombreHotel, Cliente cliente, long codigoReserva, String nombreTipoHabitacion,
			GregorianCalendar fechaInicio, GregorianCalendar fechaFin, boolean modificablePorHuesped) throws Exception {
		TipoHabitacion tipoHabitacion = this.buscarTipoHabitacion(nombreTipoHabitacion);
		Hotel hotel = this.buscarHotel(nombreHotel);
		Reserva reserva = cliente.modificarReserva(hotel, codigoReserva, tipoHabitacion, fechaInicio, fechaFin, modificablePorHuesped);
		return reserva;
		
	}

	public Reserva cancelarReservaDelCliente(Reserva reserva) {
		return reserva.cancelar();
				
	}

	public boolean confirmarDisponibilidad(Reserva reserva, String nombreHotel, String nombreTipoHabitacion,
			GregorianCalendar fechaInicio, GregorianCalendar fechaFin) throws Exception {
		if(this.tiposHabitacion.isEmpty() || this.tiposHabitacion.get(nombreTipoHabitacion) == null) {
			throw new Exception("No existe el tipo de habitación solicitado.");
		}

		if(Infrastructure.getInstance().getCalendario().esPasada(fechaInicio)) {
			throw new Exception("La fecha indicada es pasada");
		}
		
		if(Infrastructure.getInstance().getCalendario().esPosterior(fechaInicio, fechaFin)) {
			throw new Exception("La fecha de inicio es superior a la fecha de fin");
		}
		
		TipoHabitacion tipoHabitacion = this.tiposHabitacion.get(nombreTipoHabitacion);
		Hotel hotel = this.hoteles.get(nombreHotel);
		return hotel.confirmarDisponibilidad(reserva, tipoHabitacion, fechaInicio, fechaFin);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
