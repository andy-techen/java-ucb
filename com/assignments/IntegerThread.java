package com.assignments;

public class IntegerThread
{
	private Integer variable;
	private boolean empty_variable;

	public IntegerThread() {
		empty_variable = true;
	}

	synchronized boolean is_empty() {
		return empty_variable;
	}

	synchronized void insert(Integer i)
	{
		while (!is_empty()) {
			notify();
			try {
				wait(1000);
			} catch (InterruptedException e) {}
		}
		variable = i;
		empty_variable = false;
	}

	synchronized Integer extract()
	{
		while (is_empty()) {
			notify();
			try {
				wait(1000);
			} catch (InterruptedException e) {}
		}

		Integer temp = variable;
		variable = null;
		empty_variable = true;
		notify();
		return temp;
	}

	public static void main(String[] args) {
		IntegerThread thread = new IntegerThread();
		SetInteger t1 = new SetInteger(thread);
		PrintInteger t2 = new PrintInteger(thread);

		t1.start();
		t2.start();
	}
}

class SetInteger extends Thread {
	private IntegerThread thread;

	public SetInteger(IntegerThread thread) {
		this.thread = thread;
	}

	public void run() {
		for (int i=1; i<=10; i++) {
			thread.insert(i);
		}
	}
}

class PrintInteger extends Thread {
	private IntegerThread thread;

	public PrintInteger(IntegerThread thread) {
		this.thread = thread;
	}

	public void run() {
		Integer i;

		do {
			i = thread.extract();
			System.out.println("Variable = " + i);
		} while (i <= 10);
	}
}