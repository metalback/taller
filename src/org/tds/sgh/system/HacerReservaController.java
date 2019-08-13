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
import org.tds.sgh.infrastructure.Infrastructure;

public class HacerReservaController extends BaseController implements IHacerReservaController {
	private Cliente cliente;
	
	HacerReservaController(CadenaHotelera cadenaHotelera) {
		super(cadenaHotelera);
	}
	
	public Set<HotelDTO> sugerirAlternativas(String pais, String nombreTipoHabitacion, GregorianCalendar fechaInicio,
			GregorianCalendar fechaFin) throws Exception {
		if(Infrastructure.getInstance().getCalendario().esPasada(fechaInicio)) {
			throw new Exception("Fecha inicio en el pasado");
		}
		HashSet<Hotel> hoteles = (HashSet<Hotel>) this.cadenaHotelera.sugerirAlternativas( pais,nombreTipoHabitacion,fechaInicio,fechaFin);
		return DTO.getInstance().mapHoteles(hoteles);
	}
}
