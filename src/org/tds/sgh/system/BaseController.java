package org.tds.sgh.system;

import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

import org.tds.sgh.business.CadenaHotelera;
import org.tds.sgh.business.Cliente;
import org.tds.sgh.business.Hotel;
import org.tds.sgh.business.Reserva;
import org.tds.sgh.dtos.ClienteDTO;
import org.tds.sgh.dtos.DTO;
import org.tds.sgh.dtos.HotelDTO;
import org.tds.sgh.dtos.ReservaDTO;

public class BaseController implements IIdentificarClienteEnRecepcionController {

	protected CadenaHotelera cadenaHotelera;
	protected Cliente cliente;
	protected Reserva reserva;
	
	public BaseController(CadenaHotelera cadenaHotelera) {
		this.cadenaHotelera = cadenaHotelera;
	} 

	public Set<ClienteDTO> buscarCliente(String patronNombreCliente) {
		HashSet<Cliente> clientes = (HashSet<Cliente>) this.cadenaHotelera.buscarClientes(patronNombreCliente);
		return DTO.getInstance().mapClientes(clientes);
	}

	public ClienteDTO seleccionarCliente(String rut) throws Exception {
		Cliente cliente = this.cadenaHotelera.seleccionarCliente(rut);
		this.cliente = cliente;
		return DTO.getInstance().map(cliente);
	}

	public boolean confirmarDisponibilidad(String nombreHotel, String nombreTipoHabitacion,
			GregorianCalendar fechaInicio, GregorianCalendar fechaFin) throws Exception {
		Boolean disponibilidad = this.cadenaHotelera.confirmarDisponibilidad(nombreHotel, nombreTipoHabitacion, fechaInicio, fechaFin);
		return disponibilidad;
	}
	
	public ReservaDTO registrarReserva(String nombreHotel, String nombreTipoHabitacion, GregorianCalendar fechaInicio,
			GregorianCalendar fechaFin, boolean modificablePorHuesped) throws Exception {
		Reserva reserva = this.cadenaHotelera.registrarReserva(nombreHotel,  nombreTipoHabitacion,  fechaInicio,
				 fechaFin,  modificablePorHuesped);
		this.reserva = reserva;
		return DTO.getInstance().map(reserva);
		
	}

	public Set<HotelDTO> sugerirAlternativas(String pais, String nombreTipoHabitacion, GregorianCalendar fechaInicio,
			GregorianCalendar fechaFin) throws Exception {
		HashSet<Hotel> hoteles = (HashSet<Hotel>) this.cadenaHotelera.sugerirAlternativas( pais,nombreTipoHabitacion,fechaInicio,fechaFin);
		return DTO.getInstance().mapHoteles(hoteles);
	}

	public ClienteDTO registrarCliente(String rut, String nombre, String direccion, String telefono, String mail)
			throws Exception {
		Cliente cliente = this.cadenaHotelera.agregarCliente(rut, nombre, direccion, telefono, mail);
		this.cliente = cliente;
		return DTO.getInstance().map(cliente);
	}

	public Set<ReservaDTO> buscarReservasDelCliente() throws Exception {
		Set<Reserva> reservas = this.cadenaHotelera.buscarReservasDelCliente(this.cliente.getRut());
		return DTO.getInstance().mapReservas(reservas);
	}
	
	public ReservaDTO seleccionarReserva(long codigoReserva) throws Exception {
		Reserva reserva = this.cadenaHotelera.seleccionarReserva(codigoReserva);
		this.reserva = reserva;
		return DTO.getInstance().map(reserva);
	}

}
