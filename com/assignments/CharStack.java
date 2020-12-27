package com.assignments;

class UnderflowException extends Exception {
	UnderflowException(String message) {
		super(message);
	}
}

public class CharStack
{
	private char[] m_data;

	private int m_ptr;

	public CharStack(int size)
	{
		m_ptr = 0;
		m_data = new char[(size > 1 ? size : 10)];
	}

	public void push(char c)
	{
		if (m_ptr >= m_data.length)
		{
			// Grow the array automatically
			char[] tmp =
					new char[m_data.length * 2];

			System.arraycopy(
					m_data, 0,
					tmp, 0,
					m_data.length);
			m_data = tmp;
		}
		m_data[m_ptr++] = c;
	}

	public char pop() throws UnderflowException
	{
		if (m_ptr != 0) {
			return m_data[--m_ptr];
		}
		else {
			throw new UnderflowException("Stack Underflow: Empty Stack!");
		}
	}

	public static void main(String[] argv) throws Exception
	{
		CharStack s = new CharStack(10);
		int i;
		while ( (i = System.in.read()) != -1 )
		{
			s.push((char) i);
		}

		try
		{
			System.out.write(s.pop());  // catch underflow exception before popping the rest of the stack
			while (s.m_ptr > 0) {
				System.out.write(s.pop());
			}
			System.out.println();
		}
		catch (UnderflowException e)
		{
			System.out.println(e.getMessage());
			System.exit(1);
		}

	}
}