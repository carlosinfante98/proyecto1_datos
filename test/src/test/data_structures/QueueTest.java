package test.data_structures;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import model.data_structures.IteratorList;
import model.data_structures.Queue;

public class QueueTest 
{
	private Queue<String> queue;

	@Before
	public void setupEscenario1()
	{
		queue = new Queue<String>();
		for(int i= 0 ; i<5; i++)
		{
			int cont = i+1;
			queue.enqueue("H"+cont);
		}
	}

	@Test
	public void enqueueTest()
	{
		setupEscenario1();

		queue.enqueue("H6");
		assertTrue("Debio retornar el elemento esperado", queue.getLastNode().getElement().equals("H6"));
		assertTrue("Debio retornar el siguiente del primer nodo", queue.getFirstNode().getNextNode().getElement().equals("H2"));
		
		queue.enqueue("H7");
		assertEquals("Debio retornar un diferente elemento", "H7", queue.getLastNode().getElement());
		
	}

	@Test
	public void dequeueTest()
	{
		setupEscenario1();

//		assertEquals("No es el elemento que se esperaba", "H1", queue.pop());
//		queue.pop();
//		assertTrue("No esta retornando el elemento correcto", queue.pop().equals("H3"));
//		queue.pop();
//		queue.pop();
//		try
//		{
//			queue.pop();
//			fail("Deberia botar error porque no hay elementos que sacar");
//		}
//		catch(Exception e)
//		{
//			//Deberia llegar aca
//		}
	}

	@Test
	public void sizeTest()
	{
		setupEscenario1();
		assertTrue("El size no es el correcto", queue.size() == 5);
		queue.dequeue();
		assertTrue("El size no es el correcto", queue.size() == 4);
		queue.enqueue("H0");
		assertTrue("El size no es el correcto", queue.size() == 5);
	}

	@Test
	public void isEmptyTest()
	{
		setupEscenario1();
		assertFalse("La lista no deberia estar vacia", queue.isEmpty());
		int i = 0;
		while(i <5)
		{
			queue.dequeue();		
			i++;
		}
		assertTrue("La lista deberia estar vacia", queue.isEmpty());
	}

	@Test
	public void iteratorTest()
	{
		setupEscenario1();
		assertEquals("No retorna el elemento correcto", "H1", queue.iterator().next());
		
		IteratorList<String> ite = queue.iterator();
		int i = 1;
		while(ite.hasNext())
		{
			assertEquals("No retorna el elemento correcto", "H" + i, ite.next());
			i++;
		}
	}
	
}
