package model.vo;

/**
 * VO utilizado para req 3A, contiene el rango en el que se pidió generar la información del taxi y 
 * los datos solicitados
 */
public class InfoTaxiRango implements Comparable<InfoTaxiRango>
{
	//--------------------------------
	//ATRIBUTOS
	//--------------------------------
	
	/**
	 * Modela el id del taxi 
	 */
	private String idTaxi;
	
	/**
	 * Modela el rango de fechas y horas (iniciales y finales)
	 */
	private RangoFechaHora rango;
	
	/**
	 * modela el nombre de la compaï¿½ia del taxi
	 */
	private String company;
	
	/**
	 * modela el dinero que gano el taxi en el rango
	 */
	private double plataGanada;	
	/**
	 * modela la distancia recorrida por el taxi en el rango
	 */
	private double distanciaTotalRecorrida; 
	
	/**
	 * modela el tiempo total de servicios
	 */
	private int tiempoTotal;
	
	/**
	 * Modela el número de servicios en rango
	 */
	private int numServicesInRange;
	
	//--------------------------------
	//CONSTRUCTOR
	//--------------------------------
	
	public InfoTaxiRango(RangoFechaHora rangox)
	{
		rango = rangox;
		tiempoTotal = 0;
		numServicesInRange = 0;
	}
	
	//--------------------------------
	//METODOS
	//--------------------------------
	
	/**
	 * @return the idTaxi
	 */
	public String getIdTaxi()
	{
		return idTaxi;
	}
	/**
	 * @return numero de servicios en rango
	 */
	public int getNumServicesInRange()
	{
		return numServicesInRange;
	}
	/**
	 * @return numero de servicios en rango
	 */
	public void setNumServicesInRange(int pNum)
	{
		numServicesInRange = pNum;
	}

	/**
	 * @param idTaxi the idTaxi to set
	 */
	public void setIdTaxi(String idTaxi)
	{
		this.idTaxi = idTaxi;
	}

	/**
	 * @return the rango
	 */
	public RangoFechaHora getRango()
	{
		return rango;
	}

	/**
	 * @param rango the rango to set
	 */
	public void setRango(RangoFechaHora rango)
	{
		this.rango = rango;
	}

	/**
	 * @return the company
	 */
	public String getCompany()
	{
		return company;
	}

	/**
	 * @param company the company to set
	 */
	public void setCompany(String company) 
	{
		this.company = company;
	}
	
	public int getTiempoTotal()
	{
		return tiempoTotal;
	}

	/**
	 * @return the plataGanada
	 */
	public double getPlataGanada() 
	{
		return plataGanada;
	}

	/**
	 * @param plataGanada the plataGanada to set
	 */
	public void setPlataGanada(double plataGanada) 
	{
		this.plataGanada = plataGanada;
	}
	/**
	 * @param tTiempo 
	 */
	public void setTiempoTotal(int tTiempo)
	{
		tiempoTotal = tTiempo;
	}
	/**
	 * @return the distanciaTotalRecorrida
	 */
	public double getDistanciaTotalRecorrida()
	{
		return distanciaTotalRecorrida;
	}
	/**
	 * @param distancia the distancia to set
	 */
	public void setDistanciaTotalRecorrida(double distancia)
	{
		 distanciaTotalRecorrida = distancia;
	}
	/**
	 * @param pDis incrementar distancia
	 */
	public void increaseDistance(double pDis)
	{
		distanciaTotalRecorrida +=  pDis;
	}
	/**
	 * @param pPlata incrementar dinero
	 */
	public void increaseMoney( double pPlata)
	{
		plataGanada += pPlata;
	}
	/**
	 * @param pTime incrementar tiempo total
	 */
	public void increaseTime( int pTime)
	{
		tiempoTotal += pTime;
	}
	/**
	 * @param o InfoTaxiRango info del taxi con la que se comparara
	 * @return int resultado de comparar dos infotaxirango servicio
	 */
	@Override
	public int compareTo(InfoTaxiRango o) 
	{
		return 0;
	}
	
}