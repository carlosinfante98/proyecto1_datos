package controller;

import api.ITaxiTripsManager;
import model.data_structures.IQueue;
import model.data_structures.IStack;
import model.data_structures.LinkedList;
import model.logic.TaxiTripsManager;
import model.vo.Compania;
import model.vo.InfoTaxiRango;
import model.vo.RangoDistancia;
import model.vo.RangoFechaHora;
import model.vo.Servicio;
import model.vo.Taxi;
import model.vo.ZonaServicios;

/**
 * Clase que controla de manera estática los métodos que contienen los requerimientos del proyecto.
 */
public class Controller 
{
	/**
	 * Modela el manejador de la clase lógica
	 */
	private static ITaxiTripsManager manager = new TaxiTripsManager();

	//1C
	/**
	 * Carga el sistema Json para manejar el programa.
	 * @param direccionJson Nombre del json que se quiere cargar.
	 * @return True si se pudo cargar el sistema Json. False de lo contrario.
	 */
	public static boolean cargarSistema(String direccionJson)
	{
		return manager.cargarSistema(direccionJson);
	}
	
	//1A
	/**
	 * Da los servicios que están en el rango estipulado por parámetro dentro de una cola.
	 * @param rango Rango de fecha y hora de los servicios que se quieren tener en una cola.
	 * @return
	 */
	public static IQueue<Servicio> darServiciosEnRango(RangoFechaHora rango)
	{
		return manager.darServiciosEnPeriodo(rango);
	}

	//2A
	/**
	 * Da el taxi con más servicios dentro de un rango establecido el cual está dentro de una compañía dada por parámetro.
	 * @param rango Rango de fecha y hora de los servicios del taxi.
	 * @param company Nombre de la compañía de la cual se quiere buscar el taxi.
	 * @return Taxi con más servicios dentro de una compañía. Sus servicios se delimitan en un rango.
	 */
	public static Taxi darTaxiConMasServiciosEnCompaniaYRango(RangoFechaHora rango, String company)
	{
		return manager.darTaxiConMasServiciosEnCompaniaYRango(rango, company);
	}

	//3A
	/**
	 * Da la información completa de un taxi buscado por su ID dentro de un rango delimitado.
	 * @param id Identificador del taxi del que se quiere buscar la información.
	 * @param rango Rango de servicios que se quieren encontrar de un taxi.
	 * @return InfoTaxiRango que dice toda la información de un taxi.
	 */
	public static InfoTaxiRango darInformacionTaxiEnRango(String id, RangoFechaHora rango)
	{
		return manager.darInformacionTaxiEnRango(id, rango);
	}

	//4A
	/**
	 * Da una lista con listas adentro que están delimitadas por distancia. Primer nodo tiene servicios con millas de 0 a 1.
	 * Segundo nodo va con servicios de 1 a 2 y así sucesivamente.
	 * @param fecha Fecha de la que se quieren los rangos de distancia.
	 * @param horaInicial Hora inicial de los rangos de distancia de la lista.
	 * @param horaFinal Hora final de los rangos de distancia de la lista.
	 * @return Lista con listas adentro que están delimitadas por su distancia en intervalos de 1 milla.
	 */
	public static LinkedList<RangoDistancia> darListaRangosDistancia(String fecha, String horaInicial, String horaFinal) 
	{
		return manager.darListaRangosDistancia(fecha, horaInicial, horaFinal);
	}
	
	//1B
	/**
	 * Da una lista con todas las compañías que tengan al menos un taxi inscrito.
	 * @return Lista con todas las compañías que tengan al menos un taxi inscrito.
	 */
	public static LinkedList<Compania> darCompaniasTaxisInscritos()
	{
		return manager.darCompaniasTaxisInscritos();
	}
	
	//2B
	/**
	 * Da el taxi con mayor facturación de servicios delimitados en un rango, el cual pertenece a una compañía dada por parámetro.
	 * @param rango Rango de los servicios sobre los cuales se quiere hacer la facturación.
	 * @param nomCompania Nombre de la compañía de la cual se quiere conseguir el taxi de mayor facturación.
	 * @return Taxi con mayor facturación de servicios delimitados en un rango, el cual pertenece a una compañía dada por parámetro.
	 */
	public static Taxi darTaxiMayorFacturacion(RangoFechaHora rango, String nomCompania)
	{
		return manager.darTaxiMayorFacturacion(rango, nomCompania);
	}
	
	//3B
	/**
	 * Busca la información completa de cada zona a partir de unos servicios que estén dentro del rango pedido.
	 * @param rango Rango de los servicios sobre los cuales se quiere hacer la búsqueda.
	 * @param idZona Zona de la cual se quiere conocer la información completa.
	 * @return Lista con la información completa de cada zona a partir de unos servicios que estén dentro del rango pedido.
	 */
	public static LinkedList<Servicio> darServiciosZonaValorTotal(RangoFechaHora rango, String idZona)
	{
		return manager.darServiciosZonaValorTotal(rango, idZona);
	}
	
	//4B
	/**
	 * Da una lista con todas las zonas de la ciudad y su información (ordenadas por su identificador).	
	 * @param rango Rango de los servicios sobre los cuales se quiere hacer la búsqueda.
	 * @return Lista con todas las zonas de la ciudad y su información.
	 */
	public static LinkedList<ZonaServicios> darZonasServicios (RangoFechaHora rango)
	{
		return manager.darZonasServicios(rango);
	}
	
	//2C
	/**
	 * Identificar el top X de compañías que	más	servicios iniciaron en un periodo de	tiempo	
	 * dado	por	una	fecha/hora inicial y	una	fecha/hora final.
	 * @param rango Rango de los servicios sobre los cuales se quiere hacer la búsqueda.
	 * @param n Numero de compañías sobre las cuales se quiere hacer el top que mas servicios iniciaron en un periodo.
	 * @return
	 */
	public static LinkedList<Compania> companiasMasServicios(RangoFechaHora rango, int n)
	{
		return manager.companiasMasServicios(rango, n);
	}

	//3C
	/**
	 * Busca el taxi más rentable de cada compañía. El taxi más rentable de una compañía es aquel cuya 
	 * relación de	plata ganada	y distancia recorrida en los	servicios prestados es mayor.
	 * @return Lista con los taxis más rentable de cada compañía.
	 */
	public static LinkedList<Taxi> taxisMasRentables()
	{
		return manager.taxisMasRentables();
	}

	//4C
	/**
	 * Se comprime la información de los servicios de un taxi en intervalos.
	 * @param taxiId Identificador del taxi al cual se le quiere comprimir la información.
	 * @param horaInicial Hora inicial de la cual se quiere coger los servicios y comprimirlos.
	 * @param horaFinal Hora final de la cual se quiere coger los servicios y comprimirlos.
	 * @param fecha Fecha de la cual se quiere coger los servicios y comprimirlos.
	 * @return Cola con la información de un taxi comprimida en intervalos.
	 */
	public static IStack <Servicio> darServicioResumen(String taxiId, String horaInicial, String horaFinal, String fecha)
	{
		return manager.darServicioResumen(taxiId,horaInicial,horaFinal,fecha);
	}



}
