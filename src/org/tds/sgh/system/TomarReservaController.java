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
import org.tds.sgh.infrastructure.Infrastructure;

public class TomarReservaController extends BaseController implements ITomarReservaController {
	
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
	public ReservaDTO seleccionarReserva(long codigoReserva) throws Exception {
		Reserva reserva = this.cadenaHotelera.seleccionarReserva(codigoReserva, cliente.getRut());
		this.reserva = reserva;
		return DTO.getInstance().map(reserva);
	}

	@Override
	public ReservaDTO registrarHuesped(String nombre, String documento) throws Exception {
		Reserva reserva = this.cadenaHotelera.registrarHuesped(this.cliente.getRut(), this.reserva.getCodigoReserva(), nombre, documento);
		this.reserva = reserva;
		return DTO.getInstance().map(reserva);
	}

	@Override
	public ReservaDTO tomarReserva() throws Exception {
		Reserva reserva  = this.cadenaHotelera.tomarReserva(this.reserva.getCodigoReserva(), cliente.getRut(), this.reserva.getHotel());
		Infrastructure.getInstance().getSistemaMensajeria().enviarMail(cliente.getMail(), "", "");
		Infrastructure.getInstance().getSistemaFacturacion().iniciarEstadia(DTO.getInstance().map(reserva));
		return DTO.getInstance().map(reserva);
	}
}
