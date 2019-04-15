package model.logic;

import java.io.FileNotFoundException;
import java.io.FileReader;

import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import api.ITaxiTripsManager;
import model.data_structures.ArrayListT;
import model.data_structures.DoubleLinkedList;
import model.data_structures.IQueue;
import model.data_structures.IStack;
import model.data_structures.LinkedList;
import model.utils.ComparatorArea;
import model.utils.ComparatorFechaServicios;
import model.utils.ComparatorNombreCompanias;
import model.utils.ComparatorNumerosServiciosCompania;
import model.utils.ComparatorRentabilidad;
import model.utils.ComparatorServicesMiles;
import model.utils.ComparatorServicioFechaInicial;
import model.utils.ComparatorServiciosRango;
import model.utils.ComparatorTaxiCash;
import model.utils.OrdenatorP;
import model.utils.OrdenatorP.Ordenamientos;
import model.data_structures.Queue;
import model.data_structures.Stack;
import model.vo.Compania;
import model.vo.FechaServicios;
import model.vo.InfoTaxiRango;
import model.vo.RangoDistancia;
import model.vo.RangoFechaHora;
import model.vo.Servicio;
import model.vo.Taxi;
import model.vo.ZonaServicios;

public class TaxiTripsManager implements ITaxiTripsManager 
{
	//--------------------------------
	//CONSTANTES
	//--------------------------------
	
	/**
	 * Constantes que tienen las rutas de los JSON
	 */
	public static final String DIRECCION_SMALL_JSON = "./data/taxi-trips-wrvz-psew-subset-small.json";
	public static final String DIRECCION_MEDIUM_JSON = "./data/taxi-trips-wrvz-psew-subset-medium.json";
	public static final String DIRECCION_LARGE_JSON = "./data/taxi-trips-wrvz-psew-subset-large";
	
	/**
	 * Constante COMPANY
	 */
	public static final String COMPANY = "company";
	/**
	 * Constante TRIP_ID
	 */
	public static final String TRIP_ID = "trip_id";
	/**
	 * Constante TAXI_ID
	 */
	public static final String TAXI_ID ="taxi_id";
	/**
	 * Constante TRIP_SECONDS
	 */
	public static final String TRIP_SECONDS ="trip_seconds";
	/**
	 * Constante TRIP_MILES
	 */
	public static final String TRIP_MILES ="trip_miles";
	/**
	 * Constante TRIP_TOTAL
	 */
	public static final String TRIP_TOTAL ="trip_total";
	/**
	 * Constante DROPOFF_AREA
	 */
	public static final String DOFF_AREA ="dropoff_community_area";
	/**
	 * Constante PICK_UP_AREA
	 */
	public static final String PUP_AREA = "pickup_community_area";
	/**
	 * Constante TRIP_START_TIMESTAMP
	 */
	public static final String START_TIME = "trip_start_timestamp";
	/**
	 * Constante TRIP_END_TIMESTAMP
	 */
	public static final String END_TIME = "trip_end_timestamp";
	
	//--------------------------------
	//ATRIBUTOS
	//--------------------------------
	
	/**
	 * Lista de companias.
	 */
	private ArrayListT<Compania> companias;
	/**
	 * Lista de zona servicios.
	 */
	private ArrayListT<ZonaServicios> zonaServicios;
	/**
	 * Lista de servicios.
	 */
	private ArrayListT<Servicio> servicios;
	
	//--------------------------------
	//CONSTRUCTOR
	//--------------------------------
	
	/**
	 * Constructor TaxiTripsManager
	 */
	public TaxiTripsManager()
	{
		companias = new ArrayListT<Compania>();
		zonaServicios = new ArrayListT<ZonaServicios>();
		servicios = new ArrayListT<Servicio>();
	}
	
	//--------------------------------
	//METODOS
	//--------------------------------
	
	/**
	 * Carga el sistemam del TTM
	 * @param direccionJson Dirección JSON
	 * @return True si cargó.
	 */
	public boolean cargarSistema(String direccionJson) 
	{
		JsonParser parser = new JsonParser();
		try 
		{
			/* Cargar todos los JsonObject (servicio) definidos en un JsonArray en el archivo */
			JsonArray arr= (JsonArray) parser.parse(new FileReader(direccionJson));
			/* Tratar cada JsonObject (Servicio taxi) del JsonArray */
			for (int i = 0; arr != null && i < arr.size(); i++)
			{
				JsonObject obj= (JsonObject) arr.get(i);

				String company_name = obj.get(COMPANY) != null ? obj.get(COMPANY).getAsString() : "Independent";

				String taxi_id = obj.get(TAXI_ID) != null ? obj.get(TAXI_ID).getAsString() : "NaN";

				String trip_id = obj.get(TRIP_ID) != null ? obj.get(TRIP_ID).getAsString(): "NaN";			

				int drop_off_area = obj.get(DOFF_AREA) != null ? obj.get(DOFF_AREA).getAsInt() : -1;			

				int trip_sec = obj.get(TRIP_SECONDS) != null ? obj.get(TRIP_SECONDS).getAsInt(): -1;

				double trip_miles = obj.get(TRIP_MILES) != null ? obj.get(TRIP_MILES).getAsDouble() : -1.0;

				double trip_total = obj.get(TRIP_TOTAL) != null ? obj.get(TRIP_TOTAL).getAsDouble() : 0.0;

				int pick_up_area = obj.get(PUP_AREA) != null ? obj.get(PUP_AREA).getAsInt() : -1;

				String fi = obj.get(START_TIME) != null ? obj.get(START_TIME).getAsString() : "2025-09-24T00:00:00.000";

				String ff = obj.get(END_TIME) != null ? obj.get(END_TIME).getAsString() : "2025-09-24T00:00:00.000";


				servicios.add(new Servicio(trip_id, taxi_id, trip_sec, trip_miles, trip_total, drop_off_area, pick_up_area, fi, ff));
				Compania c = existsCompany(company_name);

				if(c == null)
				{
					c = new Compania(company_name);
					companias.add(c);
					if(c.existsTaxi(taxi_id) == null)
					{					
						Taxi t = new Taxi(taxi_id, company_name);
						t.getTaxiServices().add(new Servicio(trip_id,taxi_id, trip_sec, trip_miles, trip_total, drop_off_area, pick_up_area,fi, ff));
						c.addTaxisCompania(t);
					}
				}
				else
				{
					Taxi t = c.existsTaxi(taxi_id);
					if(t == null)
					{
						t = new Taxi(taxi_id, c.getNombre());
						t.getTaxiServices().add(new Servicio(trip_id,taxi_id, trip_sec, trip_miles, trip_total, drop_off_area, pick_up_area,fi, ff));
						c.addTaxisCompania(t);
					}
					else
					{
						t.getTaxiServices().add(new Servicio(trip_id,taxi_id, trip_sec, trip_miles, trip_total, drop_off_area, pick_up_area,fi, ff));
					}
				}
				c.addServiciosCompania(new Servicio(trip_id,taxi_id, trip_sec, trip_miles, trip_total, drop_off_area, pick_up_area,fi, ff));

				//ZonaServicios.


				ZonaServicios newZone1 = existsZona(pick_up_area);
				ZonaServicios newZone2 = existsZona(drop_off_area);
				ZonaServicios z1 = new ZonaServicios(pick_up_area);
				ZonaServicios z2 = new ZonaServicios(drop_off_area);

				if(newZone1 == null && newZone2 == null)
				{
					if(pick_up_area != drop_off_area)
					{
						newZone1 = zonaServicios.add(z1);
						newZone2 = zonaServicios.add(z2);						
					}
					else
					{
						newZone1 = zonaServicios.add(z1);
						newZone2 = newZone1;
					}
				}
				else if(newZone1 == null && newZone2 != null)
				{
					newZone1 = zonaServicios.add(z1);

				}
				else if(newZone1 != null && newZone2 == null)
				{
					newZone2 = zonaServicios.add(z2);
				}

				if(newZone1.getIdZona() == newZone2.getIdZona())
				{
					newZone1.getServiciosZonados().add(new Servicio(trip_id, taxi_id, trip_sec, trip_miles, trip_total, drop_off_area, pick_up_area, fi, ff));
					FechaServicios fc = newZone1.existsFechaServ(fi);
					FechaServicios fech = new FechaServicios(fi);

					if(fc != null)
					{
						fc.getServiciosDeLaFecha().add(new Servicio(trip_id, taxi_id, trip_sec, trip_miles, trip_total, drop_off_area, pick_up_area, fi, ff));
					}
					else
					{
						fc = newZone1.getFechasServicios().add(fech);
						fc.getServiciosDeLaFecha().add(new Servicio(trip_id, taxi_id, trip_sec, trip_miles, trip_total, drop_off_area, pick_up_area, fi, ff));	
					}
				}
				else
				{
					//Se añaden los servicios a cada zona
					newZone1.getServiciosZonados().add(new Servicio(trip_id, taxi_id, trip_sec, trip_miles, trip_total, drop_off_area, pick_up_area, fi, ff));
					newZone2.getServiciosZonados().add(new Servicio(trip_id, taxi_id, trip_sec, trip_miles, trip_total, drop_off_area, pick_up_area, fi, ff));

					FechaServicios fechaSe = newZone1.existsFechaServ(fi);
					FechaServicios fechaSa = newZone2.existsFechaServ(fi);
					FechaServicios fd = new FechaServicios(fi);
					if(fechaSe != null && fechaSa != null)
					{
						fechaSe.getServiciosDeLaFecha().add(new Servicio(trip_id, taxi_id, trip_sec, trip_miles, trip_total, drop_off_area, pick_up_area, fi, ff));
						fechaSa.getServiciosDeLaFecha().add(new Servicio(trip_id, taxi_id, trip_sec, trip_miles, trip_total, drop_off_area, pick_up_area, fi, ff));
					}
					else if(fechaSe != null && fechaSa == null)
					{
						//Añadir la fecha a la zona que no se ha agregado
						fechaSa = newZone2.getFechasServicios().add(fd);
						fechaSa.getServiciosDeLaFecha().add(new Servicio(trip_id, taxi_id, trip_sec, trip_miles, trip_total, drop_off_area, pick_up_area, fi, ff));

						fechaSe.getServiciosDeLaFecha().add(new Servicio(trip_id, taxi_id, trip_sec, trip_miles, trip_total, drop_off_area, pick_up_area, fi, ff));

					}
					else if(fechaSe == null && fechaSa != null)
					{
						fechaSe = newZone1.getFechasServicios().add(fd);
						fechaSe.getServiciosDeLaFecha().add(new Servicio(trip_id, taxi_id, trip_sec, trip_miles, trip_total, drop_off_area, pick_up_area, fi, ff));

						fechaSa.getServiciosDeLaFecha().add(new Servicio(trip_id, taxi_id, trip_sec, trip_miles, trip_total, drop_off_area, pick_up_area, fi, ff));
					}
					else
					{
						fechaSe = newZone1.getFechasServicios().add(fd);
						fechaSe.getServiciosDeLaFecha().add(new Servicio(trip_id, taxi_id, trip_sec, trip_miles, trip_total, drop_off_area, pick_up_area, fi, ff));

						fechaSa = newZone2.getFechasServicios().add(fd);
						fechaSa.getServiciosDeLaFecha().add(new Servicio(trip_id, taxi_id, trip_sec, trip_miles, trip_total, drop_off_area, pick_up_area, fi, ff));
					}
				}
			}
			int contador = 0;
			System.out.println("Numero de compañías es " + companias.size());
			for (int i = 0; i < companias.size(); i++)
			{
				contador += companias.get(i).getNumTaxisCompania();
			}
			System.out.println("El número de servicios es " + servicios.size());
			System.out.println("El número de taxis es " + contador);

			OrdenatorP<Compania> ordenador = new OrdenatorP<Compania>();
			OrdenatorP<ZonaServicios> ordena = new OrdenatorP<ZonaServicios>();
			ComparatorNombreCompanias comparador = new ComparatorNombreCompanias();
			ComparatorArea comparadoru = new ComparatorArea();
			ordenador.ordenar(Ordenamientos.MERGE, true, comparador, companias);
			ordena.ordenar(Ordenamientos.MERGE, true, comparadoru, zonaServicios);	
		}
		catch (JsonIOException e1 ) 
		{
			e1.printStackTrace();
		}
		catch (JsonSyntaxException e2) 
		{
			e2.printStackTrace();
		}
		catch (FileNotFoundException e3) 
		{
			e3.printStackTrace();
		} 

		return false;
	}
	/**
	 * Verifica la existencia de una compania
	 * @param nombreCompa nombre de la compania
	 * @return compania si existe. null si no existe
	 */
	public Compania existsCompany(String nombreCompa)
	{
		Compania compa = null;
		boolean existe = false;
		for (int i = 0; i < companias.size() && !existe; i++) 
		{
			if(companias.get(i).getNombre().equals(nombreCompa))
			{
				compa = companias.get(i);
				existe = true;
			}
		}

		return compa;
	}
	/**
	 * Busca de manera binaria una compania
	 * @param nombreCompa nombre de la compania
	 * @return compania buscada. null si no existe
	 */
	public Compania binarySearchCompany(String nombreCompa)
	{
		Compania compa = null;
		boolean existe = false;

		int inicio = 0;
		int fin = companias.size() - 1;

		while( inicio <= fin && ! existe)
		{
			int medio = (inicio+fin)/2;

			if(companias.get(medio).getNombre().compareTo(nombreCompa) == 0)
			{
				existe = true;
				compa = companias.get(medio);
			}
			else if(companias.get(medio).getNombre().compareTo(nombreCompa) > 0)

			{
				fin = medio-1;
			}
			else if(companias.get(medio).getNombre().compareTo(nombreCompa) < 0)
			{
				inicio = medio+1;
			}

		}
		return compa;
	}
	/**
	 * Verifica si existe la zona 
	 * @param pZona zona a buscar
	 * @return zona si existe. null si no existe
	 */
	public ZonaServicios existsZona(int pZona)
	{
		ZonaServicios zonaNueva = null;
		boolean existe = false;
		if(zonaServicios.size() >0 )
		{
			for (int i = 0; i < zonaServicios.size() && !existe; i++) 
			{
				if(zonaServicios.get(i).getIdZona() == pZona)
				{
					existe = true;
					zonaNueva = zonaServicios.get(i);
				}
			}
		}
		return zonaNueva;
	}
	/**
	 * Busca de manera binaria una zona
	 * @param nombreCompa id de la zona
	 * @return zona buscada. null si no existe
	 */
	public ZonaServicios binarySearchZonaServicios( int zone1 )
	{
		ZonaServicios zone = null;
		boolean existe = false;

		int inicio = 0;
		int fin = companias.size() - 1;

		while( inicio <= fin && ! existe)
		{
			int medio = (inicio+fin)/2;
			int res = zonaServicios.get(medio).getIdZona()-zone1;
			if(res == 0)
			{
				existe = true;
				zone = zonaServicios.get(medio);
			}
			else if(res > 0)

			{
				fin = medio-1;
			}
			else if(res < 0)
			{
				inicio = medio+1;
			}
		}
		return zone;
	}
	/**
	 * Da la lista de servicios que están en el rango
	 * @param r RangoFechaHora rango en el cual se evaluaran los servicios
	 * @return ArrayLisT con la lista de servicios
	 */
	public ArrayListT<Servicio> serviciosEnRango(RangoFechaHora r)
	{
		ArrayListT<Servicio> arr = new ArrayListT<Servicio>();
		try
		{
			for (int i = 0; i < servicios.size(); i++)
			{
				if(servicios.get(i).estaEnElRango(r))
				{
					arr.add(servicios.get(i));
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return arr;
	}
	/**
	 * Verifica si hay companias sin taxi
	 * @return true si hay al menos una compania sin taxi false si no hay al menos una
	 */
	public boolean hayCompaniasSinTaxi()
	{
		for(int i = 0; i < companias.size(); i++)
		{
			for(int j = 0; j < companias.get(i).getTaxisCompania().size(); j++)
			{
				if(companias.get(i).getTaxisCompania().get(j).getTaxiId().equals("NaN"))
				{
					return true;
				}
			}
		}
		return false;
	}

	//--------------------------------
	//REQUERIMIENTOS
	//--------------------------------
	
	/**
	 * Generar	una	Cola con	 todos	los	servicios	de	 taxi	que	se	prestaron	en	un	periodo	de	tiempo	 dado	 por	 una	 fecha/hora	 inicial	 y	 una	 fecha/hora	 final	 de	 consulta.	
	 * @return Cola con los servicios en el periodo.
	 */
	@Override //1A
	public IQueue <Servicio> darServiciosEnPeriodo(RangoFechaHora rango)
	{
		Queue<Servicio> cola = new Queue<Servicio>();
		ArrayListT<Servicio> services = new ArrayListT<Servicio>();
		OrdenatorP<Servicio> ordenador = new OrdenatorP<Servicio>();
		ComparatorServicioFechaInicial comparador = new ComparatorServicioFechaInicial();
		try
		{
			for (int j = 0; j <servicios.size(); j++)
			{
				Servicio s = servicios.get(j);
				if(s.estaEnElRango(rango))
				{
					services.add(s);					
				}
			}
			ordenador.ordenar(Ordenamientos.MERGE, true, comparador, services);
			System.out.println(services.get(services.size()-1));

			for (int i = 0; i < services.size(); i++) 
			{
				cola.enqueue(services.get(i));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return cola;
	}
	
	/**
	 *  Buscar el taxi de una compañía dada que más servicios inició en un periodo de	tiempo dado	por	una	fecha/hora inicial y una fecha/hora final de consulta.		
	 *  @param RangoFechaHora rango
	 *  @param company nombre de la compañia
	 * @return Taxi con mas servicios
	 */
	@Override //2A
	public Taxi darTaxiConMasServiciosEnCompaniaYRango(RangoFechaHora rango, String company)
	{
		ArrayListT<Taxi> arr = new ArrayListT<Taxi>();
		Compania c = binarySearchCompany(company);
		if(c != null)
			arr = c.getTaxisCompania();
		for(int i=0; i< arr.size(); i++)
		{
			arr.get(i).asignarNumeroServiciosEnRango(rango);
		}
		OrdenatorP<Taxi> ordenador = new OrdenatorP<Taxi>();
		ComparatorServiciosRango comparador = new ComparatorServiciosRango();
		ordenador.ordenar(Ordenamientos.MERGE, false, comparador, arr);

		return arr.get(0);
	}
	/**
	 * Buscar	la	información	completa	de	un	taxi,	a	partir	de	su	identificador,	en	un	periodo	de	 tiempo	dado	por	una	 fecha/hora	inicial	y	una	 fecha/hora	 final	de	consulta.	
	 * @param String id
	 * @param RangoFechaHora rango	
	 * @return InfoTaxiRango informacion del taxi 
	 */
	@Override //3A
	public InfoTaxiRango darInformacionTaxiEnRango(String id, RangoFechaHora rango)
	{
		//Necesita nombre compania, valor total de plata ganada, numero de servicios prestados, distancia recorrida
		//y tiempo total de servicios.

		InfoTaxiRango info = new InfoTaxiRango(rango);
		Taxi t = null;
		boolean encontrado = false;
		int i = 0;
		while(!encontrado)
		{
			t = companias.get(i).binarySearchTaxi(id);
			if( t != null )
			{
				encontrado = true;
			}
			i++;
		}

		if(t != null)
		{
			info.setIdTaxi(t.getTaxiId());
			info.setCompany(t.getCompany());

			t.asignarPlataGanada(rango);
			t.asignarServiciosEnRango(rango);
			t.asignarDistanciaServiciosEnRango(rango);
			t.asignarTiempoServiciosEnRango(rango);

			info.setPlataGanada(t.getPlataGanada());
			info.setNumServicesInRange(t.getNumServicesInRange());
			info.setTiempoTotal(t.getTiempoServicios());
			info.setDistanciaTotalRecorrida(t.getDistanciaRecorrida());
		}

		return info;
	}
	/**
	 * Dar una lista	de	rangos	de	distancia	recorrida,	en	la	que	se	encuentran	todos	los	servicios	de	 taxis	servidos	por	las	compañías,	en	una	 fecha	dada	y	en	un	rango	de	horas	especificada.		
	 *  @param String fecha
	 *  @param String horaInicial
	 * @param String horaFinal
	 * @return lista de rangos distancia.
	 */
	@Override //4A
	public LinkedList<RangoDistancia> darListaRangosDistancia(String fecha, String horaInicial, String horaFinal) 
	{
		DoubleLinkedList<RangoDistancia> listaSuperior = new DoubleLinkedList<RangoDistancia>();
		ArrayListT<Servicio> servicesInRange = serviciosEnRango(new RangoFechaHora(fecha, horaInicial, horaFinal));
		OrdenatorP<Servicio> ordenador = new OrdenatorP<Servicio>();
		ComparatorServicesMiles comparador = new ComparatorServicesMiles();
		ordenador.ordenar(Ordenamientos.MERGE, true, comparador, servicesInRange);		

		int n = 0;
		boolean tope;
		boolean limite = false;
		if(servicesInRange.size() != 0)
			n = (int) Math.floor(servicesInRange.get(servicesInRange.size()-1).getTripMiles());
		int j = 0;
		for (int i = 0; i < n+1; i++) 
		{
			RangoDistancia rangoDis = new RangoDistancia(i, i+1);
			tope = false;
			limite = false;
			while(!tope && !limite)
			{
				if(servicesInRange.get(j)!= null)
				{
					if(servicesInRange.get(j).getTripMiles() <  i+1)
					{
						rangoDis.getServiciosEnRango().add(servicesInRange.get(j));
					}
					else
					{
						j--;
						tope = true;
					}
				}
				else
				{
					j--;
					limite = true;
				}
				j++;
			}
			listaSuperior.add(rangoDis);
		}
		System.out.println(n+"------------------");

		return listaSuperior;
	}
	/**
	 * Mostrar	la	información	de	las	compañías	de	taxi	consistente	en:	El	total	de	compañías	que	tienen	al	menos	un	taxi	inscrito	y	el	total	de	taxis	que	prestan	servicio	para	al	menos	una	 compañía.	
	 * @return lista de companias con taxis inscritos.
	 */
	@Override //1B
	public LinkedList<Compania> darCompaniasTaxisInscritos() 
	{
		return companias.toList();
	}
	/**
	 *  Buscar	 el	 taxi	 de	 una	 compañía	 dada	 que	 mayor	 facturación	 ha	 generado	 en	 un	periodo	de	tiempo	dado	por	una	fecha/hora	inicial	y	una	fecha/hora	final	de	consulta.
	 *  @param RangoFechaHora rango
	 *  @param company nombre de la compañia
	 * @return Taxi con mas servicios
	 */
	@Override //2B
	public Taxi darTaxiMayorFacturacion(RangoFechaHora rango, String nomCompania) 
	{
		ArrayListT<Taxi> arr = new ArrayListT<Taxi>();
		Compania c = binarySearchCompany(nomCompania);
		arr = c.getTaxisCompania();
		for(int i=0; i< arr.size(); i++)
		{
			arr.get(i).asignarPlataGanada(rango);
		}
		OrdenatorP<Taxi> ordenador = new OrdenatorP<Taxi>();
		ComparatorTaxiCash comparador = new ComparatorTaxiCash();
		ordenador.ordenar(Ordenamientos.MERGE, false, comparador, arr);

		return arr.get(0);
	}
	/**
	 * Buscar	 la	 información	 completa	 de	 una	 zona	 de	 la	 ciudad	en	 un	 periodo	 de	 tiempo	dado	 por	 una	 fecha/hora	 inicial	 y	 una	 fecha/hora	 final	 de	 consulta.
	 *  @param RangoFechaHora rango
	 *  @param String idZona
	 * @return lista de servicios 
	 */
	@Override //3B
	public LinkedList<Servicio> darServiciosZonaValorTotal(RangoFechaHora rango, String idZona)
	{
		//Aplicar búsqueda binaria previamente junto a organización
		ZonaServicios z = binarySearchZonaServicios(Integer.parseInt(idZona));
		ArrayListT<Servicio> lista = z.listaZonasAcotada(rango);
		return lista.toList();
	}
	/**
	 * 	
	 * Buscar la información	 completa de una zona de la ciudad en un periodo de	tiempo dado por	una fecha/hora inicial y una fecha/hora final de consulta.
	 *  @param RangoFechaHora rango
	 * @return lista de Zonaservicios 
	 */
	@Override //4B
	public LinkedList<ZonaServicios> darZonasServicios(RangoFechaHora rango)
	{
		OrdenatorP<FechaServicios> ordenadorFechasZonas =  new OrdenatorP<FechaServicios>();
		ComparatorFechaServicios comp = new ComparatorFechaServicios();
		for (int i = 0; i < zonaServicios.size(); i++)
		{
			ordenadorFechasZonas.ordenar(Ordenamientos.MERGE, true, comp,zonaServicios.get(i).getFechasServicios());
		}	
		return zonaServicios.toList();
	}
	/**
	 * Identificar	el	top	X de compañías	que	más	servicios	iniciaron	en	un	periodo	de	tiempo dado	por	una	fecha/hora	inicial	y	una	fecha/hora	final.	
	 *  @param RangoFechaHora rango
	 * @param int  n Numero del top de companias
	 * @return lista de rangos distancia.
	 */
	@Override //2C
	public LinkedList<Compania> companiasMasServicios(RangoFechaHora rango, int n)
	{
		DoubleLinkedList<Compania> companiasTop = new DoubleLinkedList<Compania>();
		for (int i = 0; i < companias.size();i++) 
		{
			companias.get(i).asignarNumServiciosEnRango(rango);
		}
		OrdenatorP<Compania> ordenad = new OrdenatorP<Compania>();
		ordenad.ordenar(Ordenamientos.MERGE, false, new ComparatorNumerosServiciosCompania(),companias);
		for (int i = 0; i < n; i++) 
		{
			companiasTop.add(companias.get(i));
		}
		return companiasTop;
	}
	/**
	 * Busca el taxi mas rentable de cada compania
	 * @return lista de los taxis más rentables.
	 */
	@Override //3C
	public LinkedList<Taxi> taxisMasRentables()
	{
		DoubleLinkedList<Taxi> listaDeTaxisMasRentables = new DoubleLinkedList<Taxi>();
		//La rentabilidad se puede definir como el cociente entre la plata ganada y los servicios realizados
		OrdenatorP<Taxi> ordenad = new OrdenatorP<Taxi>();
		for (int i = 0; i < companias.size(); i++) 
		{
			ordenad.ordenar(Ordenamientos.MERGE, false, new ComparatorRentabilidad(),companias.get(i).getTaxisCompania());
			listaDeTaxisMasRentables.add(companias.get(i).getTaxisCompania().get(0));
		}
		return listaDeTaxisMasRentables;
	}
	/**
	 * Da un resumen de los servicios de un taxi.
	 * @param String taxiId
	 * @param String horaInicial
	 * @param String horaFinal
	 * @param String fecha
	 * @return pila con el servicio resumen
	 */
	@Override //4C
	public IStack <Servicio> darServicioResumen(String taxiId, String horaInicial, String horaFinal, String fecha) 
	{		
		Stack<Servicio> pila = new Stack<Servicio>();
		Stack<Servicio> pilaDef = new Stack<Servicio>();
		boolean encontrado = false;
		int i = 0; int n = 1; double millas = 0; double cash = 0; int time = 0;
		Taxi t  = null;
		while(!encontrado)
		{
			t = companias.get(i).binarySearchTaxi(taxiId);
			if( t != null )
			{
				encontrado = true;
			}
			i++;
		}
		String fechaIni = "";
		String fechaFi = "";
		t.ordenarServiciosPorFechaInicial();
		ArrayListT<Servicio>  lista  = t.asignarServiciosEnRango(new RangoFechaHora(fecha, horaInicial, horaFinal));
		for(int j=0; j < lista.size();j++)
		{
			if((n*10-millas) >= lista.get(j).getTripMiles())
			{
				pila.push(new Servicio(lista.get(j).getTripId(), lista.get(j).getTaxiId(), lista.get(j).getTripSeconds(),lista.get(j).getTripMiles(), lista.get(j).getTripTotal(),
						lista.get(j).getDropoffArea(), lista.get(j).getPickupArea(), lista.get(j).getFechaHoraInicial(), lista.get(j).getFechaHoraFinal()));

				millas += lista.get(j).getTripMiles();
				cash += lista.get(j).getTripTotal();
				time += lista.get(j).getTripSeconds();
			}
			else
			{				
				int na = pila.size();

				for(int k = 0; k < na  ; k++ )
				{
					Servicio s = pila.pop();
					if(k == 0)
					{
						fechaIni = s.getFechaHoraInicial();
					}
					if(k == (na-1))
					{
						fechaFi = s.getFechaHoraFinal();
					}

				}
				pilaDef.push(new Servicio("Nan", "NaN", time, millas, cash, -2, -2, fechaIni, fechaFi));
				j = j-1;
				n++;
			}


		}
		if(!pila.isEmpty())
		{
			int na = pila.size();

			for(int k = 0; k < na  ; k++ )
			{
				Servicio s = pila.pop();
				if(k == 0)
				{
					fechaIni = s.getFechaHoraInicial();
				}
				if(k == (na-1))
				{
					fechaFi = s.getFechaHoraFinal();
				}

			}
			pilaDef.push(new Servicio("Nan", "NaN", time, millas, cash, -2, -2, fechaIni, fechaFi));
		}
		if(pilaDef.isEmpty())
		{
			pilaDef.push(new Servicio("Nan", "NaN", time, millas, cash, -2, -2, "", ""));
		}

		return pilaDef;

	}

}