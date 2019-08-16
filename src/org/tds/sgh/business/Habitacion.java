package org.tds.sgh.business;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Habitacion
{
	// --------------------------------------------------------------------------------------------
	
	private long id;
	private TipoHabitacion tipoHabitacion;
	private String nombre;
	private Hotel hotel;
	

	
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
	@OneToOne
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

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	@ManyToOne
	public Hotel getHotel() {
		return hotel;
	}
	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}
}
