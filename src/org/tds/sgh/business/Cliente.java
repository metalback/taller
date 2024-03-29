package org.tds.sgh.business;

import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.tds.sgh.infrastructure.Infrastructure;

@Entity
public class Cliente
{
	// --------------------------------------------------------------------------------------------
	private long id;
	
	private String direccion;
	
	private String mail;
	
	private String nombre;
	
	private String rut;
	
	private String telefono;
	
	private Map<Long, Reserva> reservas;
	
	// --------------------------------------------------------------------------------------------
	
	public Cliente(String rut, String nombre, String direccion, String telefono, String mail)
	{
		this.direccion = direccion;
		
		this.mail = mail;
		
		this.nombre = nombre;
		
		this.rut = rut;
		
		this.telefono = telefono;
		
		this.reservas = new HashMap<Long, Reserva>();
	}
	
	// --------------------------------------------------------------------------------------------
	
	public boolean coincideElNombre(String patronNombreCliente)
	{
		return this.nombre.matches(patronNombreCliente);
	}
	
	public String getDireccion()
	{
		return this.direccion;
	}
	
	public String getMail()
	{
		return this.mail;
	}
	
	public String getNombre()
	{
		return this.nombre;
	}
	
	public String getRut()
	{
		return this.rut;
	}
	
	public String getTelefono()
	{
		return this.telefono;
	}
	
	public void agregarReservaAColeccion(Reserva reserva){
		Long codigoReserva = reserva.getCodigoReserva();
		this.reservas.put(codigoReserva, reserva);
	}
	
	public Reserva seleccionarReserva(Long codigoReserva) {
		return this.reservas.get(codigoReserva);
	}
	
	public Set<Reserva> obtenerReservasPendientes(){
		HashSet<Reserva> reservasPendientes = new HashSet<Reserva>();
		for (Map.Entry<Long, Reserva> entry : this.reservas.entrySet()) {
			Reserva reserva = entry.getValue();
			if (reserva.getEstado() == EstadoReserva.Pendiente) {
				reservasPendientes.add(entry.getValue());
			}
		}
		return reservasPendientes;
	}
	
	public Reserva modificarReserva(Hotel hotel, long codigoReserva, TipoHabitacion tipoHabitacion, GregorianCalendar fechaInicio, GregorianCalendar fechaFin, Boolean modificablePorCliente){
		Reserva reserva = this.reservas.get(codigoReserva);
		return reserva.actualizarReserva(hotel, tipoHabitacion, fechaInicio, fechaFin, modificablePorCliente);
	}
	
	public void quitarReserva(Reserva reserva) {
		this.reservas.remove(reserva.getCodigoReserva());
	}
	
	

	// --------------------------------------------------------------------------------------------

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setRut(String rut) {
		this.rut = rut;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public void setReservas(Map<Long, Reserva> reservas) {
		this.reservas = reservas;
	}
	
	public Set<Reserva> listadoReservasPendientes() {
		Set<Reserva> reservas = new HashSet<Reserva>();	
		for  ( Reserva r : this.reservas.values()) {
			if(r.getEstado() == EstadoReserva.Pendiente) {
				if(Infrastructure.getInstance().getCalendario().esPosterior(r.getFechaInicio(), Infrastructure.getInstance().getCalendario().getHoy())
						|| Infrastructure.getInstance().getCalendario().esHoy(r.getFechaInicio())) {
					reservas.add(r);
				}
			}		
		}
		return reservas;
	}
	
	@OneToMany(cascade = CascadeType.ALL)
	public Map<Long, Reserva> getReservas() {
		return reservas;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	
	
}
