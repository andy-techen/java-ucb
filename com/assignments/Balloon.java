package com.assignments;

import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Balloon {
	private int pressure;

	public Balloon() {
		pressure = 0;
	}

	public void inflate() {
		++pressure;
	}

	public static void main(String[] args) {
		int max_pressure = ThreadLocalRandom.current().nextInt(0, 10);
		Scanner sc = new Scanner(System.in);
		System.out.println("Creating new balloon...");
		Balloon balloon = new Balloon();

		while (true) {
			System.out.println("Press Enter to inflate balloon!");
			sc.nextLine();

			if (balloon.pressure <= max_pressure) {
				balloon.inflate();
				System.out.println("The balloon looks bigger.");
			} else {
				System.out.println("Oops! Balloon popped!");
				balloon = null;
				break;
			}
		}
	}
}
