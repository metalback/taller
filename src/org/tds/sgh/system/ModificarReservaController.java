package org.tds.sgh.system;

import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

import org.tds.sgh.business.CadenaHotelera;
import org.tds.sgh.business.Cliente;
import org.tds.sgh.business.Reserva;
import org.tds.sgh.dtos.ClienteDTO;
import org.tds.sgh.dtos.DTO;
import org.tds.sgh.dtos.HotelDTO;
import org.tds.sgh.dtos.ReservaDTO;
import org.tds.sgh.infrastructure.NotImplementedException;

public class ModificarReservaController extends BaseController implements IModificarReservaController {
	
	public ModificarReservaController(CadenaHotelera cadenaHotelera) {
		super(cadenaHotelera);
	}

	public ReservaDTO modificarReserva(String nombreHotel, String nombreTipoHabitacion, GregorianCalendar fechaInicio,
			GregorianCalendar fechaFin, boolean modificablePorHuesped) throws Exception {
		Cliente cliente = this.reserva.getCliente();
		Reserva reserva = this.cadenaHotelera.modificarReserva(nombreHotel, cliente, this.reserva.getCodigoReserva(), nombreTipoHabitacion, fechaInicio, fechaFin, modificablePorHuesped);
		return DTO.getInstance().map(reserva);
	}

}
