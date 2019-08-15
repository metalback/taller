package org.tds.sgh.business;

import javax.persistence.Entity;

@Entity
public class Habitacion
{
	// --------------------------------------------------------------------------------------------
	private TipoHabitacion tipoHabitacion;
	private String nombre;
	

	
	// --------------------------------------------------------------------------------------------
	
	public Habitacion() {}
	
	public Habitacion(TipoHabitacion tipoHabitacion, String nombre)
	{
		this.nombre = nombre;
		this.tipoHabitacion = tipoHabitacion;
		

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

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setTipoHabitacion(TipoHabitacion tipoHabitacion) {
		this.tipoHabitacion = tipoHabitacion;
	}

}
