package com.assignments.vehicles;

import java.util.*;

interface Domestic {}
interface Import {}

interface Japanese extends Import {}
interface German extends Import {}
interface Detroit extends Domestic {}
interface SpringHill extends Domestic {}

interface Vehicle {
	int getWeightInPounds();
}
interface Automobile extends Vehicle {
	int weight = 1000;
}
interface LargeAutomobile extends Vehicle {
	int weight = 2500;
}

interface Sedan extends Automobile {}
interface Van extends LargeAutomobile {}
interface Truck extends LargeAutomobile {}
interface Compact extends Automobile {}

interface SportsUtilityVehicle extends Truck, Van {}

class SaturnSL1 implements SpringHill, Sedan {
	@Override
	public int getWeightInPounds() {
		return weight;
	}
}
class HondaCivic implements Japanese, Compact {
	@Override
	public int getWeightInPounds() {
		return weight;
	}
}
class MercedesC230 implements German, Sedan {
	@Override
	public int getWeightInPounds() {
		return weight;
	}
}
class ChevyS10 implements Detroit, Truck {
	@Override
	public int getWeightInPounds() {
		return weight;
	}
}
class SubaruOutback implements Japanese, SportsUtilityVehicle {
	@Override
	public int getWeightInPounds() {
		return weight;
	}
}

public class ParkingGarage {
	private int acc_vehicles, acc_pounds;
	private HashMap<String, Integer> vehicle_hash;

	public ParkingGarage() {
		acc_vehicles = acc_pounds = 0;
		vehicle_hash = new HashMap<>();
	}

	public boolean ParkCar(Vehicle car) {
		int weight = car.getWeightInPounds();
		boolean parked; // return parking result to main
		if (acc_vehicles == 20 | (acc_pounds + weight) > 25000) {
			System.out.println("Level full, going to next level.");
			parked = false;
		} else {
			++acc_vehicles;
			acc_pounds += weight;
			String car_type = car.getClass().getSimpleName();
			Integer type_cnt = vehicle_hash.get(car_type);

			// keep parking records by car type
			if (type_cnt == null) {
				vehicle_hash.put(car_type, 1);
			} else {
				vehicle_hash.put(car_type, type_cnt + 1);
			}
			System.out.println("Car Parked!\r\n");
			parked = true;
		}
		return parked;
	}

	// check out current garage car tally
	public void XrayVision() {
		vehicle_hash.forEach((k, v) -> System.out.println(k + ": " + v));
		System.out.println("(Total Cars: " + acc_vehicles + "/20, Total Weight: " + acc_pounds + "/25000)\r\n");
	}

	public static void main(String[] args) {
		ArrayList<ParkingGarage> pgs = new ArrayList<>();
		pgs.add(new ParkingGarage());
		Scanner sc = new Scanner(System.in);
		int floor = 0;

		while (true) {
			System.out.println("You are currently at B" + (floor + 1) + ".");
			pgs.get(floor).XrayVision();
			System.out.println("Choose Car Type:\r\n1. Saturn SL1\r\n2. Honda Civic\r\n" +
					"3. Mercedes C230\r\n4. Chevy S10\r\n5. Subaru Outback");
			int car_type = sc.nextInt();

			switch (car_type) {
				case 1: {
					if(!pgs.get(floor).ParkCar(new SaturnSL1())) {
						++floor;
						pgs.add(new ParkingGarage());
						pgs.get(floor).ParkCar(new SaturnSL1());
					}
					break;
				}
				case 2: {
					if(!pgs.get(floor).ParkCar(new HondaCivic())) {
						++floor;
						pgs.add(new ParkingGarage());
						pgs.get(floor).ParkCar(new HondaCivic());
					}
					break;
				}
				case 3: {
					if(!pgs.get(floor).ParkCar(new MercedesC230())) {
						++floor;
						pgs.add(new ParkingGarage());
						pgs.get(floor).ParkCar(new MercedesC230());
					}
					break;
				}
				case 4: {
					if(!pgs.get(floor).ParkCar(new ChevyS10())) {
						++floor;
						pgs.add(new ParkingGarage());
						pgs.get(floor).ParkCar(new ChevyS10());
					}
					break;
				}
				case 5: {
					if(!pgs.get(floor).ParkCar(new SubaruOutback())) {
						++floor;
						pgs.add(new ParkingGarage());
						pgs.get(floor).ParkCar(new SubaruOutback());
					}
					break;
				}
			}
		}

	}
}
