package org.tds.sgh.system;

import org.tds.sgh.business.CadenaHotelera;
import org.tds.sgh.business.Reserva;
import org.tds.sgh.dtos.DTO;
import org.tds.sgh.dtos.ReservaDTO;
import org.tds.sgh.infrastructure.Infrastructure;

public class CancelarReservaController extends BaseController implements ICancelarReservaController {

	public CancelarReservaController(CadenaHotelera cadenaHotelera) {
		super(cadenaHotelera);
	}
	
	public ReservaDTO cancelarReservaDelCliente() throws Exception {
		Reserva reserva = this.cadenaHotelera.cancelarReservaDelCliente(this.reserva);
		this.reserva = reserva;
		Infrastructure.getInstance().getSistemaMensajeria().enviarMail(this.cliente.getMail(), "Reserva Cancelada", "Reserva cancelada");
		return DTO.getInstance().map(reserva);
	}

}
