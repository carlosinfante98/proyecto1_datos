package test.data_structures;


import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import model.data_structures.IteratorList;
import model.data_structures.Stack;

public class StackTest 
{

	private Stack<String> stack;

	@Before
	public void setupEscenario1()
	{
		stack = new Stack<String>();
		for(int i= 4 ; i>=0; i--)
		{
			int cont = i+1;
			stack.push("H"+cont);
		}
	}

	@Test
	public void pushTest()
	{
		setupEscenario1();

		stack.push("H0");
		assertTrue("Debio retornar el elemento esperado", stack.getActualNode().getElement().equals("H0"));

		assertTrue("Debio retornar el siguiente del nodo actual", stack.getActualNode().getNextNode().getElement().equals("H1"));

		stack.push(null);
		assertNull(stack.getActualNode().getElement());

		stack.push("H-1");
		assertNull(stack.getActualNode().getNextNode().getElement());
	}

	@Test
	public void popTest()
	{
		setupEscenario1();

		assertEquals("No es el elemento que se esperaba", "H1", stack.pop());
		stack.pop();
		assertTrue("No esta retornando el elemento correcto", stack.pop().equals("H3"));
		stack.pop();
		stack.pop();
		try
		{
			stack.pop();
			fail("Deberia botar error porque no hay elementos que sacar");
		}
		catch(Exception e)
		{
			//Deberia llegar aca
		}
	}

	@Test
	public void sizeTest()
	{
		setupEscenario1();
		assertTrue("El size no es el correcto", stack.size() == 5);
		stack.pop();
		assertTrue("El size no es el correcto", stack.size() == 4);
		stack.push("H0");
		assertTrue("El size no es el correcto", stack.size() == 5);
	}

	@Test
	public void isEmptyTest()
	{
		setupEscenario1();
		assertFalse("La lista no deberia estar vacia", stack.isEmpty());
		int i = 0;
		while(i <5)
		{
			stack.pop();		
			i++;
		}
		assertTrue("La lista deberia estar vacia", stack.isEmpty());
	}

	@Test
	public void iteratorTest()
	{
		setupEscenario1();
		assertEquals("No retorna el elemento correcto", "H1", stack.iterator().next());
		
		IteratorList<String> ite = stack.iterator();
		int i = 1;
		while(ite.hasNext())
		{
			assertEquals("No retorna el elemento correcto", "H" + i, ite.next());
			i++;
		}
	}

}
