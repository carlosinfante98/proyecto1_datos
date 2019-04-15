package model.vo;

import model.data_structures.ArrayListT;
import model.utils.ComparatorServicioFechaFinal;
import model.utils.ComparatorServicioFechaInicial;
import model.utils.OrdenatorP;
import model.utils.OrdenatorP.Ordenamientos;

/**
 * Representation of a taxi object
 */
public class Taxi implements Comparable<Taxi>
{
	private String taxi_id;
	private String company;
	private int numServicesInRange;
	private double plataGanada;
	private ArrayListT<Servicio> serviciosDelTaxi;
	private InfoTaxiRango infoTaxiRango;
	private double distanciaTotal;
	private int tiempoServicios;

	private double rentabilidad;

	public Taxi(String pTaxi_id, String company_name)
	{
		taxi_id = pTaxi_id;
		company = company_name;
		numServicesInRange = 0;
		plataGanada = 0;
		serviciosDelTaxi = new ArrayListT<Servicio>();
		infoTaxiRango = null;
		distanciaTotal = 0.0;
		tiempoServicios = 0;
		rentabilidad = 0;
	}

	/**
	 * @return id - taxi_id
	 */
	public String getTaxiId()
	{
		return  taxi_id;
	}

	/**
	 * @return company
	 */
	public String getCompany()
	{
		return company;
	}

	public int getTiempoServicios()
	{
		return tiempoServicios;
	}

	public ArrayListT<Servicio> getTaxiServices()
	{
		return serviciosDelTaxi;
	}

	public InfoTaxiRango getInfoTaxiRango()
	{
		return infoTaxiRango;
	}

	public int getNumServicesInRange()
	{
		return numServicesInRange;
	}
	public double  getRentabilidad()
	{
		rentabilidad = plataGanada/ serviciosDelTaxi.size();
		return rentabilidad;
	}

	public double getPlataGanada()
	{
		return plataGanada;
	}

	public double getDistanciaRecorrida()
	{
		return distanciaTotal;
	}

	public void asignarPlataGanada(RangoFechaHora r)
	{
		try
		{
			for(int j=0; j < serviciosDelTaxi.size(); j++)
			{
				if(serviciosDelTaxi.get(j).estaEnElRango(r))
					plataGanada += serviciosDelTaxi.get(j).getTripTotal();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public void asignarNumeroServiciosEnRango(RangoFechaHora r)
	{
		try
		{
			for(int i=0; i < serviciosDelTaxi.size(); i++)
			{
				if(serviciosDelTaxi.get(i).estaEnElRango(r))
					numServicesInRange++;
			}
		}
		catch(Exception e )
		{
			e.printStackTrace();
		}
	}

	public ArrayListT<Servicio>asignarServiciosEnRango(RangoFechaHora r)
	{
		ArrayListT<Servicio> arr = new ArrayListT<Servicio>();
		try
		{
			for(int i=0; i < serviciosDelTaxi.size(); i++)
			{
				if(serviciosDelTaxi.get(i).estaEnElRango(r))
				{
					arr.add(serviciosDelTaxi.get(i));
					numServicesInRange++;
				}
			}
			return arr;
		}
		catch(Exception e )
		{
			e.printStackTrace();
			return null;
		}
	}

	public void asignarDistanciaServiciosEnRango(RangoFechaHora r)
	{
		try
		{
			for(int i=0; i < serviciosDelTaxi.size(); i++)
			{
				if(serviciosDelTaxi.get(i).estaEnElRango(r))
					distanciaTotal += serviciosDelTaxi.get(i).getTripMiles();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public void asignarTiempoServiciosEnRango(RangoFechaHora r)
	{
		try
		{
			for(int i=0; i < serviciosDelTaxi.size(); i++)
			{
				if(serviciosDelTaxi.get(i).estaEnElRango(r))
					tiempoServicios += serviciosDelTaxi.get(i).getTripSeconds();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public void ordenarServiciosPorFechaInicial()
	{
		OrdenatorP<Servicio> ord = new OrdenatorP<Servicio>();
		ComparatorServicioFechaInicial comp = new ComparatorServicioFechaInicial();
		ord.ordenar(Ordenamientos.MERGE, true, comp, serviciosDelTaxi);

	}
	public void ordenarServiciosPorFechaFinal()
	{
		OrdenatorP<Servicio> orde = new OrdenatorP<Servicio>();
		ComparatorServicioFechaFinal compa = new ComparatorServicioFechaFinal();
		orde.ordenar(Ordenamientos.MERGE, false, compa, serviciosDelTaxi);
	}
	@Override
	public int compareTo(Taxi o) 
	{
		int compare = taxi_id.compareTo(o.taxi_id);
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

	public String toString()
	{
		return taxi_id;
	}
}