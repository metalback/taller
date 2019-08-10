package org.tds.sgh.system;

import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

import org.tds.sgh.business.CadenaHotelera;
import org.tds.sgh.business.Cliente;
import org.tds.sgh.business.Hotel;
import org.tds.sgh.dtos.ClienteDTO;
import org.tds.sgh.dtos.DTO;
import org.tds.sgh.dtos.HotelDTO;
import org.tds.sgh.dtos.ReservaDTO;

public class TomarReservaController implements ITomarReservaController {
	private CadenaHotelera cadenaHotelera;
	private Cliente cliente;
	private Reserva reserva;
	
	@Override
	public Set<ReservaDTO> buscarReservasDelCliente() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<ClienteDTO> buscarCliente(String patronNombreCliente) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClienteDTO seleccionarCliente(String rut) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean confirmarDisponibilidad(String nombreHotel, String nombreTipoHabitacion,
			GregorianCalendar fechaInicio, GregorianCalendar fechaFin) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ReservaDTO registrarReserva(String nombreHotel, String nombreTipoHabitacion, GregorianCalendar fechaInicio,
			GregorianCalendar fechaFin, boolean modificablePorHuesped) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<HotelDTO> sugerirAlternativas(String pais, String nombreTipoHabitacion, GregorianCalendar fechaInicio,
			GregorianCalendar fechaFin) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClienteDTO registrarCliente(String rut, String nombre, String direccion, String telefono, String mail)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReservaDTO modificarReserva(String nombreHotel, String nombreTipoHabitacion, GregorianCalendar fechaInicio,
			GregorianCalendar fechaFin, boolean modificablePorHuesped) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<ReservaDTO> buscarReservasPendientes(String nombreHotel) throws Exception {
		HashSet<Reserva> reservas = this.cadenaHotelera.buscarReservasPendientes(nombreHotel);
		return DTO.getInstance().mapReservas(reservas);
	}

	@Override
	public ReservaDTO seleccionarReserva(long codigoReserva) throws Exception {
		Reserva reserva = this.cadenaHotelera.seleccionarReserva(codigoReserva, cliente.getNombre());
		return DTO.getInstance().map(reserva);
	}

	@Override
	public ReservaDTO registrarHuesped(String nombre, String documento) throws Exception {
		Reserva reserva = this.cadenaHotelera.registrarHuesped(cliente.getNombre());
		return DTO.getInstance().map(reserva);
	}

	@Override
	public ReservaDTO tomarReserva() throws Exception {
		Reserva reserva  = this.cadenaHotelera.tomarReserva(reserva.getCodigoReserva(), cliente.getNombre());
		return DTO.getInstance().map(reserva);
	}
	
	
	
}
