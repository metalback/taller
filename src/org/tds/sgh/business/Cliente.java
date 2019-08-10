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
	
	private Long codigoCliente;
	
	// --------------------------------------------------------------------------------------------
	
	public Cliente(String rut, String nombre, String direccion, String telefono, String mail)
	{
		this.codigoCliente = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
		
		this.direccion = direccion;
		
		this.mail = mail;
		
		this.nombre = nombre;
		
		this.rut = rut;
		
		this.telefono = telefono;
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
	
	public Reserva seleccionarReserva(Long codigoReserva) {
		return this.reservas.get(codigoReserva);
	}
	
	public Set<Reserva> obtenerReservasPendientes(){
		HashSet<Reserva> reservasPendientes = new HashSet<Reserva>();
		
		this.reservas.forEach((k, v) -> i[0] += k + v);
		
		this.reservas
		
		return reservasPendientes;
	}
	
}
