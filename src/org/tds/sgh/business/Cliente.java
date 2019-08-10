package org.tds.sgh.business;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

public class Cliente
{
	// --------------------------------------------------------------------------------------------
	
	private String direccion;
	
	private String mail;
	
	private String nombre;
	
	private String rut;
	
	private String telefono;
	
	private Map<String, Reserva> reservas;
	
	// --------------------------------------------------------------------------------------------
	
	public Cliente(String rut, String nombre, String direccion, String telefono, String mail)
	{
		this.direccion = direccion;
		
		this.mail = mail;
		
		this.nombre = nombre;
		
		this.rut = rut;
		
		this.telefono = telefono;
		
		this.reservas = new HashMap<String, Reserva>();
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
	
	public void agregarReservaAColeccion(Reserva reserva) {
		long codigoReserva = reserva.getCodigoReserva();
		if (this.reservas.containsKey(codigoReserva))
		{
			throw new Exception("Ya existe una reserva con el código indicado.");
		}
		this.reservas.put(codigoReserva, reserva);
	}
	
	public Reserva seleccionarReserva(long codigoReserva) {
		return this.reservas.get(codigoReserva);
	}
	
	public Set<Reserva> obtenerReservasPendientes(){
		HashSet<Reserva> reservasPendientes = new HashSet<Reserva>();
		for (Map.Entry<String, Reserva> entry : this.reservas.entrySet()) {
			Reserva reserva = entry.getValue();
			if (reserva.getEstado() == EstadoReserva.Pendiente) {
				reservasPendientes.add(entry.getValue());
			}
		}
		return reservasPendientes;
	}
	
	public Reserva modificarReserva(long codigoReserva, TipoHabitacion tipoHabitacion, GregorianCalendar fechaInicio, GregorianCalendar fechaFin, Boolean modificablePorCliente){
		Reserva reserva = this.reservas.get(codigoReserva);
		return reserva.actualizarReserva(tipoHabitacion, fechaInicio, fechaFin, modificablePorCliente);
	}
	
	public Reserva asociarHuesped(long codigoReserva, String nombre, String documento) {
		Reserva reserva = this.reservas.get(codigoReserva);
		return reserva.asociarHuesped(nombre, documento);
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

	public void setReservas(Map<String, Reserva> reservas) {
		this.reservas = reservas;
	}
	
}
