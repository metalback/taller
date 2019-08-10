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

public class TomarReservaController extends BaseController implements ITomarReservaController {
	private CadenaHotelera cadenaHotelera;
	private Cliente cliente;
	private Reserva reserva;
	
	TomarReservaController(CadenaHotelera cadenaHotelera) {
		super(cadenaHotelera);
	}
	
	@Override
	public Set<ReservaDTO> buscarReservasDelCliente() throws Exception {
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
	public ReservaDTO seleccionarReserva(Long codigoReserva) throws Exception {
		Reserva reserva = this.cadenaHotelera.seleccionarReserva(codigoReserva, cliente.getCodigoCliente());
		return DTO.getInstance().map(reserva);
	}

	@Override
	public ReservaDTO registrarHuesped(String nombre, String documento) throws Exception {
		Reserva reserva = this.cadenaHotelera.registrarHuesped(cliente.getCodigoCliente(), reserva.getCodigoReserva(), nombre, documento);
		return DTO.getInstance().map(reserva);
	}

	@Override
	public ReservaDTO tomarReserva() throws Exception {
		Reserva reserva  = this.cadenaHotelera.tomarReserva(reserva.getCodigoReserva(), cliente.getCodigoCliente());
		return DTO.getInstance().map(reserva);
	}
}
