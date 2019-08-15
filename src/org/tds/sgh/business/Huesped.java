package org.tds.sgh.business;

import javax.persistence.Entity;

@Entity
public class Huesped {
	private String nombre;
	private String documento;
	
	public Huesped(String nombre, String documento) {
		this.nombre = nombre;
		this.documento = documento;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}
	
}
