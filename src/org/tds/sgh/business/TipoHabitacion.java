package org.tds.sgh.business;

public class TipoHabitacion
{
	// --------------------------------------------------------------------------------------------
	
	private String nombre;
	
	// --------------------------------------------------------------------------------------------
	
	public TipoHabitacion(String nombre)
	{
		this.nombre = nombre;
	}
	
	// --------------------------------------------------------------------------------------------
	
	public String getNombre()
	{
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
