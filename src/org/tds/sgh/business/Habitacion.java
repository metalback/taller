package org.tds.sgh.business;

public class Habitacion
{
	// --------------------------------------------------------------------------------------------
	private long codigoHabitacion;
	private String nombre;
	

	
	// --------------------------------------------------------------------------------------------
	
	public Habitacion(TipoHabitacion tipoHabitacion, String nombre)
	{
		this.nombre = nombre;
		this.nombre = nombre;
		

	}
	
	// --------------------------------------------------------------------------------------------
	
	public String getNombre()
	{
		return this.nombre;
	}
	
	public TipoHabitacion getTipoHabitacion()
	{
		return this.tipoHabitacion;
	}
}
