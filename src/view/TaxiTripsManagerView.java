package view;

import java.io.File;
import java.util.Scanner;

import controller.Controller;
import model.data_structures.ArrayListT;
import model.data_structures.DoubleLinkedList;
import model.data_structures.IQueue;
import model.data_structures.LinkedList;
import model.data_structures.Stack;
import model.logic.TaxiTripsManager;
import model.vo.Compania;
import model.vo.FechaServicios;
import model.vo.InfoTaxiRango;
import model.vo.RangoDistancia;
import model.vo.RangoFechaHora;
import model.vo.Servicio;
import model.vo.Taxi;
import model.vo.ZonaServicios;

/**
 * view del programa
 */
public class TaxiTripsManagerView 
{

	public static void main(String[] args) 
	{
		Scanner sc = new Scanner(System.in);
		boolean fin=false;
		while(!fin)
		{
			//imprime menu
			printMenu();

			//opcion req
			int option = sc.nextInt();

			switch(option)
			{
			//1C cargar informacion dada
			case 1:

				//imprime menu cargar
				printMenuCargar();

				//opcion cargar
				int optionCargar = sc.nextInt();

				//directorio json
				String linkJson = "";
				switch (optionCargar)
				{
				//direccion json pequeno
				case 1:

					linkJson = TaxiTripsManager.DIRECCION_SMALL_JSON;
					break;

					//direccion json mediano
				case 2:

					linkJson = TaxiTripsManager.DIRECCION_MEDIUM_JSON;
					break;

					//direccion json grande
				case 3:

					linkJson = TaxiTripsManager.DIRECCION_LARGE_JSON;
					break;
				}

				//Memoria y tiempo
				long memoryBeforeCase1 = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
				long startTime = System.nanoTime();

				if(linkJson.equals(TaxiTripsManager.DIRECCION_LARGE_JSON))
				{
					System.out.println(linkJson);
					File arch = new File(linkJson);
					if ((arch.exists())) 
					{
						File[] files = arch.listFiles();
						for (File file : files) 
						{
							if(file.isFile())
							{
								System.out.println("./data/taxi-trips-wrvz-psew-subset-large/"+file.getName());
								Controller.cargarSistema("./data/taxi-trips-wrvz-psew-subset-large/"+file.getName());
							}
						}
					}
				}
				else
				{
					Controller.cargarSistema(linkJson);
				}



				//Tiempo en cargar
				long endTime = System.nanoTime();
				long duration = (endTime - startTime)/(1000000);

				//Memoria usada
				long memoryAfterCase1 = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
				System.out.println("Tiempo en cargar: " + duration + " milisegundos \nMemoria utilizada:  "+ ((memoryAfterCase1 - memoryBeforeCase1)/1000000.0) + " MB");

				break;

				//1A	
			case 2:

				//fecha inicial
				System.out.println("Ingrese la fecha inicial (Ej : 2017-02-01)");
				String fechaInicialReq1A = sc.next();

				//hora inicial
				System.out.println("Ingrese la hora inicial (Ej: 09:00:00.000)");
				String horaInicialReq1A = sc.next();

				//fecha final
				System.out.println("Ingrese la fecha final (Ej : 2017-02-01)");
				String fechaFinalReq1A = sc.next();

				//hora final
				System.out.println("Ingrese la hora final (Ej: 09:00:00.000)");
				String horaFinalReq1A = sc.next();

				//VORangoFechaHora
				RangoFechaHora rangoReq1A = new RangoFechaHora(fechaInicialReq1A, fechaFinalReq1A, horaInicialReq1A, horaFinalReq1A);

				//Se obtiene la queue dada el rango
				IQueue<Servicio> colaReq1A = Controller.darServiciosEnRango(rangoReq1A);
				//TODO 
				//Recorra la cola y muestre cada servicio en ella
				int n = colaReq1A.size();
				for (int i = 0; i < n; i++) 
				{
					Servicio s = colaReq1A.dequeue();
					System.out.println("Fecha/hora inicial: " + s.getFechaHoraInicial() +" -- " + "Service Trip ID: " + s.getTripId());
				}
				break;

			case 3: //2A

				//comany
				System.out.println("Ingrese el nombre de la compañía");
				sc.nextLine();
				String companyReq2A = sc.nextLine();

				//fecha inicial
				System.out.println("Ingrese la fecha inicial (Ej : 2017-02-01)");
				String fechaInicialReq2A = sc.nextLine();

				//hora inicial
				System.out.println("Ingrese la hora inicial (Ej: 09:00:00.000)");
				String horaInicialReq2A = sc.nextLine();

				//fecha final
				System.out.println("Ingrese la fecha final (Ej : 2017-02-01)");
				String fechaFinalReq2A = sc.nextLine();

				//hora final
				System.out.println("Ingrese la hora final (Ej: 09:00:00.000)");
				String horaFinalReq2A = sc.next();

				//VORangoFechaHora
				RangoFechaHora rangoReq2A = new RangoFechaHora(fechaInicialReq2A, fechaFinalReq2A, horaInicialReq2A, horaFinalReq2A);
				Taxi taxiReq2A = Controller.darTaxiConMasServiciosEnCompaniaYRango(rangoReq2A, companyReq2A);

				//TODO
				//Muestre la info del taxi
				if(taxiReq2A != null)
					System.out.println("ID del taxi: " + taxiReq2A.getTaxiId() + " \n Compañía del taxi: " + taxiReq2A.getCompany() + " - " +" Número servicios en rango: " + taxiReq2A.getNumServicesInRange());
				else
					System.out.println("ID del taxi: " + "NA" + " - Compañía del taxi: " + "NA" + " - " + " Número servicios en rango: " + "NA");

				break;

			case 4: //3A

				//comany
				System.out.println("Ingrese el id del taxi");
				String idTaxiReq3A = sc.next();

				//fecha inicial
				System.out.println("Ingrese la fecha inicial (Ej : 2017-02-01)");
				String fechaInicialReq3A = sc.next();

				//hora inicial
				System.out.println("Ingrese la hora inicial (Ej: 09:00:00.000)");
				String horaInicialReq3A = sc.next();

				//fecha final
				System.out.println("Ingrese la fecha final (Ej : 2017-02-01)");
				String fechaFinalReq3A = sc.next();

				//hora final
				System.out.println("Ingrese la hora final (Ej: 09:00:00.000)");
				String horaFinalReq3A = sc.next();

				//VORangoFechaHora
				RangoFechaHora rangoReq3A = new RangoFechaHora(fechaInicialReq3A, fechaFinalReq3A, horaInicialReq3A, horaFinalReq3A);
				InfoTaxiRango taxiReq3A = Controller.darInformacionTaxiEnRango(idTaxiReq3A, rangoReq3A);

				//TODO
				//Muestre la info del taxi:
				//Nombre compania 
				//Valor total de plata ganada
				//Numero de servicios prestados
				//Distancia recorrida
				//Tiempo total de servicios.
				if(taxiReq3A.getIdTaxi() != "")
				{
					System.out.println("Compañía del taxi: " + taxiReq3A.getCompany());
					System.out.println("Valor total de plata ganada: $" + taxiReq3A.getPlataGanada());
					System.out.println("Número de servicios prestados en rango: " + taxiReq3A.getNumServicesInRange());
					System.out.println("Distancia total recorrida de servicios en rango: " + taxiReq3A.getDistanciaTotalRecorrida() + " millas");
					System.out.println("Tiempo total duración de servicios en rango: " + taxiReq3A.getTiempoTotal() + " seg");
				}
				else
				{
					System.out.println("Compañía del taxi: " + "NA");
					System.out.println("Valor total de plata ganada: " + "NA");
					System.out.println("Número de servicios prestados en rango: " + "NA");
					System.out.println("Distancia total recorrida de servicios en rango: " + "NA");
					System.out.println("Tiempo total duración de servicios en rango: " + "NA");
				}

				break;

			case 5: //4A

				//fecha 
				System.out.println("Ingrese la fecha inicial (Ej : 2017-02-01)");
				String fechaReq4A = sc.next();

				//hora inicial
				System.out.println("Ingrese la hora inicial (Ej: 09:00:00.000)");
				String horaInicialReq4A = sc.next();

				//hora final
				System.out.println("Ingrese la hora final (Ej: 09:00:00.000)");
				String horaFinalReq4A = sc.next();

				DoubleLinkedList<RangoDistancia> listaReq4A = (DoubleLinkedList<RangoDistancia>) Controller.darListaRangosDistancia(fechaReq4A, horaInicialReq4A, horaFinalReq4A);

				//TODO
				//Recorra la lista y por cada VORangoDistancia muestre los servicios
				listaReq4A.listing();
				int contador1 = 0;

				int n1 = listaReq4A.get(listaReq4A.size()-1).getServiciosEnRango().size();
				System.out.println(n1);

				while(listaReq4A.getActualNode()!= null)
				{
					System.out.println("~SERVICIOS ENTRE " + contador1 + " Y " + (contador1+1) + " MILLAS~");

					if(listaReq4A.getCurrent().getServiciosEnRango().size() == 0)
					{
						System.out.println("No hay servicios en este rango.");
					}
					else
					{
						for (int i = 0; i < listaReq4A.getCurrent().getServiciosEnRango().size(); i++) 
						{					
							Servicio s = listaReq4A.getCurrent().getServiciosEnRango().get(i);

							System.out.println("Trip ID del servicio:"+"->" + s.getTripId() + " -- Millas recorridas en el servicio: " + s.getTripMiles());
						}
					}
					listaReq4A.next();
					contador1++;
				}
				break;

			case 6: //1B
				int numeroDeTaxis = 0;
				DoubleLinkedList<Compania> lista=(DoubleLinkedList<Compania>) Controller.darCompaniasTaxisInscritos();
				System.out.println(" El numero de companias con al menos un taxi inscrito es : "+ lista.size());
				for(int i = 0; i < lista.size();i++)
				{
					System.out.println("   ="+lista.get(i).getNombre() + "  N.Taxis "+lista.get(i).getNumTaxisCompania());
					if(lista.get(i).getNombre() != "Independent")
						numeroDeTaxis += lista.get(i).getNumTaxisCompania();
				}
				System.out.println("El número de taxis asociados a una compañía es : " + numeroDeTaxis);

				//TODO
				//Mostrar la informacion de acuerdo al enunciado
				break;

			case 7: //2B
				//fecha inicial
				System.out.println("Ingrese la fecha inicial (Ej : 2017-02-01)");
				String fechaInicialReq2B = sc.next();

				//hora inicial
				System.out.println("Ingrese la hora inicial (Ej: 09:00:00.000)");
				String horaInicialReq2B = sc.next();

				//fecha final
				System.out.println("Ingrese la fecha final (Ej : 2017-02-01)");
				String fechaFinalReq2B = sc.next();

				//hora final
				System.out.println("Ingrese la hora final (Ej: 09:00:00.000)");
				String horaFinalReq2B = sc.next();

				//Compania
				System.out.println("Ingrese el nombre de la compañía");
				String compania2B=sc.nextLine()!=null?sc.nextLine():"Pennis";

				//VORangoFechaHora
				RangoFechaHora rangoReq2B = new RangoFechaHora(fechaInicialReq2B, fechaFinalReq2B, horaInicialReq2B, horaFinalReq2B);

				Taxi taxi=Controller.darTaxiMayorFacturacion(rangoReq2B, compania2B);
				System.out.println(" Nombre de la compañía : " + taxi.getCompany());
				System.out.println(" ID del taxi con mayor facturación: " +taxi.getTaxiId());
				System.out.println(" Dinero ganado por el taxi de mayor facturación: $" + taxi.getPlataGanada());

				//TODO
				//Mostrar la informacion del taxi obtenido

				break;

			case 8: //3B
				//Compania
				System.out.println("Ingrese el id de la zona");
				String zona3B=sc.next();

				//fecha inicial
				System.out.println("Ingrese la fecha inicial (Ej : 2017-02-01)");
				String fechaInicialReq3B = sc.next();

				//hora inicial
				System.out.println("Ingrese la hora inicial (Ej: 09:00:00.000)");
				String horaInicialReq3B = sc.next();

				//fecha final
				System.out.println("Ingrese la fecha final (Ej : 2017-02-01)");
				String fechaFinalReq3B = sc.next();

				//hora final
				System.out.println("Ingrese la hora final (Ej: 09:00:00.000)");
				String horaFinalReq3B = sc.next();

				RangoFechaHora rango3B= new RangoFechaHora(fechaInicialReq3B, fechaFinalReq3B, horaInicialReq3B, horaFinalReq3B);

				LinkedList<Servicio> resp=Controller.darServiciosZonaValorTotal(rango3B, zona3B);

				int mismaZona = 0;
				double plataMismaArea = 0.0;
				double plataupArea = 0.0;
				double plataoffArea = 0.0;

				int upZona = 0;
				int offZona = 0;
				for(int i= 0; i< resp.size(); i++)
				{
					if(resp.get(i).getPickupArea() == resp.get(i).getDropoffArea())
					{
						mismaZona++;
						plataMismaArea +=  resp.get(i).getTripTotal();

					}
					else if(resp.get(i).getPickupArea() == Integer.parseInt(zona3B) && resp.get(i).getDropoffArea() != Integer.parseInt(zona3B))
					{
						upZona++;
						plataupArea += resp.get(i).getTripTotal();

					}
					else
					{
						offZona++;
						plataoffArea += resp.get(i).getTripTotal();
					}
					System.out.println("Los servicios de esta zona en el rango de tiempo estipulado están referenciados por : "+resp.get(i).getTripId()+i);
				}
				System.out.println("  Los servicios que fueron recogidos y dejados en la misma zona son: "+mismaZona +" y aglomeran una cantidad de dinero correspodiente a $" + plataMismaArea);
				System.out.println("  Los servicios que fueron recogidos(unicamente) en esta zona son: "+upZona +" y aglomeran una cantidad de dinero correspodiente a $" + plataupArea);
				System.out.println("  Los servicios que fueron dejados(unicamente) en esta zona son: "+offZona +" y aglomeran una cantidad de dinero correspodiente a $" + plataoffArea);
				//TODO
				//Mostrar la informacion de acuerdo al enunciado
				break;

			case 9: //4B
				//fecha inicial
				System.out.println("Ingrese la fecha inicial (Ej : 2017-02-01)");
				String fechaInicialReq4B = sc.next();

				//hora inicial
				System.out.println("Ingrese la hora inicial (Ej: 09:00:00.000)");
				String horaInicialReq4B = sc.next();

				//fecha final
				System.out.println("Ingrese la fecha final (Ej : 2017-02-01)");
				String fechaFinalReq4B = sc.next();

				//hora final
				System.out.println("Ingrese la hora final (Ej: 09:00:00.000)");
				String horaFinalReq4B = sc.next();

				RangoFechaHora rango4B= new RangoFechaHora(fechaInicialReq4B, fechaFinalReq4B, horaInicialReq4B, horaFinalReq4B);

				LinkedList<ZonaServicios> lista4B= Controller.darZonasServicios(rango4B);
				ArrayListT<FechaServicios> arrayFech = new ArrayListT<FechaServicios>();
				int contador = 0;
				for (int i = 0; i < lista4B.size(); i++)
				{
					System.out.println("------- ZONA "+lista4B.get(i).getIdZona()+"-------");
					arrayFech = lista4B.get(i).listaAcotada(rango4B);
					for(int j=0; j < arrayFech.size() ; j++)
					{
						contador +=  arrayFech.get(j).getServiciosDeLaFecha().size();
						System.out.println("La fecha "+arrayFech.get(j).getFechaDeReferencia() + " tiene "+ arrayFech.get(j).getServiciosDeLaFecha().size() +" servicios");
					}
					System.out.println("                                                          ");
					System.out.println("La zona con id "+ lista4B.get(i).getIdZona() +" cuenta con "+ contador +" servicios en el rango establecido.");
					contador = 0;
				}

				//TODO
				//Mostrar la informacion de acuerdo al enunciado

				break;

			case 10: //2C
				System.out.println("Ingrese el número n de compañías");
				int n2C=sc.nextInt();

				//fecha inicial
				System.out.println("Ingrese la fecha inicial (Ej : 2017-02-01)");
				String fechaInicialReq2C = sc.next();

				//hora inicial
				System.out.println("Ingrese la hora inicial (Ej: 09:00:00.000)");
				String horaInicialReq2C = sc.next();

				//fecha final
				System.out.println("Ingrese la fecha final (Ej : 2017-02-01)");
				String fechaFinalReq2C = sc.next();

				//hora final
				System.out.println("Ingrese la hora final (Ej: 09:00:00.000)");
				String horaFinalReq2C = sc.next();

				RangoFechaHora rango2C= new RangoFechaHora(fechaInicialReq2C, fechaFinalReq2C, horaInicialReq2C, horaFinalReq2C);

				LinkedList<Compania> lista2C= Controller.companiasMasServicios(rango2C, n2C);

				for (int i = 0; i < lista2C.size(); i++) 
				{
					System.out.println("No. " + (i+1) + ": " + lista2C.get(i).getNombre() + " tiene " + lista2C.get(i).getNumServiciosEnRango() + " servicios");
				}
				//TODO
				//Mostrar la informacion de acuerdo al enunciado				

				break;
			case 11: //3C
				LinkedList<Taxi> lista3C=Controller.taxisMasRentables();

				for (int i = 0; i < lista3C.size(); i++)
				{
					System.out.println("  El taxi "+ lista3C.get(i).getTaxiId() + " de la compañia "+lista3C.get(i).getCompany()+ " es el más rentable.");
				}
				//TODO
				//Mostrar la informacion de acuerdo al enunciado				

				break;

			case 12: //4C

				//id taxi
				System.out.println("Ingrese el id del taxi");
				String idTaxi4C=sc.next();

				//fecha 
				System.out.println("Ingrese la fecha inicial (Ej : 2017-02-01)");
				String fechaReq4C = sc.next();

				//hora inicial
				System.out.println("Ingrese la hora inicial (Ej: 09:00:00.000)");
				String horaInicialReq4C = sc.next();

				//hora final
				System.out.println("Ingrese la hora final (Ej: 09:00:00.000)");
				String horaFinalReq4C = sc.next();
				System.out.println("El taxi "+ idTaxi4C + 
						"\n tuvo una compresión de sus datos en el periodo de tiempo siguiente : \n"+
						fechaReq4C+"-------"+ horaInicialReq4C +" - "+horaFinalReq4C);
				Stack <Servicio> resp4C=(Stack<Servicio>) Controller.darServicioResumen(idTaxi4C, horaInicialReq4C, horaFinalReq4C, fechaReq4C);
				int na = resp4C.size();
				if(na == 1)
				{
					System.out.println("Sólo tiene un rango.");
					Servicio s = resp4C.pop();
					System.out.println("____________ Entre "+  0 + " y " + 10+" _________________") ;
					System.out.println("                -Dinero compreso: $" + s.getTripTotal());
					System.out.println("                -Segundos compresos: " + s.getTripSeconds());
					System.out.println("                -Distancia compresa: " + s.getTripMiles());
				}
				else
				{
					double rango  = Math.round(resp4C.getActualNode().getElement().getTripMiles()/10);
					for(int j = 0; j < na  ; j++ )
					{
						Servicio s = resp4C.pop();

						//double millas = Math.round(s.getTripMiles()/10);
						if(rango ==0)
						{
							rango = 0.1;
							System.out.println("____________ Entre 0  y  10 _________________") ;

						}
						else
						{
							System.out.println("____________ Entre "+  ((rango*10)-10) + " y " + (rango*10)+" _________________") ;
						}
						System.out.println("                -Dinero compreso :" + s.getTripTotal());
						System.out.println("                -Segundos compresos :" + s.getTripSeconds());
						System.out.println("                -Distancia compresa :" + s.getTripMiles());

						rango = rango -1;
					}
				}				
				//TODO
				//Mostrar la informacion de acuerdo al enunciado				

				break;

			case 13: //salir
				fin=true;
				sc.close();
				break;

			}
		}
	}
	/**
	 * Menu 
	 */
	private static void printMenu() //
	{
		System.out.println("---------ISIS 1206 - Estructuras de datos----------");
		System.out.println("---------------------Proyecto 1----------------------");
		System.out.println("Cargar data (1C):");
		System.out.println("1. Cargar toda la informacion dada una fuente de datos (small,medium, large).");

		System.out.println("\nParte A:\n");
		System.out.println("2.Obtenga una lista con todos los servicios de taxi ordenados cronologicamente por su fecha/hora inicial, \n"
				+ " que se prestaron en un periodo de tiempo dado por una fecha/hora inicial y una fecha/hora final de consulta. (1A) ");
		System.out.println("3.Dada una compania y un rango de fechas y horas, obtenga  el taxi que mas servicios inicio en dicho rango. (2A)");
		System.out.println("4.Consulte la compania, dinero total obtenido, servicios prestados, distancia recorrida y tiempo total de servicios de un taxi dados su id y un rango de fechas y horas. (3A)");
		System.out.println("5.Dada una fecha y un rango de horas, obtenga una lista de rangos de distancia en donde cada posicion contiene los servicios de taxis cuya distancia recorrida pertence al rango de distancia. (4A)\n");

		System.out.println("Parte B: \n");
		System.out.println("6. Obtenga el numero de companias que tienen al menos un taxi inscrito y el numero total de taxis que trabajan para al menos una compania. \n"
				+ "Luego, genera una lista (en orden alfabetico) de las companias a las que estan inscritos los servicios de taxi. Para cada una, indique su nombre y el numero de taxis que tiene registrados. (1B)");
		System.out.println("7. Dada una compania, una fecha/hora inicial y una fecha/hora final, buscar el taxi de la compania que mayor facturacion generï¿½ en el tiempo dado. (2B)");
		System.out.println("8. Dada una zona de la ciudad, una fecha/hora inicial y una fecha/hora final, dar la siguiente informacion: (3B) \n"
				+ "   -Numero de servicios que iniciaron en la zona dada y terminaron en otra zona, junto con el valor total pagado por los usuarios. \n"
				+ "   -Numero de servicios que iniciaron en otra zona y terminaron en la zona dada, junto con el valor total pagado por los usuarios. \n"
				+ "   -Numero de servicios que iniciaron y terminaron en la zona dada, junto con el valor total pagado por los usuarios.");
		System.out.println("9. Dado un rango de fechas, obtener la lista de todas las zonas, ordenadas por su identificador. Para cada zona, dar la lista de fechas dentro del rango (ordenadas cronológicamente) \n "
				+ "y para cada fecha, dar el numero de servicios que se realizaron en dicha fecha. (4B)");


		System.out.println("\nParte C: \n");
		System.out.println("10. Dado un numero n, una fecha/hora inicial y una fecha/hora final, mostrar las n companias que mas servicios iniciaron dentro del rango. La lista debe estar ordenada descendentemente \n por el numero de servicios. Para cada compania, dar el nombre y el numero de servicios (2C)");
		System.out.println("11. Para cada compania, dar el taxi mas rentable. La rentabilidad es dada por la relacion entre el dinero ganado y la distancia recorrida. (3C)");
		System.out.println("12. Dado un taxi, dar el servicio resumen, resultado de haber comprimido su informacion segun el enunciado. (4C) ");
		System.out.println("13. Salir");
		System.out.println("Type the option number for the task, then press enter: (e.g., 1):");

	}

	private static void printMenuCargar()
	{
		System.out.println("-- Que fuente de datos desea cargar?");
		System.out.println("-- 1. Small");
		System.out.println("-- 2. Medium");
		System.out.println("-- 3. Large");
		System.out.println("-- Type the option number for the task, then press enter: (e.g., 1)");
	}

}
