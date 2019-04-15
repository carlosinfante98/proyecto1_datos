package model.vo;

import model.data_structures.ArrayListT;

public class FechaServicios implements Comparable<FechaServicios>
{
	//--------------------------------
	//ATRIBUTOS
	//--------------------------------
	
	/**
	 * Fecha de referencia
	 */
	private String fechaDeReferencia;
	/**
	 * Lista de servicios de la fecha 
	 */
	private ArrayListT<Servicio> serviciosDeLaFecha;
	
	//--------------------------------
	//CONSTRUCTOR
	//--------------------------------
	
	/**
	 * Constructor FechaServicios
	 */
	public FechaServicios(String pFecha)
	{
		fechaDeReferencia = pFecha;
		serviciosDeLaFecha =  new ArrayListT<Servicio>();
	}
	
	//--------------------------------
	//METODOS
	//--------------------------------
	
	/**
	 * Da la fecha de referencia
	 * @return fecha
	 */
	public String getFechaDeReferencia() 
	{
		return fechaDeReferencia;
	}
	/**
	 * Lista de servicios de la fecha
	 * @return lista de servicios de la fecha.
	 */
	public ArrayListT<Servicio> getServiciosDeLaFecha() 
	{
		return serviciosDeLaFecha;
	}
	/**
	 * Pertenece al rango de la fecha
	 * @param r RangoFechaHora 
	 * @return true si pertenece . false si no pertenece
	 */
	public boolean perteneceAlRango(RangoFechaHora r)
	{
		if(fechaDeReferencia.compareTo(r.getFechaHoraInicial()) >= 0 && fechaDeReferencia.compareTo(r.getFechaHoraFinal()) <= 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	/**
	 * @param o FechaServicios compania con la que se comparara
	 * @return int resultado de comparar dos fechas servicio
	 */
	@Override
	public int compareTo(FechaServicios o) 
	{
		int compare = fechaDeReferencia.compareTo(o.getFechaDeReferencia());
		if(compare < 0)
		{
			return -1;
		}
		else if(compare > 0)
		{
			return 1;
		}
		else
		{
			return 0;			
		}
	}





}