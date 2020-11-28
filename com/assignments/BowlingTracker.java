package com.assignments;

import java.util.*;
import java.util.concurrent.*;
import java.time.*;

class Bowler {
	String name;
	TreeMap<LocalDate, Integer> games_hm;   // TreeMap for sorting

	public Bowler(String n) {
		name = n;
		games_hm = new TreeMap<>();
	}

	void NewGame(LocalDate dt, Integer sc) {
		games_hm.put(dt, sc);
	}
	void NewGame() {
		long max = LocalDate.now().toEpochDay();
		long min = LocalDate.of(2011, 1, 1).toEpochDay();
		LocalDate dt = LocalDate.ofEpochDay(ThreadLocalRandom.current().nextLong(min, max));   // generate random date
		Integer sc = ThreadLocalRandom.current().nextInt(0, 300);   // generate random score

		games_hm.put(dt, sc);
	}

	int AverageScore() {
		int total = 0;
		for (LocalDate k : games_hm.keySet()) {
			total += games_hm.get(k);
		}
		return total / games_hm.size();
	}

	public void ShowGames() {
		System.out.println("Bowler Name: " + name +
				"\r\nNumber of Games: " + games_hm.size() +
				"\r\nAverage Score: " + AverageScore() +
				"\r\nLast Game: " + games_hm.lastKey() +
				"\r\nLast Score: " + games_hm.lastEntry().getValue());
	}
}

public class BowlingTracker {
	public static void main(String[] args) {
		ArrayList<Bowler> bowlers = new ArrayList<>();
		bowlers.add(new Bowler("Andy"));
		bowlers.add(new Bowler("Jennifer"));
		bowlers.add(new Bowler("Carl"));

		bowlers.forEach(bowler -> {
			bowler.NewGame(LocalDate.parse("2020-11-26"), 200);  // test TreeMap sorting
			bowler.NewGame();  // randomly generate bowling results
			bowler.NewGame();

			bowler.ShowGames();
			System.out.println();
		});
	}
}
