package org.tds.sgh.system;

import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

import org.tds.sgh.business.CadenaHotelera;
import org.tds.sgh.business.Cliente;
import org.tds.sgh.dtos.ClienteDTO;
import org.tds.sgh.dtos.DTO;
import org.tds.sgh.dtos.HotelDTO;
import org.tds.sgh.dtos.ReservaDTO;

public class ModificarReservaController extends BaseController implements IModificarReservaController {
	
	Cliente cliente;
	
	public ModificarReservaController(CadenaHotelera cadenaHotelera) {
		super(cadenaHotelera);
	}


	@Override
	public ClienteDTO seleccionarCliente(String rut) throws Exception {
		
		return null;
	}

	@Override
	public Set<ReservaDTO> buscarReservasDelCliente() throws Exception {
		HashSet<Reserva> reservas = this.cadenaHotelera.buscarReservasDelCliente(this.cliente.getId());
		return DTO.getInstance().mapReservas(reservas);
	}

	@Override
	public ReservaDTO seleccionarReserva(long codigoReserva) throws Exception {
		
		return null;
	}


	@Override
	public ReservaDTO modificarReserva(String nombreHotel, String nombreTipoHabitacion, GregorianCalendar fechaInicio,
			GregorianCalendar fechaFin, boolean modificablePorHuesped) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
