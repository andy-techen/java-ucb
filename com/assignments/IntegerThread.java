package com.assignments;

public class IntegerThread
{
	private Integer variable;

	public IntegerThread() {
		variable = null;
	}

	synchronized void set_value(int i)
	{
		try {
			variable = i;
			// awaken one other waiting Thread
			notify();
			// release the monitor
			wait();
		} catch (InterruptedException e) {}

		notify();
	}

	synchronized void print_value()
	{
		try {
			while (variable != null) {
				System.out.println(variable);
			}
			System.out.println("null variable");
			// awaken one other waiting Thread
			notify();
			// release the monitor and go to sleep
			wait();
		} catch (InterruptedException e) {}
	}

	public static void main(String[] args) {
		Runnable sv = new Thread(new IntegerThread());
		Runnable pv = new Thread(new IntegerThread());
		sv.start();

		for (int i=1; i<=10; i++) {
			sv.set_value(i);
			pv.print_value();
		}
	}
}